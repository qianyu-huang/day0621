package com.lening.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Student;
import com.lening.vo.StudentVo;

public interface StudentService {

	List<StudentVo> findVo();

	PageInfo<StudentVo> findPage(int page, int limit);

	PageInfo<StudentVo> findByCase(Student student, int page, int limit);

	int save(Student student);

	int getStudentCount(int gradeId);
}
