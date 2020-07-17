package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.mapper.DormitoryMapper;
import com.lening.pojo.Dormitory;
import com.lening.service.DormitoryService;

@Service
public class DormitoryServiceImpl implements DormitoryService {

	@Autowired
	private DormitoryMapper dormitoryMapper;

	@Override
	public List<Dormitory> findAll() {

		List<Dormitory> list = dormitoryMapper.findAll();
		return list;
	}

	@Override
	public PageInfo<Dormitory> findPage(int page, int limit) {

		PageHelper.startPage(page, limit);

		List<Dormitory> list = dormitoryMapper.findAll();

		PageInfo<Dormitory> pageInfo = new PageInfo<Dormitory>(list);
		return pageInfo;
	}

	@Override
	public int save(Dormitory dormitory) {

		int insert = dormitoryMapper.insert(dormitory);
		return insert;
	}

	@Override
	public Dormitory findOne(String number, String name) {

		Dormitory dormitory = dormitoryMapper.findOne(number, name);
		return dormitory;
	}

}
