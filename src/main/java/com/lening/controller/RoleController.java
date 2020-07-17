package com.lening.controller;

import com.alibaba.fastjson.JSON;
import com.lening.pojo.*;
import com.lening.service.*;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.vo.MenuVo;
import com.lening.vo.RoleVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;

	@Autowired
	private ButtonService buttonService;

	/**
	 * 
	 * @Title: findVo
	 * @Description: 查询角色详细信息
	 * @return
	 * @return: LayuiData
	 */
	@RequestMapping(value = "/findVo")
	public LayuiData findVo() {

		List<RoleVo> list = roleService.findVo();

		return AjaxUtils.getLayuiData(list);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 新增角色
	 * @param role
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Role role) {

		// 校验角色名称
		String roleName = role.getRoleName();
		if (StringUtils.isBlank(roleName) == true) {

			return AjaxUtils.fail("角色名称为空");
		} else {

			// 校验角色名称唯一性
			Role find = roleService.findByName(roleName);

			if (find != null) {

				return AjaxUtils.fail("角色名称已存在");
			}

		}

		// 校验角色描述
		if (StringUtils.isBlank(role.getRoleDesc()) == true) {

			return AjaxUtils.fail("角色描述不能为空");
		}

		// 新增角色
		int save = roleService.save(role);

		if (save > 0) {
			return AjaxUtils.success("角色新增成功");
		} else {
			return AjaxUtils.fail("角色新增失败");
		}
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 角色单条删除
	 * @param id
	 * @return
	 * @return: AjaxResponse
	 */
	@RequestMapping(value = "/delete")
	public AjaxResponse delete(Integer id) {

		// 在用户角色关联表中校验是否存在与之关联的用户
		List<UserRole> list = userRoleService.findByRole(id);

		if (list.isEmpty() == false) {

			return AjaxUtils.fail("已经有用户使用该角色，请先删除与之关联的用户信息");
		}

		// 删除角色信息
		int delete = roleService.delete(id);

		if (delete > 0) {
			return AjaxUtils.success("角色删除成功");
		} else {
			return AjaxUtils.fail("角色删除失败");
		}

	}

	/**
	 * 
	 * @Title: toUpdate
	 * @Description: 进入修改页面
	 * @param id
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@RequestMapping(value = "/toUpdate")
	public AjaxResponse toUpdate(Integer id, HttpSession session) {

		if (id != null) {

			Role role = roleService.findById(id);

			if (role != null) {

				session.setAttribute("updateRole", role);

				return AjaxUtils.success("进入角色修改页面成功");
			}
		}

		return AjaxUtils.fail("进入角色修改页面失败");
	}

	/**
	 * 
	 * @Title: getRoleInfo
	 * @Description: 在修改页面上，获取角色信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@GetMapping(value = "/getRoleInfo")
	public AjaxResponse getRoleInfo(HttpSession session) {

		Role role = (Role) session.getAttribute("updateRole");

		if (role != null) {

			String jsonString = JSON.toJSONString(role);

			return AjaxUtils.success(jsonString);
		}

		return AjaxUtils.fail("获取角色信息失败");

	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改角色
	 * @param role
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/update")
	public AjaxResponse update(Role role) {

		// 校验角色id
		Integer id = role.getId();
		if (id == null) {

			return AjaxUtils.fail("角色id不能为空");
		} else {

			Role find = roleService.findById(id);

			if (find == null) {

				return AjaxUtils.fail("角色不存在");
			}
		}

		// 校验角色名称
		String roleName = role.getRoleName();
		if (StringUtils.isBlank(roleName) == true) {

			return AjaxUtils.fail("角色名称为空");
		} else {

			// 校验角色名称唯一性
			Role find = roleService.findByName(roleName);

			if (find != null) {

				return AjaxUtils.fail("角色名称已存在");
			}

		}

		// 校验角色描述
		if (StringUtils.isBlank(role.getRoleDesc()) == true) {

			return AjaxUtils.fail("角色描述不能为空");
		}

		// 修改角色
		int update = roleService.update(role);

		if (update > 0) {
			return AjaxUtils.success("角色修改成功");
		} else {
			return AjaxUtils.fail("角色修改失败");
		}
	}

	/**
	 * 
	 * @Title: findMenuList
	 * @Description: 根据角色查询已授权的菜单列表
	 * @param roleId
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findMenuList")
	public LayuiData findMenuList(Integer roleId) {

		if (roleId != null) {

			List<MenuVo> list = roleService.findMenuList(roleId);

			return AjaxUtils.getLayuiData(list);
		}

		return null;
	}

	/**
	 * 
	 * @Title: saveRoleInfo
	 * @Description: 进入菜单授权页面，将当前要授权的角色信息存入session
	 * @param id
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/saveRoleInfo")
	public AjaxResponse saveRoleInfo(Integer id, HttpSession session) {

		// 校验角色id
		if (id == null) {

			return AjaxUtils.fail("角色id不存在");
		} else {

			Role role = roleService.findById(id);

			if (role == null) {

				return AjaxUtils.fail("角色不存在");
			} else {

				session.setAttribute("roleInfo", role);

				return AjaxUtils.success("进入菜单授权页面成功");

			}
		}

	}

	/**
	 * 
	 * @Title: getRole
	 * @Description: 在菜单授权页面上，获取当前角色信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@GetMapping(value = "/getRole")
	public AjaxResponse getRole(HttpSession session) {

		Role role = (Role) session.getAttribute("roleInfo");

		if (role != null) {

			return AjaxUtils.success(JSON.toJSONString(role));
		} else {

			return AjaxUtils.fail("进入菜单授权页面失败");
		}

	}

	/**
	 * 
	 * @Title: findOtherMenuList
	 * @Description: 查询当前角色未授权的菜单列表
	 * @param roleId
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findOtherMenuList")
	public LayuiData findOtherMenuList(Integer roleId) {

		if (roleId != null) {

			List<Menu> list = roleService.findOtherMenuList(roleId);

			return AjaxUtils.getLayuiData(list);
		}

		return null;
	}

	/**
	 * 
	 * @Title: saveMenuInfo
	 * @Description: 进入新增菜单按钮选择页面，将当前菜单信息存入session
	 * @param menuId
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/saveMenuInfo")
	public AjaxResponse saveMenuInfo(Integer menuId, HttpSession session) {

		// 校验菜单id
		if (menuId == null) {

			return AjaxUtils.fail("菜单id不存在");
		} else {

			Menu menu = menuService.findById(menuId);

			if (menu == null) {

				return AjaxUtils.fail("菜单不存在");
			} else {

				session.setAttribute("menuInfo", menu);

				return AjaxUtils.success("进入新增菜单按钮选择页面成功");

			}
		}

	}

	/**
	 * 
	 * @Title: getMenu
	 * @Description: 在新增菜单按钮选择页面上，获取当前菜单信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@GetMapping(value = "/getMenu")
	public AjaxResponse getMenu(HttpSession session) {

		Menu menu = (Menu) session.getAttribute("menuInfo");

		if (menu != null) {

			return AjaxUtils.success(JSON.toJSONString(menu));
		} else {

			return AjaxUtils.fail("进入新增菜单按钮选择页面失败");
		}

	}

	/**
	 * 
	 * @Title: addMenu
	 * @Description: 为当前角色增加菜单
	 * @param roleId
	 * @param menuId
	 * @param buttonIds
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/addMenu")
	public AjaxResponse addMenu(Integer roleId, Integer menuId, String buttonIds) {

		// 校验角色id
		if (roleId == null) {
			return AjaxUtils.fail("角色id为空");
		} else {

			Role role = roleService.findById(roleId);

			if (role == null) {

				return AjaxUtils.fail("角色不存在");
			}

		}

		// 校验菜单id
		if (menuId == null) {
			return AjaxUtils.fail("菜单id为空");
		} else {

			Menu menu = menuService.findById(menuId);

			if (menu == null) {
				return AjaxUtils.fail("菜单不存在");
			}
		}

		// 校验buttonIds
		String[] buttonIdArray = null;
		
		if (StringUtils.isBlank(buttonIds) == true) {

			return AjaxUtils.fail("按钮id为空");
		} else {

			if (buttonIds.startsWith(",") || buttonIds.endsWith(",")) {
				return AjaxUtils.fail("按钮id不能以逗号开头或结尾");
			} else {

				// 定义正则表达式
				String regex = "\\d{1,}";

				buttonIdArray = buttonIds.split(",");

				ArrayList<String> buttonIdList = new ArrayList<String>();

				RoleMenuButton roleMenuButton = new RoleMenuButton();
				roleMenuButton.setRoleId(roleId);
				roleMenuButton.setMenuId(menuId);

				for (String buttonId : buttonIdArray) {

					// 如果按钮id不是数字或者为空，则返回失败
					if (buttonId.matches(regex) == false || StringUtils.isBlank(buttonId) == true) {

						return AjaxUtils.fail("按钮id不合法");
					}

					// 保证按钮id不重复
					if (buttonIdList.contains(buttonId) == false) {
						buttonIdList.add(buttonId);
					} else {
						return AjaxUtils.fail("按钮id重复");
					}

					// 保证按钮存在
					Button button = buttonService.findById(new Integer(buttonId));
					if (button == null) {
						return AjaxUtils.fail("按钮不存在");
					}

					// 根据角色id，菜单id，按钮id到角色菜单按钮关联表中查询记录
					roleMenuButton.setButtonId(new Integer(buttonId));
					RoleMenuButton find = roleMenuButtonService.findOne(roleMenuButton);

					if (find != null) {
						return AjaxUtils.fail("角色菜单按钮关联表相应记录已存在");
					}

				}

			}

		}
		
		
		//新增菜单
		int addMenu = roleService.addMenu(roleId, menuId, buttonIdArray);

		if (addMenu > 0) {
			return AjaxUtils.success("当前角色新增菜单成功");
		} else {
			return AjaxUtils.fail("当前角色新增菜单失败");
		}

	}

	/**
	 * 
	 * @Title: deleteMenu
	 * @Description: 为当前角色删除菜单
	 * @param roleId
	 * @param menuId
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/deleteMenu")
	public AjaxResponse deleteMenu(Integer roleId, Integer menuId) {

		if (roleId != null && menuId != null) {

			int deleteMenu = roleService.deleteMenu(roleId, menuId);

			if (deleteMenu > 0) {
				return AjaxUtils.success("当前角色删除菜单成功");
			}
		}

		return AjaxUtils.fail("当前角色删除菜单失败");
	}

	/**
	 * 
	 * @Title: findButtonList
	 * @Description: 根据角色id和菜单id查询已授权的按钮列表
	 * @param roleId
	 * @param menuId
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findButtonList")
	public LayuiData findButtonList(Integer roleId, Integer menuId) {

		if (roleId != null && menuId != null) {

			List<Button> list = roleService.findButtonList(roleId, menuId);

			return AjaxUtils.getLayuiData(list);
		}

		return null;
	}

	/**
	 * 
	 * @Title: findOtherButtonList
	 * @Description: 根据角色id和菜单id查询未授权的按钮列表
	 * @param roleId
	 * @param menuId
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findOtherButtonList")
	public LayuiData findOtherButtonList(Integer roleId, Integer menuId) {

		if (roleId != null && menuId != null) {

			List<Button> list = roleService.findOtherButtonList(roleId, menuId);

			return AjaxUtils.getLayuiData(list);
		}

		return null;
	}

	/**
	 * 
	 * @Title: deleteButton
	 * @Description: 当前菜单删除按钮
	 * @param roleMenuButton
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/deleteButton")
	public AjaxResponse deleteButton(RoleMenuButton roleMenuButton) {

		RoleMenuButton findOne = roleMenuButtonService.findOne(roleMenuButton);

		int deleteButton = roleService.deleteButton(findOne.getId());

		if (deleteButton > 0) {
			return AjaxUtils.success("当前菜单按钮删除成功");
		} else {

			return AjaxUtils.fail("当前菜单按钮删除失败");
		}
	}

	/**
	 * 
	 * @Title: addButton
	 * @Description: 为当前菜单增加按钮
	 * @param roleMenuButton
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/addButton")
	public AjaxResponse addButton(RoleMenuButton roleMenuButton) {

		// 校验角色id
		Integer roleId = roleMenuButton.getRoleId();
		Role role = roleService.findById(roleId);
		if (role == null) {
			return AjaxUtils.fail("角色不存在");
		}

		// 校验菜单id
		Integer menuId = roleMenuButton.getMenuId();
		Menu menu = menuService.findById(menuId);
		if (menu == null) {
			return AjaxUtils.fail("菜单不存在");
		}

		// 校验按钮id
		Integer buttonId = roleMenuButton.getButtonId();
		Button button = buttonService.findById(buttonId);
		if (button == null) {
			return AjaxUtils.fail("按钮不存在");
		}

		RoleMenuButton findOne = roleMenuButtonService.findOne(roleMenuButton);

		if (findOne != null) {

			return AjaxUtils.fail("角色菜单按钮关联表记录已存在");
		}

		int addButton = roleService.addButton(roleMenuButton);

		if (addButton > 0) {
			return AjaxUtils.success("当前菜单按钮增加成功");
		} else {

			return AjaxUtils.fail("当前菜单按钮增加失败");
		}
	}

}
