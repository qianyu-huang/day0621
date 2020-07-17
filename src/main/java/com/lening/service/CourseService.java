package com.lening.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Course;

public interface CourseService {

	List<Course> findAll();

	PageInfo<Course> findPage(int page, int limit);

	int save(Course course);

	Course findByNumber(String number);
}
