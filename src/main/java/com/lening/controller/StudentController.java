package com.lening.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Grade;
import com.lening.pojo.Student;
import com.lening.service.GradeService;
import com.lening.service.StudentService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.vo.StudentVo;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private GradeService gradeService;

	/**
	 * 
	 * @Title: findVo
	 * @Description: 查询学生详细信息
	 * @return
	 * @return: List<StudentVo>
	 */
	@GetMapping(value = "/findVo")
	public List<StudentVo> findVo() {

		List<StudentVo> list = studentService.findVo();

		return list;
	}

	/**
	 * 
	 * @Title: findPage
	 * @Description: 学生信息分页查询
	 * @param page
	 * @param limit
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findPage")
	public LayuiData findPage(Integer page, Integer limit) {

		if (page != null && limit != null) {

			PageInfo<StudentVo> pageInfo = studentService.findPage(page, limit);

			return AjaxUtils.getLayuiData(pageInfo);
		}

		return null;
	}

	/**
	 * 
	 * @Title: findByCase
	 * @Description: 学生信息多条件查询
	 * @param page
	 * @param limit
	 * @param student
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findByCase")
	public LayuiData findByCase(Integer page, Integer limit, Student student) {

		if (page != null && limit != null) {

			PageInfo<StudentVo> pageInfo = studentService.findByCase(student, page, limit);

			return AjaxUtils.getLayuiData(pageInfo);
		}

		return null;
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 学生新增
	 * @param student
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Student student) {

		// 校验学生姓名
		String studentName = student.getStudentName();
		if (StringUtils.isBlank(studentName) == true) {

			return AjaxUtils.fail("学生姓名不能为空");
		} else {

			if (studentName.length() < 2 || studentName.length() > 4) {

				return AjaxUtils.fail("学生姓名只能为2-4位");
			}

		}

		// 校验性别
		String sex = student.getSex();
		if (StringUtils.isBlank(sex) == true) {
			return AjaxUtils.fail("性别不能为空");
		} else {

			if (sex.equals("男") == false && sex.equals("女") == false) {
				return AjaxUtils.fail("性别只能为男或女");
			}

		}


		// 校验爱好
		String hobby = student.getHobby();

		// 非空判断
		if (StringUtils.isBlank(hobby) == true) {
			return AjaxUtils.fail("爱好不能为空");
		}

		// 判断爱好是否是唱歌、跳舞、打游戏中的单独一项
		ArrayList<String> hobbyList = new ArrayList<String>();
		hobbyList.add("唱歌");
		hobbyList.add("跳舞");
		hobbyList.add("打游戏");

		if (hobbyList.contains(hobby) == false) {

			// 判断是否包含逗号
			if (hobby.contains(",") == false) {

				return AjaxUtils.fail("爱好不包含逗号，不合法");
			} else {
				
				// 判断是否以逗号开头或结尾，如果是，返回失败
				if (hobby.startsWith(",") || hobby.endsWith(",")) {

					return AjaxUtils.fail("不能以逗号开头或结尾，爱好不合法");
				} else {

					// 遍历数组
					String[] hobbyArray = hobby.split(",");

					// 数组长度只能为2或3
					if (hobbyArray.length > 3) {
						return AjaxUtils.fail("爱好长度不合法");
					} else {

						// 判断每一个元素是否是唱歌、跳舞、打游戏其中的一项
						for (String h : hobbyArray) {

							if (hobbyList.contains(h) == false) {

								return AjaxUtils.fail("爱好不属于唱歌、跳舞、打游戏中的任何一种，爱好不合法");
							}
						}

						// 判断爱好是否存在重复的情况
						hobbyList.clear();

						for (String h : hobbyArray) {

							if (hobbyList.contains(h) == false) {
								hobbyList.add(h);
							} else {

								// 爱好重复
								return AjaxUtils.fail("爱好重复");
							}
						}

					}


				}

			}
			
		}


		// 校验班级
		Integer gradeId = student.getGradeId();
		if (gradeId == null) {

			return AjaxUtils.fail("班级id不能为空");
		} else {

			Grade grade = gradeService.findById(gradeId);

			if (grade == null) {

				return AjaxUtils.fail("班级id不存在");
			} else {

				// 学号生成，规则为：入学年份（4位数字）+班级代码（5位数字）+学生序号（2位数字）
				int studentCount = studentService.getStudentCount(gradeId);

				String studentNumber;

				// 判断学生数量是否大于等于9，如果是，则不需要用0补全；如果小于9，则需要用0补全
				if (studentCount >= 9) {
					studentNumber = grade.getEnterYear() + grade.getClassCode() + (studentCount + 1);
				} else {

					studentNumber = grade.getEnterYear() + grade.getClassCode() + "0" + (studentCount + 1);
				}

				// 给学号属性赋值
				student.setStudentNumber(studentNumber);

			}
		}


		// 新增学生
		int save = studentService.save(student);

		if (save > 0) {
			return AjaxUtils.success("学生信息新增成功");
		} else {
			return AjaxUtils.fail("学生信息新增失败，请重试");
		}

	}

}
