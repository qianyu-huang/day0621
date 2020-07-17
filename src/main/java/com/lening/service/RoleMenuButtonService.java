package com.lening.service;

import java.util.List;

import com.lening.pojo.RoleMenuButton;

public interface RoleMenuButtonService {

	List<RoleMenuButton> findByMenu(int menuId);

	List<RoleMenuButton> findByButton(int buttonId);

	RoleMenuButton findOne(RoleMenuButton roleMenuButton);

	List<RoleMenuButton> findByUser(int userId);
}
