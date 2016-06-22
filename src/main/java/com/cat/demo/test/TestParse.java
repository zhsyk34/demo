package com.cat.demo.test;

import org.junit.Test;

import pl.jsolve.typeconverter.TypeConverter;

public class TestParse {

	@Test
	public void test1() {

		long value = 3l;
		int i = TypeConverter.convert(value, int.class);
		System.out.println(i);
	}
}
