package com.cat.demo.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeConvertUtil {

	private static final String TIMEPATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final String DATEPATTERN = "yyyy-MM-dd";

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
			// 0,false,null,""->false
			return "0".equals(str) || "false".equalsIgnoreCase(str) || Tools.isEmpty(str) ? Boolean.FALSE : Boolean.TRUE;
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

		// date pattern
		String pattern = str.length() >= TIMEPATTERN.length() ? TIMEPATTERN : DATEPATTERN;

		// type:
		if (type == java.util.Date.class) {
			return new SimpleDateFormat(pattern).parse(str);
		}

		// type: date, year
		if (type == java.sql.Date.class) {
			return new java.sql.Date(new SimpleDateFormat(pattern).parse(str).getTime());
		}

		// type: time
		if (type == java.sql.Time.class) {
			return java.sql.Time.valueOf(str);
		}

		// type: timestamp, datetime
		if (type == java.sql.Timestamp.class) {
			return new java.sql.Timestamp(new SimpleDateFormat(pattern).parse(str).getTime());
		}

		throw new RuntimeException("未知类型...");

	}
}
