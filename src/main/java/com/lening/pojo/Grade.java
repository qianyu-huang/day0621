package com.lening.pojo;

public class Grade {
    private Integer id;

    private String className;

    private String classCode;

    private Integer collegeId;

    private Integer majorId;

	private String enterYear;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

	public String getEnterYear() {
		return enterYear;
	}

	public void setEnterYear(String enterYear) {
		this.enterYear = enterYear;
	}
}