package com.lening.mapper;

import java.util.List;

import com.lening.pojo.College;

public interface CollegeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(College record);

    int insertSelective(College record);

    College selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(College record);

    int updateByPrimaryKey(College record);

	List<College> findAll();
}