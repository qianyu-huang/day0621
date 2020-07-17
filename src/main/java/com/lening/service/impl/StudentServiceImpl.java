package com.lening.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.mapper.RoleMapper;
import com.lening.mapper.StudentMapper;
import com.lening.mapper.UserMapper;
import com.lening.mapper.UserRoleMapper;
import com.lening.pojo.Role;
import com.lening.pojo.Student;
import com.lening.pojo.User;
import com.lening.pojo.UserRole;
import com.lening.service.StudentService;
import com.lening.vo.StudentVo;

@Service
public class StudentServiceImpl implements StudentService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	// 默认密码
	@Value(value = "${defaultPassword}")
	private String defaultPassword;

	// 学生角色
	@Value(value = "${studentRole}")
	private String studentRole;

	@Override
	public List<StudentVo> findVo() {

		List<StudentVo> list = studentMapper.findVo();
		return list;
	}

	@Override
	public PageInfo<StudentVo> findPage(int page, int limit) {

		PageHelper.startPage(page, limit);

		List<StudentVo> list = studentMapper.findVo();

		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(list);

		return pageInfo;
	}

	@Override
	public PageInfo<StudentVo> findByCase(Student student, int page, int limit) {

		PageHelper.startPage(page, limit);

		List<StudentVo> list = studentMapper.findByCase(student);

		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(list);
		return pageInfo;
	}

	@Override
	@Transactional
	public int save(Student student) {

		try {

			// 增加学生记录
			studentMapper.insert(student);

			// 查询角色表，查看是否存在学生角色，如果不存在，两张表都不会进行增加操作
			Role role = roleMapper.findByName(studentRole);

			if (role != null) {

				// 用户表增加记录
				User user = new User();

				user.setPassword(defaultPassword);
				user.setRealname(student.getStudentName());
				user.setSex(student.getSex());
				user.setUsername(student.getStudentNumber());

				userMapper.insert(user);

				// 用户角色关联表增加记录
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(role.getId());

				userRoleMapper.insert(userRole);
			}

			return 1;

		} catch (Exception e) {

			logger.error("学生增加失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return 0;

	}

	@Override
	public int getStudentCount(int gradeId) {

		int studentCount = studentMapper.getStudentCount(gradeId);
		return studentCount;
	}

}
