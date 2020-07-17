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
import com.lening.mapper.TeacherMapper;
import com.lening.mapper.UserMapper;
import com.lening.mapper.UserRoleMapper;
import com.lening.pojo.Role;
import com.lening.pojo.Teacher;
import com.lening.pojo.User;
import com.lening.pojo.UserRole;
import com.lening.service.TeacherService;
import com.lening.vo.TeacherVo;

@Service
public class TeacherServiceImpl implements TeacherService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TeacherMapper teacherMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	// 默认密码
	@Value(value = "${defaultPassword}")
	private String defaultPassword;

	// 讲师角色
	@Value(value = "${teacherRole}")
	private String teacherRole;

	@Override
	public List<Teacher> findAll() {

		List<Teacher> list = teacherMapper.findAll();
		return list;
	}

	@Override
	public PageInfo<TeacherVo> findPage(int page, int limit) {

		PageHelper.startPage(page, limit);

		List<TeacherVo> list = teacherMapper.findVo();

		PageInfo<TeacherVo> pageInfo = new PageInfo<TeacherVo>(list);

		return pageInfo;
	}

	@Override
	@Transactional
	public int save(Teacher teacher) {

		try {

			// 新增讲师
			teacherMapper.insert(teacher);

			// 到角色表查询是否存在讲师角色
			Role role = roleMapper.findByName(teacherRole);

			if (role != null) {

				// 用户表增加记录
				User user = new User();

				user.setPassword(defaultPassword);
				user.setRealname(teacher.getTeacherName());
				user.setSex(teacher.getSex());
				user.setUsername(teacher.getPhoneNumber());

				userMapper.insert(user);

				// 用户角色表增加记录，为当前用户分配讲师角色
				UserRole userRole = new UserRole();

				userRole.setUserId(user.getId());
				userRole.setRoleId(role.getId());

				userRoleMapper.insert(userRole);
			}

			return 1;

		} catch (Exception e) {

			logger.error("讲师增加失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}

		return 0;

	}

}
