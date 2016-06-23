package com.cat.demo.controller;

import com.cat.demo.entity.Stu;
import com.cat.demo.entity.User;

public class HouseController extends BaseController {

	protected int id;

	protected String name;

	protected int age;

	protected User user;

	protected Stu stu;

	public void index() {
		System.out.println("house index");
		renderJsp("/house/house.jsp");
	}

	public void add() {
		boolean flag = stuDao.save(stu);
		System.out.println(flag);
		System.out.println("action model : " + stu);
		render(stu);
	}

	public void list() {
		stu = stuDao.find(8);
		render(stu);
	}
}
