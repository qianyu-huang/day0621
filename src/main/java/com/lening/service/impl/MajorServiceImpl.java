package com.lening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lening.mapper.MajorMapper;
import com.lening.pojo.Major;
import com.lening.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService {

	@Autowired
	private MajorMapper majorMapper;

	@Override
	public List<Major> findByCollege(int collegeId) {

		List<Major> list = majorMapper.findByCollege(collegeId);
		return list;
	}

	@Override
	public Major findByIdAndCollege(int id, int collegeId) {

		Major major = majorMapper.findByIdAndCollege(id, collegeId);
		return major;
	}

}
