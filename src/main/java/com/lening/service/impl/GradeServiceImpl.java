package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.mapper.GradeMapper;
import com.lening.pojo.Grade;
import com.lening.service.GradeService;
import com.lening.vo.GradeVo;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeMapper gradeMapper;

	@Override
	public List<Grade> findAll() {

		List<Grade> list = gradeMapper.findAll();
		return list;
	}

	@Override
	public PageInfo<GradeVo> findPage(int page, int limit) {

		PageHelper.startPage(page, limit);

		List<GradeVo> list = gradeMapper.findVo();

		PageInfo<GradeVo> pageInfo = new PageInfo<GradeVo>(list);

		return pageInfo;
	}

	@Override
	public int save(Grade grade) {

		int insert = gradeMapper.insert(grade);
		return insert;
	}

	@Override
	public String getClassCode(int majorId) {

		String classCode = gradeMapper.getClassCode(majorId);
		return classCode;
	}

	@Override
	public int getEndNumber(int majorId) {

		int endNumber = gradeMapper.getEndNumber(majorId);
		return endNumber;
	}

	@Override
	public Grade findById(int id) {

		Grade grade = gradeMapper.selectByPrimaryKey(id);
		return grade;
	}

}
