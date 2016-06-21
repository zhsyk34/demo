package com.cat.demo.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeConvertUtil {

	private static final String timeStampPattern = "yyyy-MM-dd HH:mm:ss";
	private static final String datePattern = "yyyy-MM-dd";
	private static final int timeStampLen = timeStampPattern.length();

	public static Object convert(Class<?> type, Object obj) throws ParseException {
		if (obj.getClass() == type) {
			return obj;
		}

		if (!(obj instanceof String)) {

		}
		String str = (String) obj;

		if (type == String.class) {
			return str == null ? null : str.trim();// 去除空白...
		}

		// type:int, integer, tinyint(n) n > 1, smallint, mediumint
		if (type == Integer.class || type == int.class) {
			return Integer.parseInt(str);
		}

		// bigint
		if (type == Long.class || type == long.class) {
			return Long.parseLong(str);
		}

		// type: real, double
		if (type == Double.class || type == double.class) {
			return Double.parseDouble(str);
		}

		// type: float
		if (type == Float.class || type == float.class) {
			return Float.parseFloat(str);
		}

		// type: bit, tinyint(1)
		if (type == Boolean.class || type == boolean.class) {
			if ("1".equals(str) || "true".equalsIgnoreCase(str)) {
				return Boolean.TRUE;
			} else if ("0".equals(str) || "false".equalsIgnoreCase(str)) {
				return Boolean.FALSE;
			} else {
				throw new RuntimeException("转换失败...");
			}
		}

		// type: decimal, numeric
		if (type == BigDecimal.class) {
			return new BigDecimal(str);
		}

		// type: unsigned bigint
		if (type == BigInteger.class) {
			return new BigInteger(str);
		}

		// type: binary, varbinary, tinyblob, blob, mediumblob, longblob.
		if (type == byte[].class) {
			return str.getBytes();
		}

		// java.util.Date 类型专为传统 java bean 带有该类型的 setter 方法转换做准备，万不可去掉
		// 经测试 JDBC 不会返回 java.util.Data 类型。java.sql.Date,
		// java.sql.Time,java.sql.Timestamp 全部直接继承自 java.util.Data, 所以
		// getDate可以返回这三类数据
		if (type == java.util.Date.class) {
			if (str.length() >= timeStampLen) { // if (x < timeStampLen) 改用
												// datePattern 转换，更智能
				// Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
				// return new
				// java.util.Date(java.sql.Timestamp.valueOf(s).getTime()); //
				// error under jdk 64bit(maybe)
				return new SimpleDateFormat(timeStampPattern).parse(str);
			} else {
				// return new
				// java.util.Date(java.sql.Date.valueOf(s).getTime()); // error
				// under jdk 64bit
				return new SimpleDateFormat(datePattern).parse(str);
			}
		}

		// type: date, year
		if (type == java.sql.Date.class) {
			if (str.length() >= timeStampLen) { // if (x < timeStampLen) 改用
												// datePattern 转换，更智能
				// return new
				// java.sql.Date(java.sql.Timestamp.valueOf(s).getTime()); //
				// error under jdk 64bit(maybe)
				return new java.sql.Date(new SimpleDateFormat(timeStampPattern).parse(str).getTime());
			} else {
				// return new java.sql.Date(java.sql.Date.valueOf(s).getTime());
				// // error under jdk 64bit
				return new java.sql.Date(new SimpleDateFormat(datePattern).parse(str).getTime());
			}
		}

		// type: time
		if (type == java.sql.Time.class) {
			return java.sql.Time.valueOf(str);
		}

		// type: timestamp, datetime
		if (type == java.sql.Timestamp.class) {
			if (str.length() >= timeStampLen) {
				return java.sql.Timestamp.valueOf(str);
			} else {
				return new java.sql.Timestamp(new SimpleDateFormat(datePattern).parse(str).getTime());
			}
		}

		throw new RuntimeException("未知类型...");

	}
}
