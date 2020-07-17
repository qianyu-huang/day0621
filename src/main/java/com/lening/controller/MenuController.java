package com.lening.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lening.pojo.Menu;
import com.lening.pojo.RoleMenuButton;
import com.lening.service.MenuService;
import com.lening.service.RoleMenuButtonService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 菜单全查
	 * @return
	 * @return: LayuiData
	 */
	@RequestMapping(value = "/findAll")
	public LayuiData findAll() {

		List<Menu> list = menuService.findAll();

		return AjaxUtils.getLayuiData(list);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 菜单新增
	 * @param menu
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Menu menu) {

		// 校验菜单名称
		if (StringUtils.isBlank(menu.getMenuName()) == true) {
			return AjaxUtils.fail("菜单名称不能为空");
		}

		// 校验菜单描述
		if (StringUtils.isBlank(menu.getMenuDesc()) == true) {
			return AjaxUtils.fail("菜单描述不能为空");
		}

		// 校验菜单页面
		String menuPage = menu.getMenuPage();
		if (StringUtils.isBlank(menuPage) == true) {
			return AjaxUtils.fail("菜单页面不能为空");
		}else {
			
			//校验是否以.html结尾
			if (menuPage.endsWith(".html") == false) {

				return AjaxUtils.fail("菜单页面必须以.html结尾");
			} else {

				// 生成菜单url
				String[] split = menuPage.split("\\.");

				String menuUrl = "/" + split[0];

				// 给菜单url属性赋值
				menu.setMenuUrl(menuUrl);

			}
			
			
		}

		// 新增菜单
		int save = menuService.save(menu);

		if (save > 0) {
			return AjaxUtils.success("菜单新增成功");
		} else {

			return AjaxUtils.fail("菜单新增失败");
		}

	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 菜单单条删除
	 * @param id
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/delete")
	public AjaxResponse delete(Integer id) {

		if (id != null) {
			// 根据菜单id在角色菜单按钮表中查询相应的记录
			List<RoleMenuButton> list = roleMenuButtonService.findByMenu(id);

			if (list.size() > 0) {
				return AjaxUtils.fail("在角色菜单按钮关联表中存在与当前菜单关联的角色，需要先删除该菜单所关联的所有角色信息，再进行删除操作");
			}

			// 单条删除
			int delete = menuService.delete(id);

			if (delete > 0) {
				return AjaxUtils.success("菜单单条删除成功");
			}
		}

		return AjaxUtils.fail("菜单单条删除失败，请重试");
	}

	/**
	 * 
	 * @Title: toUpdate
	 * @Description: 将要修改菜单id保存到session中
	 * @param id
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/toUpdate")
	public AjaxResponse toUpdate(Integer id, HttpSession session) {

		if (id != null) {

			Menu menu = menuService.findById(id);

			if (menu != null) {

				session.setAttribute("updateMenu", menu);
				return AjaxUtils.success("跳转页面成功");
			}
		}

		return AjaxUtils.fail("进入修改页面失败，请重试");
	}

	/**
	 * 
	 * @Title: getMenuInfo
	 * @Description: 在修改页面上，获取菜单信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/getMenuInfo")
	public AjaxResponse getMenuInfo(HttpSession session) {

		Menu menu = (Menu) session.getAttribute("updateMenu");

		if (menu != null) {
			return AjaxUtils.success(JSON.toJSONString(menu));
		} else {
			return AjaxUtils.fail("获取菜单信息失败，请重试");
		}
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 菜单修改
	 * @param menu
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/update")
	public AjaxResponse update(Menu menu) {

		// 校验菜单id
		if (menu.getId() == null) {
			return AjaxUtils.fail("菜单id不能为空");
		}

		// 校验菜单名称
		if (StringUtils.isBlank(menu.getMenuName()) == true) {
			return AjaxUtils.fail("菜单名称不能为空");
		}

		// 校验菜单描述
		if (StringUtils.isBlank(menu.getMenuDesc()) == true) {
			return AjaxUtils.fail("菜单描述不能为空");
		}

		// 校验菜单页面
		String menuPage = menu.getMenuPage();
		if (StringUtils.isBlank(menuPage) == true) {
			return AjaxUtils.fail("菜单页面不能为空");
		} else {

			// 校验是否以.html结尾
			if (menuPage.endsWith(".html") == false) {

				return AjaxUtils.fail("菜单页面必须以.html结尾");
			} else {

				// 生成菜单url
				String[] split = menuPage.split("\\.");

				String menuUrl = "/" + split[0];

				// 给菜单url属性赋值
				menu.setMenuUrl(menuUrl);

			}

		}

		// 修改菜单
		int update = menuService.update(menu);

		if (update > 0) {
			return AjaxUtils.success("菜单修改成功");
		} else {

			return AjaxUtils.fail("菜单修改失败");
		}

	}

}
