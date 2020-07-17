package com.lening.utils;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class AjaxUtils {

	/**
	 * 
	 * @Title: getLayuiData
	 * @Description: 获取Layui表格分页数据
	 * @param pageInfo
	 * @return
	 * @return: LayuiData
	 */
	public static LayuiData getLayuiData(PageInfo<?> pageInfo) {

		LayuiData layuiData = new LayuiData();
		layuiData.setData(pageInfo.getList());
		layuiData.setCount(pageInfo.getTotal());

		return layuiData;
	}

	/**
	 * 
	 * @Title: getLayuiData
	 * @Description: 获取Layui表格全查数据
	 * @param list
	 * @return
	 * @return: LayuiData
	 */
	public static LayuiData getLayuiData(List<?> list) {

		LayuiData layuiData = new LayuiData();
		layuiData.setData(list);
		layuiData.setCount(new Long(list.size()));

		return layuiData;
	}

	/**
	 * Ajax成功变量
	 */
	private final static int success = 1;
	/**
	 * Ajax失败变量
	 */
	private final static int fail = 0;

	/**
	 * 
	 * @Title: success
	 * @Description: Ajax成功的响应方法
	 * @param msg
	 * @return
	 * @return: AjaxResponse
	 */
	public static AjaxResponse success(String msg) {

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setCode(success);
		ajaxResponse.setMsg(msg);

		return ajaxResponse;
	}

	/**
	 * 
	 * @Title: fail
	 * @Description: Ajax失败的响应方法
	 * @param msg
	 * @return
	 * @return: AjaxResponse
	 */
	public static AjaxResponse fail(String msg) {

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setCode(fail);
		ajaxResponse.setMsg(msg);

		return ajaxResponse;
	}

}
