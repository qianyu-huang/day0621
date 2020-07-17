package com.lening.service;

import java.util.List;

import com.lening.pojo.College;

public interface CollegeService {

	List<College> findAll();

	College findById(int id);
}
