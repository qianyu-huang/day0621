package com.lening.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lening.pojo.Role;
import com.lening.pojo.User;
import com.lening.pojo.UserRole;
import com.lening.service.RoleService;
import com.lening.service.UserService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.vo.UserVo;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	// 默认密码
	@Value(value = "${defaultPassword}")
	private String defaultPassword;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 用户全查
	 * @return
	 * @return: List<User>
	 */
	@RequestMapping(value = "/findAll")
	public List<User> findAll() {

		List<User> list = userService.findAll();

		return list;
	}

	/**
	 * 
	 * @Title: findVo
	 * @Description: 用户详细信息全查
	 * @return
	 * @return: LayuiData
	 */
	@RequestMapping(value = "/findVo")
	public LayuiData findVo() {

		List<UserVo> list = userService.findVo();

		// 对角色List做一个字符串处理，获取最终的角色列表字符串，例如学生，辅导员
		// for (UserVo userVo : list) {
		//
		// List<Role> roleList = userVo.getRoleList();
		//
		// // 判断一下角色集合是否为空集合
		// if (roleList.size() > 0) {
		// StringBuilder roleInfo = new StringBuilder();
		//
		// for (Role role : roleList) {
		//
		// String roleName = role.getRoleName();
		//
		// roleInfo.append(roleName + ",");
		//
		// }
		//
		// // 去掉末尾的逗号
		// String roleInfoStr = roleInfo.substring(0, roleInfo.length() - 1);
		//
		// userVo.setRoleInfo(roleInfoStr);
		// }
		//
		// }

		return AjaxUtils.getLayuiData(list);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 用户新增
	 * @param user
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(User user) {

		// 校验用户名
		String username = user.getUsername();

		if (StringUtils.isBlank(username) == true) {

			return AjaxUtils.fail("用户名不能为空");
		} else {

			// 校验用户名的唯一性
			User find = userService.findByUsername(username);

			if (find != null) {

				return AjaxUtils.fail("用户名重复，请重新输入");
			}
		}

		// 校验姓名
		String realname = user.getRealname();

		if (StringUtils.isBlank(realname) == true) {
			return AjaxUtils.fail("真实姓名不能为空");
		} else {
			if (realname.length() < 2 || realname.length() > 4) {
				return AjaxUtils.fail("真实姓名只能为2-4位");
			}
		}

		// 校验性别
		String sex = user.getSex();
		if (StringUtils.isBlank(sex) == true) {
			return AjaxUtils.fail("性别不能为空");
		} else {
			if (sex.equals("男") == false && sex.equals("女") == false) {

				return AjaxUtils.fail("性别只能为男或女");
			}
		}

		// 密码默认设置为123
		user.setPassword(defaultPassword);

		// 新增用户
		int save = userService.save(user);

		if (save > 0) {
			return AjaxUtils.success("用户新增成功");
		} else {
			return AjaxUtils.fail("用户新增失败");
		}
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 用户单条删除
	 * @param id
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/delete")
	public AjaxResponse delete(Integer id) {

		if (id != null) {

			int delete = userService.delete(id);

			if (delete > 0) {
				return AjaxUtils.success("用户单条删除成功");
			}
		}

		return AjaxUtils.fail("用户单条删除失败");
	}

	/**
	 * 
	 * @Title: saveUserInfo
	 * @Description: 将要进行角色授权的用户信息存入session
	 * @param id
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/saveUserInfo")
	public AjaxResponse saveUserInfo(Integer id, HttpSession session) {

		if (id != null) {

			User user = userService.findById(id);

			if (user != null) {
				session.setAttribute("updateUser", user);

				return AjaxUtils.success("用户信息成功存入session");
			}
		}

		return AjaxUtils.fail("用户信息存入session失败");
	}

	/**
	 * 
	 * @Title: getUserInfo
	 * @Description: 在角色授权页面，获取用户信息
	 * @param session
	 * @return
	 * @return: AjaxResponse
	 */
	@GetMapping(value = "/getUserInfo")
	public AjaxResponse getUserInfo(HttpSession session) {

		User user = (User) session.getAttribute("updateUser");

		if (user != null) {
			return AjaxUtils.success(JSON.toJSONString(user));
		} else {
			return AjaxUtils.fail("获取用户信息失败");
		}
	}

	/**
	 * 
	 * @Title: findRoleList
	 * @Description: 查询已授权的角色列表
	 * @param userId
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findRoleList")
	public LayuiData findRoleList(Integer userId) {

		if (userId != null) {

			List<Role> list = userService.findRoleList(userId);

			return AjaxUtils.getLayuiData(list);
		}

		return null;
	}

	/**
	 * 
	 * @Title: findOtherRoleList
	 * @Description: 查询未授权的角色列表
	 * @param userId
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findOtherRoleList")
	public LayuiData findOtherRoleList(Integer userId) {

		if (userId != null) {

			List<Role> list = userService.findOtherRoleList(userId);

			return AjaxUtils.getLayuiData(list);
		}

		return null;
	}

	/**
	 * 
	 * @Title: addRole
	 * @Description: 为当前用户增加角色
	 * @param userId
	 * @param roleId
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/addRole")
	public AjaxResponse addRole(Integer userId, Integer roleId) {

		if (userId != null && roleId != null) {

			// 校验用户id在用户表是否存在
			User user = userService.findById(userId);

			if (user == null) {
				return AjaxUtils.fail("用户id不存在");
			}

			// 校验角色id在角色表是否存在
			Role role = roleService.findById(roleId);
			if (role == null) {
				return AjaxUtils.fail("角色id不存在");
			}

			UserRole userRole = new UserRole();

			userRole.setUserId(userId);
			userRole.setRoleId(roleId);

			int addRole = userService.addRole(userRole);

			if (addRole > 0) {
				return AjaxUtils.success("角色增加成功");
			}
		}

		return AjaxUtils.fail("增加角色失败");
	}

	/**
	 * 
	 * @Title: deleteRole
	 * @Description: 为当前用户删除角色
	 * @param userId
	 * @param roleId
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/deleteRole")
	public AjaxResponse deleteRole(Integer userId, Integer roleId) {

		if (userId != null && roleId != null) {

			// 校验用户id在用户表是否存在
			User user = userService.findById(userId);

			if (user == null) {
				return AjaxUtils.fail("用户id不存在");
			}

			// 校验角色id在角色表是否存在
			Role role = roleService.findById(roleId);
			if (role == null) {
				return AjaxUtils.fail("角色id不存在");
			}

			UserRole userRole = new UserRole();

			userRole.setUserId(userId);
			userRole.setRoleId(roleId);

			int deleteRole = userService.deleteRole(userRole);

			if (deleteRole > 0) {
				return AjaxUtils.success("角色删除成功");
			}
		}

		return AjaxUtils.fail("删除角色失败");
	}

}
