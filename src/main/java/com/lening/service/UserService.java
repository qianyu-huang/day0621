package com.lening.service;

import java.util.List;

import com.lening.pojo.Role;
import com.lening.pojo.User;
import com.lening.pojo.UserRole;
import com.lening.vo.UserVo;

public interface UserService {

	List<User> findAll();

	List<UserVo> findVo();

	User findByUsername(String username);

	int save(User user);

	int delete(int id);

	User findById(int id);

	List<Role> findRoleList(int userId);

	List<Role> findOtherRoleList(int userId);

	int addRole(UserRole userRole);

	int deleteRole(UserRole userRole);

	int update(User user);
}
