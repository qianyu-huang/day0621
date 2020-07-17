package com.lening.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Grade;
import com.lening.vo.GradeVo;

public interface GradeService {

	List<Grade> findAll();

	PageInfo<GradeVo> findPage(int page, int limit);

	int save(Grade grade);

	String getClassCode(int majorId);

	int getEndNumber(int majorId);

	Grade findById(int id);
}
