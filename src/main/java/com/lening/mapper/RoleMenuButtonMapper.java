package com.lening.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lening.pojo.RoleMenuButton;

public interface RoleMenuButtonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenuButton record);

    int insertSelective(RoleMenuButton record);

    RoleMenuButton selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenuButton record);

    int updateByPrimaryKey(RoleMenuButton record);

	List<RoleMenuButton> findByMenu(int menuId);

	List<RoleMenuButton> findByButton(int buttonId);

	int deleteByRole(int roleId);

	RoleMenuButton findOne(RoleMenuButton roleMenuButton);

	int deleteByRoleAndMenu(@Param(value = "roleId") int roleId, @Param(value = "menuId") int menuId);

	List<RoleMenuButton> findByUser(int userId);

}