package com.lening.mapper;

import java.util.List;

import com.lening.pojo.Grade;
import com.lening.vo.GradeVo;

public interface GradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);

	List<Grade> findAll();

	List<GradeVo> findVo();

	String getClassCode(int majorId);

	int getEndNumber(int majorId);
}