package com.lening.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lening.pojo.Button;
import com.lening.pojo.RoleMenuButton;
import com.lening.service.ButtonService;
import com.lening.service.RoleMenuButtonService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;

@RestController
@RequestMapping(value = "/button")
public class ButtonController {

	@Autowired
	private ButtonService buttonService;

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 按钮全查
	 * @return
	 * @return: LayuiData
	 */
	@RequestMapping(value = "/findAll")
	public LayuiData findAll() {

		System.out.println(123);

		List<Button> list = buttonService.findAll();

		return AjaxUtils.getLayuiData(list);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 按钮新增
	 * @param button
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Button button) {

		// 校验按钮名称
		if (StringUtils.isBlank(button.getButtonName()) == true) {
			return AjaxUtils.fail("按钮名称不能为空");
		}

		// 校验属性名称
		String attrName = button.getAttrName();
		if (StringUtils.isBlank(attrName) == true) {
			return AjaxUtils.fail("属性名称不能为空");
		} else {

			// 要求必须为大小写字母
			String regex = "[a-zA-Z]{1,}";

			if (attrName.matches(regex) == false) {

				return AjaxUtils.fail("属性名称只能为大写或小写字母");
			}
		}

		// 给按钮url属性赋值
		button.setButtonUrl("/" + attrName);

		// 新增按钮
		int save = buttonService.save(button);

		if (save > 0) {
			return AjaxUtils.success("按钮新增成功");
		} else {

			return AjaxUtils.fail("按钮新增失败，请重试");
		}

	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 按钮单条删除
	 * @param id
	 * @return
	 * @return: AjaxResponse
	 */
	@RequestMapping(value = "/delete")
	public AjaxResponse delete(Integer id) {

		if (id != null) {

			// 根据按钮id查询记录
			List<RoleMenuButton> list = roleMenuButtonService.findByButton(id);

			if (list.size() > 0) {
				return AjaxUtils.fail("角色菜单按钮关联表中存在与当前按钮关联的角色，需要先删除该按钮所关联的所有角色信息，再进行删除操作");
			} else {

				int delete = buttonService.delete(id);

				if (delete > 0) {
					return AjaxUtils.success("按钮删除成功");
				}
			}
		}

		return AjaxUtils.fail("按钮删除失败，请重试");
	}

	/**
	 * 
	 * @Title: toUpdate
	 * @Description: 将要修改的按钮信息存入session
	 * @param id
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/toUpdate")
	public AjaxResponse toUpdate(Integer id, HttpSession session) {

		if (id != null) {

			Button button = buttonService.findById(id);

			if (button != null) {

				session.setAttribute("updateButton", button);
				return AjaxUtils.success("跳转页面成功");
			}
		}

		return AjaxUtils.fail("进入修改页面失败，请重试");
	}

	/**
	 * 
	 * @Title: getButtonInfo
	 * @Description: 在修改页面上，获取按钮信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/getButtonInfo")
	public AjaxResponse getButtonInfo(HttpSession session) {

		Button button = (Button) session.getAttribute("updateButton");

		if (button != null) {
			return AjaxUtils.success(JSON.toJSONString(button));
		} else {
			return AjaxUtils.fail("获取按钮信息失败，请重试");
		}
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改按钮
	 * @param button
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/update")
	public AjaxResponse update(Button button) {

		// 校验id
		if (button.getId() == null) {
			return AjaxUtils.fail("按钮id不能为空");
		}

		// 校验按钮名称
		if (StringUtils.isBlank(button.getButtonName()) == true) {
			return AjaxUtils.fail("按钮名称不能为空");
		}

		// 校验属性名称
		String attrName = button.getAttrName();
		if (StringUtils.isBlank(attrName) == true) {
			return AjaxUtils.fail("属性名称不能为空");
		} else {

			// 要求必须为大小写字母
			String regex = "[a-zA-Z]{1,}";

			if (attrName.matches(regex) == false) {

				return AjaxUtils.fail("属性名称只能为大写或小写字母");
			}
		}

		// 给按钮url属性赋值
		button.setButtonUrl("/" + attrName);

		// 修改按钮
		int update = buttonService.update(button);

		if (update > 0) {
			return AjaxUtils.success("按钮修改成功");
		} else {
			return AjaxUtils.fail("按钮修改失败，请重试");
		}

	}

}
