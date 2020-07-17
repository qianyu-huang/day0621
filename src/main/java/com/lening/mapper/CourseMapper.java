package com.lening.mapper;

import java.util.List;

import com.lening.pojo.Course;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

	List<Course> findAll();

	Course findByNumber(String number);
}