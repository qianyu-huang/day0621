package com.lening.vo;

import com.lening.pojo.Grade;

public class GradeVo extends Grade {

	private String collegeName;

	private String majorName;

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

}
