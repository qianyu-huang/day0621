package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lening.mapper.CollegeMapper;
import com.lening.pojo.College;
import com.lening.service.CollegeService;

@Service
public class CollegeServiceImpl implements CollegeService {

	@Autowired
	private CollegeMapper collegeMapper;

	@Override
	public List<College> findAll() {

		List<College> list = collegeMapper.findAll();
		return list;
	}

	@Override
	public College findById(int id) {

		College college = collegeMapper.selectByPrimaryKey(id);

		return college;
	}

}
