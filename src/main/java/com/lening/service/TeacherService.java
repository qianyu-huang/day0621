package com.lening.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Teacher;
import com.lening.vo.TeacherVo;

public interface TeacherService {

	List<Teacher> findAll();

	PageInfo<TeacherVo> findPage(int page, int limit);

	int save(Teacher teacher);
}
