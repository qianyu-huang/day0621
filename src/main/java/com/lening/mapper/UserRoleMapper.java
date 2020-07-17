package com.lening.mapper;

import java.util.List;

import com.lening.pojo.Role;
import com.lening.pojo.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

	int deleteByUser(int userId);

	List<Role> findRoleList(int userId);

	List<Role> findOtherRoleList(int userId);

	int deleteRole(UserRole userRole);

	List<UserRole> findByRole(int roleId);
}