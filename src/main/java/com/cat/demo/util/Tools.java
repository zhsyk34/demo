package com.cat.demo.util;

import java.util.Map;

public class Tools {

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String firstToLower(String str) {
		char[] chars = str.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	public static String firstToUpper(String str) {
		char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public static String getMethodName(String field) {
		return "get" + firstToUpper(field);
	}

	public static String concat(String[] strs) {
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str).append(",");
		}
		return builder.toString().replaceAll(",$", "");
	}

	public static String concatValues(Map<? extends Object, String> map) {
		StringBuilder builder = new StringBuilder();
		map.forEach((o, s) -> {
			builder.append(s).append(",");
		});
		return builder.toString().replaceAll(",$", "");
	}

	public static void main(String[] args) {
		String[] strs = { "1", "2", "3" };
		System.out.println(concat(strs));
	}
}
