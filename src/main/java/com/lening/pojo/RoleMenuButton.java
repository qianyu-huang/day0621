package com.lening.pojo;

public class RoleMenuButton {
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    private Integer buttonId;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getButtonId() {
        return buttonId;
    }

    public void setButtonId(Integer buttonId) {
        this.buttonId = buttonId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}