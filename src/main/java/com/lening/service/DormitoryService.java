package com.lening.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Dormitory;

public interface DormitoryService {

	List<Dormitory> findAll();

	PageInfo<Dormitory> findPage(int page, int limit);

	int save(Dormitory dormitory);

	Dormitory findOne(String number, String name);
}
