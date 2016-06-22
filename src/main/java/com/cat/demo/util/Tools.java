package com.cat.demo.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class Tools {

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static String firstToLower(String str) {
		char[] chars = str.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	public static String printDate(Date date) {
		return date == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String firstToUpper(String str) {
		char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public static String getMethodName(String field) {
		return "get" + firstToUpper(field);
	}

	public static String setMethodName(String field) {
		return "set" + firstToUpper(field);
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

	public static <T> T createInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("无法实例化...");
		}
	}

	public static void main(String[] args) {
		String[] strs = { "1", "2", "3" };
		System.out.println(concat(strs));
	}
}
