package com.lening.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lening.mapper.MenuMapper;
import com.lening.mapper.RoleMenuButtonMapper;
import com.lening.pojo.Menu;
import com.lening.pojo.RoleMenuButton;
import com.lening.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RoleMenuButtonMapper roleMenuButtonMapper;

	@Override
	public List<Menu> findAll() {

		List<Menu> list = menuMapper.findAll();

		return list;
	}

	@Override
	public int save(Menu menu) {

		int insert = menuMapper.insert(menu);
		return insert;
	}

	@Override
	public int delete(int id) {

		int deleteByPrimaryKey = menuMapper.deleteByPrimaryKey(id);
		return deleteByPrimaryKey;
	}

	@Override
	public Menu findById(int id) {

		Menu menu = menuMapper.selectByPrimaryKey(id);
		return menu;
	}

	@Override
	@Transactional
	public int update(Menu menu) {

		try {

			Integer id = menu.getId();
			String newMenuUrl = menu.getMenuUrl();

			// 判断菜单url是否发生变化
			Menu oldMenu = menuMapper.selectByPrimaryKey(id);
			String oldMenuUrl = oldMenu.getMenuUrl();

			if (oldMenuUrl.equals(newMenuUrl) == false) {

				// 角色菜单按钮关联表相关记录发生变化
				List<RoleMenuButton> rmbList = roleMenuButtonMapper.findByMenu(id);

				// 修改url
				for (RoleMenuButton roleMenuButton : rmbList) {

					String oldUrl = roleMenuButton.getUrl();
					String[] split = oldUrl.split("/");

					String newUrl = newMenuUrl + "/" + split[split.length - 1];

					roleMenuButton.setUrl(newUrl);

					roleMenuButtonMapper.updateByPrimaryKey(roleMenuButton);
				}

			}

			// 修改菜单
			menuMapper.updateByPrimaryKey(menu);

			return 1;

		} catch (Exception e) {

			logger.error("菜单修改失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return 0;
	}

}
