package com.cat.demo;

import java.util.Date;

import pl.jsolve.typeconverter.TypeConverter;

public class TestConvert {

	public static void main(String[] args) {
		Date date = new Date();
		java.sql.Date sqlDate = TypeConverter.convert(date, java.sql.Date.class);
		System.out.println(sqlDate);
	}

}
