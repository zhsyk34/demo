package com.cat.demo.test;

import org.junit.Test;

import com.cat.demo.dao.UserDao;
import com.cat.demo.entity.Student;
import com.cat.demo.entity.User;
import com.cat.demo.util.ParseMapping;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class Validate {

	@Test
	public void test0() {
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost/test", "root", "root");
		cp.start();

		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		arp.start();

		ParseMapping.load(arp);

		test1();
		arp.stop();
	}

	@Test
	public void test1() {
		Class<?>[] clazzs = new Class[] { User.class, Student.class };// TODO
		ParseMapping.parseAnnotation(clazzs);
		printMapping();
	}

	@Test
	public void test2() {
		ParseMapping.parsePackage("com.cat.demo.model");
		printMapping();

	}

	@Test
	public void test3() {
		test0();
		UserDao userDao = new UserDao();

		User user = new User("cjj", 33, "xiamen");
		userDao.save(user);

		System.out.println(user.getId());

	}

	@Test
	public void transform() {
		test1();
		Record r = new Record().set("id", 1).set("age", 33).set("uname", "zhsy");
		User user = ParseMapping.toEntity(r, User.class);
		System.out.println(user);
	}

	public static void testSave() {
		User user = new User("crg", 33, "xiamen");
		Record r = ParseMapping.toRecord(user, true);

		System.out.println(r.getColumns().get("id"));
		System.out.println(r.getColumns().get("name"));

		Db.save("user", r);
	}

	private static void printMapping() {
		ParseMapping.classTableMap.forEach((k, v) -> {
			System.out.println("table : " + v);
			ParseMapping.classFieldIdMap.get(k).forEach((filed, column) -> {
				System.out.println("\tid : " + column);
			});
			ParseMapping.classFieldColumnMap.get(k).forEach((filed, column) -> {
				System.out.println("\tcolumn : " + column);
			});
		});
	}

}
