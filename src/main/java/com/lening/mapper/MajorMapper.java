package com.lening.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lening.pojo.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);

	List<Major> findByCollege(int collegeId);

	Major findByIdAndCollege(@Param(value = "id") int id, @Param(value = "collegeId") int collegeId);
}