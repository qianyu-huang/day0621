package com.lening.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lening.pojo.Dormitory;

public interface DormitoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dormitory record);

    int insertSelective(Dormitory record);

    Dormitory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dormitory record);

    int updateByPrimaryKey(Dormitory record);

	List<Dormitory> findAll();

	Dormitory findOne(@Param(value = "number") String number, @Param(value = "name") String name);
}