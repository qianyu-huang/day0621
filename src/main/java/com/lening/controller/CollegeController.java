package com.lening.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lening.pojo.College;
import com.lening.service.CollegeService;

/**
 * 
 * @ClassName: CollegeController
 * @Description: 学院模块
 * @author: 17861
 * @date: 2020-6-22 14:56:36
 */
@RestController
@RequestMapping(value = "/college")
public class CollegeController {

	@Autowired
	private CollegeService collegeService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 学院全查
	 * @return
	 * @return: List<College>
	 */
	@RequestMapping(value = "/findAll")
	public List<College> findAll() {

		List<College> list = collegeService.findAll();

		return list;
	}

}
