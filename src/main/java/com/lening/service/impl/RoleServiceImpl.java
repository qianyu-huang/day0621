package com.lening.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lening.mapper.ButtonMapper;
import com.lening.mapper.MenuMapper;
import com.lening.mapper.RoleMapper;
import com.lening.mapper.RoleMenuButtonMapper;
import com.lening.pojo.Button;
import com.lening.pojo.Menu;
import com.lening.pojo.Role;
import com.lening.pojo.RoleMenuButton;
import com.lening.service.RoleService;
import com.lening.vo.MenuVo;
import com.lening.vo.RoleVo;

@Service
public class RoleServiceImpl implements RoleService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private ButtonMapper buttonMapper;

	@Autowired
	private RoleMenuButtonMapper roleMenuButtonMapper;

	@Override
	public Role findById(int id) {

		Role role = roleMapper.selectByPrimaryKey(id);
		return role;
	}

	@Override
	public List<RoleVo> findVo() {

		List<RoleVo> list = roleMapper.findVo();
		return list;
	}

	@Override
	public Role findByName(String roleName) {

		Role role = roleMapper.findByName(roleName);
		return role;
	}

	@Override
	public int save(Role role) {

		int insert = roleMapper.insert(role);
		return insert;
	}

	@Override
	@Transactional
	public int delete(int id) {

		try {

			// 角色表删除记录
			roleMapper.deleteByPrimaryKey(id);

			// 角色菜单按钮关联表删除相关记录
			roleMenuButtonMapper.deleteByRole(id);

			return 1;
		} catch (Exception e) {
			logger.error("角色删除失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return 0;
	}

	@Override
	public int update(Role role) {

		int update = roleMapper.updateByPrimaryKey(role);
		return update;
	}

	@Override
	public List<MenuVo> findMenuList(int roleId) {

		List<MenuVo> list = roleMapper.findMenuList(roleId);
		return list;
	}

	@Override
	public List<Menu> findOtherMenuList(int roleId) {

		List<Menu> list = roleMapper.findOtherMenuList(roleId);
		return list;
	}

	@Override
	@Transactional
	public int addMenu(int roleId, int menuId, String[] buttonIdArray) {

		try {

			for (String buttonId : buttonIdArray) {

				RoleMenuButton roleMenuButton = new RoleMenuButton();
				roleMenuButton.setRoleId(roleId);
				roleMenuButton.setMenuId(menuId);
				roleMenuButton.setButtonId(new Integer(buttonId));

				// 拼接url
				Menu menu = menuMapper.selectByPrimaryKey(menuId);
				Button button = buttonMapper.selectByPrimaryKey(new Integer(buttonId));
				String url = menu.getMenuUrl() + button.getButtonUrl();

				roleMenuButton.setUrl(url);

				roleMenuButtonMapper.insert(roleMenuButton);

			}

			return 1;
		} catch (Exception e) {
			logger.error("当前角色新增菜单失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return 0;

	}

	@Override
	public int deleteMenu(int roleId, int menuId) {

		int deleteByRoleAndMenu = roleMenuButtonMapper.deleteByRoleAndMenu(roleId, menuId);

		return deleteByRoleAndMenu;
	}

	@Override
	public List<Button> findButtonList(int roleId, int menuId) {

		List<Button> list = roleMapper.findButtonList(roleId, menuId);
		return list;
	}

	@Override
	public List<Button> findOtherButtonList(int roleId, int menuId) {

		List<Button> list = roleMapper.findOtherButtonList(roleId, menuId);
		return list;
	}

	@Override
	public int deleteButton(int rmbId) {

		int deleteByPrimaryKey = roleMenuButtonMapper.deleteByPrimaryKey(rmbId);
		return deleteByPrimaryKey;
	}

	@Override
	public int addButton(RoleMenuButton roleMenuButton) {

		// 拼接url
		Menu menu = menuMapper.selectByPrimaryKey(roleMenuButton.getMenuId());
		Button button = buttonMapper.selectByPrimaryKey(roleMenuButton.getButtonId());
		String url = menu.getMenuUrl() + button.getButtonUrl();

		roleMenuButton.setUrl(url);

		int insert = roleMenuButtonMapper.insert(roleMenuButton);
		return insert;
	}

}
