package com.lening.controller;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Grade;
import com.lening.pojo.Teacher;
import com.lening.service.GradeService;
import com.lening.service.RoleMenuButtonService;
import com.lening.service.TeacherService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.vo.TeacherVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private GradeService gradeService;

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 讲师全查
	 * @return
	 * @return: List<Teacher>
	 */
	@GetMapping(value = "/findAll")
	public List<Teacher> findAll() {

		List<Teacher> list = teacherService.findAll();

		return list;
	}

	/**
	 * 
	 * @Title: findPage
	 * @Description: 讲师分页查询
	 * @param page
	 * @param limit
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findPage")
	public LayuiData findPage(Integer page, Integer limit) {

		if (page != null && limit != null) {
			PageInfo<TeacherVo> pageInfo = teacherService.findPage(page, limit);

			return AjaxUtils.getLayuiData(pageInfo);
		}

		return null;
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 新增讲师
	 * @param teacher
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Teacher teacher) {

		// 校验讲师姓名
		String teacherName = teacher.getTeacherName();
		if (StringUtils.isBlank(teacherName) == true) {

			return AjaxUtils.fail("讲师姓名不能为空");
		} else {

			if (teacherName.length() < 2 || teacherName.length() > 4) {

				return AjaxUtils.fail("讲师姓名只能为2-4位");
			}

		}

		// 校验性别
		String sex = teacher.getSex();
		if (StringUtils.isBlank(sex) == true) {
			return AjaxUtils.fail("性别不能为空");
		} else {

			if (sex.equals("男") == false && sex.equals("女") == false) {
				return AjaxUtils.fail("性别只能为男或女");
			}

		}

		// 校验手机号
		String phoneNumber = teacher.getPhoneNumber();
		if (StringUtils.isBlank(phoneNumber) == true) {
			return AjaxUtils.fail("手机号不能为空");
		} else {

			String regex = "1\\d{10}";

			if (phoneNumber.matches(regex) == false) {
				return AjaxUtils.fail("手机号只能为11位数字");
			}
		}

		// 校验职称
		String title = teacher.getTitle();
		if (StringUtils.isBlank(title) == true) {
			return AjaxUtils.fail("职称不能为空");
		} else {

			ArrayList<String> list = new ArrayList<String>();

			list.add("助教");
			list.add("讲师");
			list.add("副教授");
			list.add("教授");

			if (list.contains(title) == false) {
				return AjaxUtils.fail("职称不合法");
			}
		}

		// 校验班级
		Integer gradeId = teacher.getGradeId();
		if (gradeId == null) {

			return AjaxUtils.fail("班级id不能为空");
		} else {

			Grade grade = gradeService.findById(gradeId);

			if (grade == null) {

				return AjaxUtils.fail("班级id不存在");
			}
		}

		// 新增讲师
		int save = teacherService.save(teacher);

		if (save > 0) {
			return AjaxUtils.success("讲师信息新增成功");
		} else {
			return AjaxUtils.fail("讲师信息新增失败，请重试");
		}

	}

}
