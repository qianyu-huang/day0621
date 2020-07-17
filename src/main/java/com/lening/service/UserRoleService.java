package com.lening.service;

import java.util.List;

import com.lening.pojo.Role;
import com.lening.pojo.UserRole;

public interface UserRoleService {

	List<UserRole> findByRole(int roleId);

	List<Role> findRoleList(int userId);
}
