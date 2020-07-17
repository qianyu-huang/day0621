package com.lening.controller;

import com.alibaba.fastjson.JSON;
import com.lening.pojo.Button;
import com.lening.pojo.Role;
import com.lening.pojo.User;
import com.lening.service.RoleService;
import com.lening.service.UserRoleService;
import com.lening.service.UserService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.vo.MenuVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/access")
public class AccessController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @Title: login 
	 * @Description: 登录系统
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/login")
	public AjaxResponse login(String username,String password,HttpSession session) {
		
		if(StringUtils.isBlank(username)) {
			return AjaxUtils.fail("用户名为空");
		}
		
		if(StringUtils.isBlank(password)) {
			
			return AjaxUtils.fail("密码为空");
		}
		
		//根据用户名查询记录
		User user = userService.findByUsername(username);
		
		if(user==null) {
			
			return AjaxUtils.fail("用户名不存在");
		}else {
			
			//校验密码
			if(password.equals(user.getPassword())==false) {
				return AjaxUtils.fail("密码错误");
			}else {
				
				// 校验该用户是否拥有角色
				List<Role> list = userRoleService.findRoleList(user.getId());

				if (list.isEmpty()) {
					return AjaxUtils.fail("该用户尚未拥有任何角色，请先分配角色，再登录系统");
				}

				//将用户信息存入session
				session.setAttribute("user", user);
				
				return AjaxUtils.success("登录成功");
			}
		}
		
	}

	/**
	 * 
	 * @Title: getUserInfo
	 * @Description: 从session中获取登录的用户信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@GetMapping(value = "/getUserInfo")
	public AjaxResponse getUserInfo(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {

			return AjaxUtils.success(JSON.toJSONString(user));
		} else {

			return AjaxUtils.fail("获取用户信息失败");
		}
	}

	/**
	 * 
	 * @Title: findRoleList
	 * @Description: 根据用户id查询对应的角色列表
	 * @param userId
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/findRoleList")
	public AjaxResponse findRoleList(Integer userId) {

		if (userId == null) {
			return AjaxUtils.fail("用户id为空");
		}

		List<Role> list = userRoleService.findRoleList(userId);

		if (list.isEmpty()) {
			return AjaxUtils.fail("用户尚未拥有角色");
		} else {
			return AjaxUtils.success(JSON.toJSONString(list));
		}

	}

	/**
	 * 
	 * @Title: findMenuList
	 * @Description: 根据角色加载对应的菜单列表
	 * @param roleId
	 * @return
	 * @return: LayuiData
	 */
	@PostMapping(value = "/findMenuList")
	public LayuiData findMenuList(Integer roleId) {

		if (roleId == null) {
			return null;
		} else {

			List<MenuVo> list = roleService.findMenuList(roleId);

			return AjaxUtils.getLayuiData(list);
		}
	}

	/**
	 * 
	 * @Title: exit
	 * @Description: 退出系统
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/exit")
	public AjaxResponse exit(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			session.removeAttribute("user");

			return AjaxUtils.success("退出系统成功");
		} else {

			return AjaxUtils.fail("退出系统失败，请重试");
		}
	}

	/**
	 * 
	 * @Title: changePassword
	 * @Description: 修改密码
	 * @param newPwd
	 * @param oldPwd
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/changePassword")
	public AjaxResponse changePassword(String newPwd, String oldPwd, HttpSession session) {

		// 非空校验
		if (StringUtils.isBlank(oldPwd)) {
			return AjaxUtils.fail("原密码为空");
		}

		if (StringUtils.isBlank(newPwd)) {
			return AjaxUtils.fail("新密码为空");
		}

		User user = (User) session.getAttribute("user");

		if (user == null) {
			return AjaxUtils.fail("用户尚未登录");
		}

		// 校验原密码是否正确
		if (user.getPassword().equals(oldPwd) == false) {
			return AjaxUtils.fail("原密码错误");
		}

		// 修改密码
		user.setPassword(newPwd);

		int update = userService.update(user);

		if (update > 0) {
			return AjaxUtils.success("密码修改成功，请使用新密码登录系统");
		} else {
			return AjaxUtils.fail("密码修改失败，请重试");
		}

	}

	/**
	 * 
	 * @Title: enterMenu
	 * @Description: 进入菜单
	 * @param roleId
	 * @param menuId
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/enterMenu")
	public AjaxResponse enterMenu(Integer roleId, Integer menuId, HttpSession session) {

		if (roleId == null) {
			return AjaxUtils.fail("角色id为空");
		}

		// 根据角色id查询角色单条记录
		Role role = roleService.findById(roleId);
		if (role != null) {
			session.setAttribute("role", role);
			return AjaxUtils.success("进入菜单成功");
		} else {
			return AjaxUtils.fail("没有该菜单的访问权限，无法进入");
		}
	}

	/**
	 * 
	 * @Title: getButtonList
	 * @Description: 获取按钮权限
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@GetMapping(value = "/getButtonList")
	public AjaxResponse getButtonList(HttpSession session, String page) {

		// 非空判断
		if (StringUtils.isBlank(page)) {
			return AjaxUtils.fail("当前页面名字为空");
		}

		// 获取当前角色信息
		Role role = (Role) session.getAttribute("role");

		if (role == null) {

			return AjaxUtils.fail("角色信息为空");
		} else {

			List<MenuVo> menuList = roleService.findMenuList(role.getId());

			for (MenuVo menuVo : menuList) {

				if (page.equals(menuVo.getMenuPage())) {

					// 查询出按钮集合
					List<Button> buttonList = roleService.findButtonList(role.getId(), menuVo.getId());

					if (buttonList.size() > 0) {
						return AjaxUtils.success(JSON.toJSONString(buttonList));
					}
				}
			}

		}

		return AjaxUtils.fail("获取按钮权限失败");
	}

}
