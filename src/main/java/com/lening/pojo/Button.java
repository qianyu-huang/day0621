package com.lening.pojo;

public class Button {
    private Integer id;

    private String buttonName;

    private String attrName;

    private String buttonUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName == null ? null : buttonName.trim();
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl == null ? null : buttonUrl.trim();
    }
}