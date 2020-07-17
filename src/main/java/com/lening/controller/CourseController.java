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
import com.lening.pojo.Course;
import com.lening.service.CourseService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.utils.RegexUtils;

@RestController
@RequestMapping(value = "/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 课程全查
	 * @return
	 * @return: List<Course>
	 */
	@GetMapping(value = "/findAll")
	public List<Course> findAll() {

		List<Course> list = courseService.findAll();

		return list;
	}

	/**
	 * 
	 * @Title: findPage
	 * @Description: 课程分页查询
	 * @param page
	 * @param limit
	 * @return
	 * @return: LayuiData
	 * @throws InterruptedException
	 */
	@GetMapping(value = "/findPage")
	public LayuiData findPage(Integer page, Integer limit) throws InterruptedException {

		// 模拟网络卡顿
		// Thread.sleep(1000);

		if (page != null && limit != null) {

			PageInfo<Course> pageInfo = courseService.findPage(page, limit);

			return AjaxUtils.getLayuiData(pageInfo);
		}

		return null;
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 课程新增
	 * @param course
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Course course) {

		// 校验课程名称
		if (StringUtils.isBlank(course.getCourseName()) == true) {
			return AjaxUtils.fail("请输入课程名称");
		}

		// 校验课程代码
		String courseNumber = course.getCourseNumber();
		if (RegexUtils.isNumber(courseNumber, 6) == false) {

			return AjaxUtils.fail("课程代码不合法");
		} else {

			// 校验课程代码的唯一性
			Course find = courseService.findByNumber(courseNumber);

			if (find != null) {
				return AjaxUtils.fail("课程代码已存在");
			}

		}

		// 校验课程类型
		String type = course.getType();
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("公共课");
		typeList.add("选修课");
		typeList.add("专业课");

		if (typeList.contains(type) == false) {

			return AjaxUtils.fail("课程类型不合法");
		}

		int save = courseService.save(course);

		if (save > 0) {
			return AjaxUtils.success("课程新增成功");
		} else {
			return AjaxUtils.fail("课程新增失败，请重试");
		}
	}

}
