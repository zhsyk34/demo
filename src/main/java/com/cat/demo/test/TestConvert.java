package com.cat.demo.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cat.demo.model.Company;
import com.cat.demo.util.InjectUtil;

public class TestConvert {

	@Test
	public void test() {
		Map<String, String> map = new HashMap<>();
		map.put("id", "33");
		map.put("name", "24");
		map.put("createtime", "2015-11-11");
		map.put("", "");

		Company company = InjectUtil.me.injectByMethod(Company.class, map, true);

		System.out.println(company);

	}
}
