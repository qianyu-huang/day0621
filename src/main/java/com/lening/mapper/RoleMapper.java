package com.lening.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lening.pojo.Button;
import com.lening.pojo.Menu;
import com.lening.pojo.Role;
import com.lening.vo.MenuVo;
import com.lening.vo.RoleVo;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	Role findByName(String roleName);

	List<RoleVo> findVo();

	List<MenuVo> findMenuList(int roleId);

	List<Menu> findOtherMenuList(int roleId);

	List<Button> findButtonList(@Param(value = "roleId") int roleId, @Param(value = "menuId") int menuId);

	List<Button> findOtherButtonList(@Param(value = "roleId") int roleId, @Param(value = "menuId") int menuId);

}