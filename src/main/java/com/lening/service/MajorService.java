package com.lening.service;

import java.util.List;

import com.lening.pojo.Major;

public interface MajorService {

	List<Major> findByCollege(int collegeId);

	Major findByIdAndCollege(int id, int collegeId);
}
