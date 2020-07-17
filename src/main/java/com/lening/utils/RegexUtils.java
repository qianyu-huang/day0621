package com.lening.utils;

import org.apache.commons.lang.StringUtils;

public class RegexUtils {

	private static String regex;

	/**
	 * 
	 * @Title: isNumber
	 * @Description: 校验传入的字符串是否为指定长度的纯数字
	 * @param input
	 * @param length
	 * @return
	 * @return: boolean
	 */
	public static boolean isNumber(String input, int length) {

		// 字符串非空判定
		if (StringUtils.isBlank(input) == true) {
			return false;
		} else {
			regex = "\\d{" + length + "}";

			boolean matches = input.matches(regex);

			return matches;
		}

	}
}
