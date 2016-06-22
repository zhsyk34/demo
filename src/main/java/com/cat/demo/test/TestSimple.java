package com.cat.demo.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.cat.demo.dao.CompanyDao;
import com.cat.demo.dao.StuDao;
import com.cat.demo.dao.UserDao;
import com.cat.demo.entity.Stu;
import com.cat.demo.entity.User;
import com.cat.demo.model.Company;
import com.cat.demo.util.ParseMapping;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class TestSimple {

	// 初始化映射关系...
	@Test
	public void load() {
		Class<?>[] clazzs = new Class[] { User.class, Stu.class };// TODO
		ParseMapping.parseAnnotation(clazzs);
	}

	// 初始化数据源映射关系...
	@Test
	public void init() {
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost/test", "root", "root");
		cp.start();

		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		arp.start();

		load();
		// printMapping();
		arp.stop();
	}

	// bean->record
	@Test
	public void beanToReord() {
		init();
		Stu s = new Stu();
		s.setMoney(new BigDecimal(200.5));
		s.setBirthday(new Date());
		s.setSname("haha");

		Record r = ParseMapping.toRecord(s, false);
		Record r2 = ParseMapping.toRecord(s, true);
		System.out.println(r);
		System.out.println(r2);
	}

	@Test
	public void recordToBean() {
		init();
		Record r = new Record().set("id", 1).set("age", 33).set("name", "zhsy").set("birthday", new Date());
		r.set("sscore", 99).set("score", 100).set("money", new BigDecimal(23));
		Stu student = ParseMapping.toEntity(r, Stu.class);

		System.out.println(student);
	}

	@Test
	public void recordToBean2() {
		init();
		Record r = new Record().set("id", 1).set("age", 33).set("name", "zhsy").set("birthday", new Date());
		r.set("sscore", 99).set("score", 100).set("money", new BigDecimal(23));
		Stu student = ParseMapping.toEntity2(r, Stu.class);
		System.out.println(student);

		Record r2 = new Record().set("id", 233).set("age", 34).set("name", "zhsy").set("address", "海边");
		User u = ParseMapping.toEntity2(r2, User.class);
		System.out.println(u);
	}

	@Test
	public void ininFiled() {
		init();
		Record r = new Record().set("id", 1).set("age", 33);
		User u = new User();
		ParseMapping.initEntityInField(r, u, "uid");

		System.out.println(u);

		Record r2 = new Record().set("id", 333).set("age", 33);
		User u2 = new User();
		ParseMapping.loadPKToEntity(r2, u2);

		System.out.println(u2);

	}

	@Test
	public void testSave1() {
		init();
		Stu s = new Stu();
		s.setSscore(99.5);
		s.setMoney(new BigDecimal(100.5));
		s.setBirthday(new Date());
		s.setSname("xixi");

		StuDao sdao = new StuDao();
		sdao.save(s);

		System.out.println(s);

	}

	@Test
	public void testSave2() {
		init();

		User u = new User();
		u.setAddr("fj");
		u.setAge(37);
		u.setN("abc");

		UserDao udao = new UserDao();
		udao.save(u);

		System.out.println(u);

	}

	@Test
	public void testDel() {
		init();
		Stu s = new Stu();
		s.setId(7);

		StuDao dao = new StuDao();
		Record r = new Record().set("id", 7);

		Db.delete("student", r);
		boolean success = dao.delete(s);

		System.out.println(success);

	}

	@Test
	public void testDel2() {
		init();
		User u = new User();
		u.setUid(3);

		UserDao dao = new UserDao();

		boolean success = dao.delete(u);

		System.out.println(success);

	}

	@Test
	public void update1() {
		init();
		User u = new User();
		u.setUid(3);
		u.setAddr("遥远的地方");

		UserDao dao = new UserDao();

		boolean success = dao.update(u);

		System.out.println(success);

	}

	@Test
	public void find1() {
		init();

		UserDao dao = new UserDao();
		User u = dao.find(1);

		System.out.println(u);

	}

	@Test
	public void find2() {
		init();

		UserDao dao = new UserDao();
		List<User> u = dao.findList();
		System.out.println(u);

	}

	@Test
	public void testError() {
		init();

		CompanyDao dao = new CompanyDao();
		Company c = new Company();
		dao.save(c);

	}

	@Test
	public void testCount() {
		init();

		UserDao dao = new UserDao();
		int count = dao.count();
		System.out.println(count);

	}

	public static void printMapping() {
		// ParseMapping.parsePackage("com.cat.demo.model");
		// printMapping();
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
