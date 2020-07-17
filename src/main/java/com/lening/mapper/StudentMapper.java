package com.lening.mapper;

import java.util.List;

import com.lening.pojo.Student;
import com.lening.vo.StudentVo;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

	List<StudentVo> findVo();

	List<StudentVo> findByCase(Student student);

	int getStudentCount(int gradeId);
}