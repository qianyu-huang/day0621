package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.mapper.CourseMapper;
import com.lening.pojo.Course;
import com.lening.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Override
	public List<Course> findAll() {

		List<Course> list = courseMapper.findAll();

		return list;
	}

	@Override
	public PageInfo<Course> findPage(int page, int limit) {

		PageHelper.startPage(page, limit);

		List<Course> list = courseMapper.findAll();

		PageInfo<Course> pageInfo = new PageInfo<Course>(list);
		return pageInfo;
	}

	@Override
	public int save(Course course) {

		int insert = courseMapper.insert(course);
		return insert;
	}

	@Override
	public Course findByNumber(String number) {

		Course course = courseMapper.findByNumber(number);
		return course;
	}

}
