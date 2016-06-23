package com.cat.demo.config;

import java.util.List;

import com.cat.demo.controller.HouseController;
import com.cat.demo.interceptor.PropertyInterceptor;
import com.cat.demo.util.ParseMapping;
import com.cat.demo.util.ScanClass;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class InitConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/house", HouseController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost/test", "root", "root");
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		// TODO controller mapping...
		// arp.addMapping("house", House.class);
		me.add(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new PropertyInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {

	}

	@Override
	public void afterJFinalStart() {
		// 扫描包下的类并注册映射关系...
		List<Class<?>> clazzs = ScanClass.getClasses("com.cat.demo.entity");
		ParseMapping.parseAnnotation(clazzs);
	}

}
