package com.lening.mapper;

import java.util.List;

import com.lening.pojo.Teacher;
import com.lening.vo.TeacherVo;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

	List<Teacher> findAll();

	List<TeacherVo> findVo();
}