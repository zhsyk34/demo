package com.cat.demo.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;

public class HouseController extends Controller {

	public void index() {
		renderText("I am angry.");
	}

	public void add() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "zhsy");
		map.put("age", 32);
		renderJson(map);
	}
}
