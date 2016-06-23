package com.cat.demo.controller;

import com.cat.demo.util.ParseMapping;
import com.jfinal.core.Controller;

public class BaseController extends Controller {

	protected <Entity> void render(Entity entity) {
		super.renderJson(ParseMapping.toRecord(entity, false));
	}
}
