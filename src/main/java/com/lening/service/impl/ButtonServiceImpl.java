package com.lening.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lening.mapper.ButtonMapper;
import com.lening.mapper.RoleMenuButtonMapper;
import com.lening.pojo.Button;
import com.lening.pojo.RoleMenuButton;
import com.lening.service.ButtonService;

@Service
public class ButtonServiceImpl implements ButtonService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ButtonMapper buttonMapper;

	@Autowired
	private RoleMenuButtonMapper roleMenuButtonMapper;

	@Override
	public List<Button> findAll() {

		List<Button> list = buttonMapper.findAll();
		return list;
	}

	@Override
	public int save(Button button) {

		int insert = buttonMapper.insert(button);
		return insert;
	}

	@Override
	public int delete(int id) {

		int deleteByPrimaryKey = buttonMapper.deleteByPrimaryKey(id);
		return deleteByPrimaryKey;
	}

	@Override
	public Button findById(int id) {

		Button button = buttonMapper.selectByPrimaryKey(id);
		return button;
	}

	@Override
	@Transactional
	public int update(Button button) {

		try {

			Integer buttonId = button.getId();

			Button oldButton = buttonMapper.selectByPrimaryKey(buttonId);
			String oldButtonUrl = oldButton.getButtonUrl();

			String newButtonUrl = button.getButtonUrl();

			// 按钮url发生了变化
			if (newButtonUrl.equals(oldButtonUrl) == false) {

				// 修改角色菜单按钮关联表中和该按钮相关联的url信息
				List<RoleMenuButton> rmbList = roleMenuButtonMapper.findByButton(buttonId);

				for (RoleMenuButton roleMenuButton : rmbList) {
					String url = roleMenuButton.getUrl();

					String[] split = url.split("/");

					String newUrl = "/" + split[split.length - 2] + newButtonUrl;

					// 给url重新赋值
					roleMenuButton.setUrl(newUrl);

					roleMenuButtonMapper.updateByPrimaryKey(roleMenuButton);

				}

			}

			// 修改按钮
			buttonMapper.updateByPrimaryKey(button);

			return 1;
		} catch (Exception e) {

			logger.error("按钮修改失败", e);

			// 手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}
		return 0;
	}

}
