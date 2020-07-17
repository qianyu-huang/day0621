package com.lening.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lening.mapper.UserMapper;
import com.lening.mapper.UserRoleMapper;
import com.lening.pojo.Role;
import com.lening.pojo.User;
import com.lening.pojo.UserRole;
import com.lening.service.UserService;
import com.lening.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<User> findAll() {

		List<User> list = userMapper.findAll();

		return list;
	}

	@Override
	public List<UserVo> findVo() {

		List<UserVo> list = userMapper.findVo();
		return list;
	}

	@Override
	public User findByUsername(String username) {

		User user = userMapper.findByUsername(username);
		return user;
	}

	@Override
	public int save(User user) {

		int insert = userMapper.insert(user);
		return insert;
	}

	@Override
	@Transactional
	public int delete(int id) {

		try {

			// 用户表记录删除
			userMapper.deleteByPrimaryKey(id);

			// 用户角色关联表相关记录删除
			userRoleMapper.deleteByUser(id);

			return 1;
		} catch (Exception e) {

			logger.error("学生增加失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return 0;
	}

	@Override
	public User findById(int id) {

		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}

	@Override
	public List<Role> findRoleList(int userId) {

		List<Role> list = userRoleMapper.findRoleList(userId);
		return list;
	}

	@Override
	public List<Role> findOtherRoleList(int userId) {

		List<Role> list = userRoleMapper.findOtherRoleList(userId);
		return list;
	}

	@Override
	public int addRole(UserRole userRole) {

		int insert = userRoleMapper.insert(userRole);
		return insert;
	}

	@Override
	public int deleteRole(UserRole userRole) {

		int deleteRole = userRoleMapper.deleteRole(userRole);
		return deleteRole;
	}

	@Override
	public int update(User user) {

		int updateByPrimaryKey = userMapper.updateByPrimaryKey(user);
		return updateByPrimaryKey;
	}

}
