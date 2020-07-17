package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lening.mapper.UserRoleMapper;
import com.lening.pojo.Role;
import com.lening.pojo.UserRole;
import com.lening.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public List<UserRole> findByRole(int roleId) {

		List<UserRole> list = userRoleMapper.findByRole(roleId);
		return list;
	}

	@Override
	public List<Role> findRoleList(int userId) {

		List<Role> list = userRoleMapper.findRoleList(userId);
		return list;
	}

}
