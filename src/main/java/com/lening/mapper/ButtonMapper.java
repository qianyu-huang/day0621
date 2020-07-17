package com.lening.mapper;

import com.lening.pojo.Button;

import java.util.List;

public interface ButtonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Button record);

    int insertSelective(Button record);

    Button selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Button record);

    int updateByPrimaryKey(Button record);

	List<Button> findAll();
}