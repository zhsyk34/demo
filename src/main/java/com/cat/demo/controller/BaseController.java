package com.cat.demo.controller;

import com.cat.demo.dao.StuDao;
import com.cat.demo.dao.UserDao;
import com.cat.demo.util.ParseMapping;
import com.jfinal.core.Controller;

public class BaseController extends Controller {

	protected UserDao userDao = new UserDao();

	protected StuDao stuDao = new StuDao();

	protected <Entity> void render(Entity entity) {
		super.renderJson(ParseMapping.toRecord(entity, false));
	}
}
