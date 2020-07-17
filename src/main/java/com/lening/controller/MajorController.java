package com.lening.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lening.pojo.Major;
import com.lening.service.MajorService;

/**
 * 
 * @ClassName: MajorController
 * @Description: 专业模块
 * @author: 17861
 * @date: 2020-6-22 15:16:20
 */
@RestController
@RequestMapping(value = "/major")
public class MajorController {

	@Autowired
	private MajorService majorService;

	/**
	 * 
	 * @Title: findByCollege
	 * @Description: 根据学院id查询对应的所有专业
	 * @param collegeId
	 * @return
	 * @return: List<Major>
	 */
	@RequestMapping(value = "/findByCollege")
	public List<Major> findByCollege(Integer collegeId) {

		// 非空判定
		if (collegeId != null) {
			List<Major> list = majorService.findByCollege(collegeId);
			return list;
		}

		return null;

	}

}
