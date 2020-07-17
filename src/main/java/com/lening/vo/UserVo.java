package com.lening.vo;

import java.util.List;

import com.lening.pojo.Role;
import com.lening.pojo.User;

public class UserVo extends User {

	private List<Role> roleList;

	private String roleInfo;

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
	}

}
