package com.lening.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.College;
import com.lening.pojo.Grade;
import com.lening.pojo.Major;
import com.lening.service.CollegeService;
import com.lening.service.GradeService;
import com.lening.service.MajorService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.vo.GradeVo;

/**
 * 
 * @ClassName: GradeController
 * @Description: 班级管理模块
 * @author: 17861
 * @date: 2020-6-22 10:08:01
 */
@RestController
@RequestMapping(value = "/grade")
public class GradeController {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private MajorService majorService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 班级全查
	 * @return
	 * @return: List<Grade>
	 */
	@GetMapping(value = "/findAll")
	public List<Grade> findAll() {

		List<Grade> list = gradeService.findAll();

		return list;
	}

	/**
	 * 
	 * @Title: findPage
	 * @Description: 班级详细信息分页查询
	 * @param page
	 * @param limit
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findPage")
	public LayuiData findPage(Integer page, Integer limit) {

		if (page != null && limit != null) {

			PageInfo<GradeVo> pageInfo = gradeService.findPage(page, limit);

			return AjaxUtils.getLayuiData(pageInfo);
		}

		return null;

	}

	/**
	 * 
	 * @Title: save
	 * @Description: 新增班级
	 * @param grade
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Grade grade) {

		// 校验学院
		Integer collegeId = grade.getCollegeId();
		College college;

		if (collegeId == null) {
			return AjaxUtils.fail("请选择学院");
		} else {
			college = collegeService.findById(collegeId);
			if (college == null) {
				return AjaxUtils.fail("选择的学院不存在");
			}
		}

		// 校验专业
		Integer majorId = grade.getMajorId();
		Major major;

		if (majorId == null) {

			return AjaxUtils.fail("请选择专业");
		} else {

			// 根据专业id和学院id查询单条记录
			major = majorService.findByIdAndCollege(majorId, collegeId);

			if (major == null) {
				return AjaxUtils.fail("选择的专业不存在或者与所选学院不符");
			}

		}

		// 校验入学年份，限定为4位纯数字
		String enterYear = grade.getEnterYear();

		if (StringUtils.isBlank(enterYear) == true) {
			return AjaxUtils.fail("请选择入学年份");
		} else {

			// 限定入学年份前两位只能为20，后两位随意
			String regex = "20\\d{2}";

			if (enterYear.matches(regex) == false) {
				return AjaxUtils.fail("入学年份只能为4位纯数字");
			}

		}

		// 第一种方法：获取新生成的班级代码，生成规则：学院代码（2位数字）+专业代码（2位数字）+班级序号（1位数字）
		// String classCode = gradeService.getClassCode(majorId);
		//
		// if (classCode == null) {
		//
		// // 该专业下没有任何班级，末尾数字置为1
		// classCode = college.getCollegeCode() + major.getMajorCode() + 1;
		// }

		// 第二种方法：根据当前专业下的班级数量+1，获取新生成的班级代码的末位数字
		int endNumber = gradeService.getEndNumber(majorId);
		String classCode = college.getCollegeCode() + major.getMajorCode() + endNumber;

		// 给班级代码赋值
		grade.setClassCode(classCode);

		// 生成班级名称，生成规则为：专业名称 + “-” + 班级序号（1位数字）
		String className = major.getMajorName() + "-" + endNumber;
		// 给班级名称赋值
		grade.setClassName(className);

		// 新增班级
		int save = gradeService.save(grade);

		if (save > 0) {
			return AjaxUtils.success("班级新增成功");
		} else {

			return AjaxUtils.fail("班级新增失败，请重试");
		}

	}

}
