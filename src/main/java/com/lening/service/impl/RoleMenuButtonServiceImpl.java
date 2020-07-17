package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lening.mapper.RoleMenuButtonMapper;
import com.lening.pojo.RoleMenuButton;
import com.lening.service.RoleMenuButtonService;

@Service
public class RoleMenuButtonServiceImpl implements RoleMenuButtonService {

	@Autowired
	private RoleMenuButtonMapper roleMenuButtonMapper;

	@Override
	public List<RoleMenuButton> findByMenu(int menuId) {

		List<RoleMenuButton> list = roleMenuButtonMapper.findByMenu(menuId);
		return list;
	}

	@Override
	public List<RoleMenuButton> findByButton(int buttonId) {

		List<RoleMenuButton> list = roleMenuButtonMapper.findByButton(buttonId);
		return list;
	}

	@Override
	public RoleMenuButton findOne(RoleMenuButton roleMenuButton) {

		RoleMenuButton one = roleMenuButtonMapper.findOne(roleMenuButton);
		return one;
	}

	@Override
	public List<RoleMenuButton> findByUser(int userId) {

		List<RoleMenuButton> list = roleMenuButtonMapper.findByUser(userId);
		return list;
	}

}
