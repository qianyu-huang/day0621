package com.lening.service;

import java.util.List;

import com.lening.pojo.Button;
import com.lening.pojo.Menu;
import com.lening.pojo.Role;
import com.lening.pojo.RoleMenuButton;
import com.lening.vo.MenuVo;
import com.lening.vo.RoleVo;

public interface RoleService {

	Role findById(int id);

	List<RoleVo> findVo();

	Role findByName(String roleName);

	int save(Role role);

	int delete(int id);

	int update(Role role);

	List<MenuVo> findMenuList(int roleId);

	List<Menu> findOtherMenuList(int roleId);

	int addMenu(int roleId, int menuId, String[] buttonIdArray);

	int deleteMenu(int roleId, int menuId);

	List<Button> findButtonList(int roleId, int menuId);

	List<Button> findOtherButtonList(int roleId, int menuId);

	int deleteButton(int rmbId);

	int addButton(RoleMenuButton roleMenuButton);

}
