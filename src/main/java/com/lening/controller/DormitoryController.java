package com.lening.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lening.pojo.Dormitory;
import com.lening.service.DormitoryService;
import com.lening.service.RoleMenuButtonService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import com.lening.utils.LayuiData;
import com.lening.utils.RegexUtils;

@RestController
@RequestMapping(value = "/dormitory")
public class DormitoryController {

	@Autowired
	private DormitoryService dormitoryService;

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;

	/**
	 * 
	 * @Title: findAll
	 * @Description: 宿舍全查
	 * @return
	 * @return: List<Dormitory>
	 */
	@RequestMapping(value = "/findAll")
	public List<Dormitory> findAll() {

		List<Dormitory> list = dormitoryService.findAll();

		return list;
	}

	/**
	 * 
	 * @Title: findPage
	 * @Description: 宿舍分页查询
	 * @param page
	 * @param limit
	 * @return
	 * @return: LayuiData
	 */
	@GetMapping(value = "/findPage")
	public LayuiData findPage(Integer page, Integer limit) {

		if (page != null && limit != null) {

			PageInfo<Dormitory> pageInfo = dormitoryService.findPage(page, limit);

			return AjaxUtils.getLayuiData(pageInfo);
		}

		return null;
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 宿舍新增
	 * @param dormitory
	 * @return
	 * @return: AjaxResponse
	 */
	@PostMapping(value = "/save")
	public AjaxResponse save(Dormitory dormitory) {

		// 校验楼号，限制为1-9
		String number = dormitory.getNumber();
		if (RegexUtils.isNumber(number, 1) == false) {

			return AjaxUtils.fail("楼号不合法");
		} else {

			int buildingNumber = Integer.parseInt(number);

			if (buildingNumber < 1) {
				return AjaxUtils.fail("楼号只能为1-9");
			}
		}

		// 校验楼层号，限制为1-5
		String floor = dormitory.getFloor();
		if (RegexUtils.isNumber(floor, 1) == false) {

			return AjaxUtils.fail("楼层号不合法");
		} else {

			int floorNumber = Integer.parseInt(floor);

			if (floorNumber < 1 || floorNumber > 5) {
				return AjaxUtils.fail("楼层号只能为1-5");
			}
		}

		// 校验宿舍号，限制为3位数字，用户选择的楼层号和宿舍号的第一位数字保持一致
		String name = dormitory.getName();
		if (RegexUtils.isNumber(name, 3) == false) {
			return AjaxUtils.fail("宿舍号不合法");
		} else {

			if (name.substring(0, 1).equals(floor) == false) {

				return AjaxUtils.fail("宿舍号第一位数字和楼层号不一致");
			} else {

				// 校验一下在当前楼号下，宿舍号是否重复
				Dormitory findOne = dormitoryService.findOne(number, name);

				if (findOne != null) {
					return AjaxUtils.fail("宿舍号重复");
				}

			}

		}

		// 校验宿舍类型
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("四人间");
		typeList.add("六人间");
		typeList.add("八人间");

		if (typeList.contains(dormitory.getType()) == false) {
			return AjaxUtils.fail("宿舍类型不合法");
		}

		int save = dormitoryService.save(dormitory);

		if (save > 0) {
			return AjaxUtils.success("宿舍新增成功");
		} else {
			return AjaxUtils.fail("宿舍新增失败，请重试");
		}

	}
}
