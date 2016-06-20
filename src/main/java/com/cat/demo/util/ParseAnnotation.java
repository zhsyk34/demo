package com.cat.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Id;
import com.cat.demo.annotation.Table;
import com.cat.demo.entity.Student;
import com.cat.demo.entity.User;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public final class ParseAnnotation {

	private final static Map<Class<?>, String> classTableMap = new HashMap<>();
	private final static Map<Class<?>, Map<String, String>> classFieldIdMap = new HashMap<>();
	private final static Map<Class<?>, Map<String, String>> classFieldColumnMap = new HashMap<>();

	private static void parse(Class<?>[] clazzs) {
		for (Class<?> clazz : clazzs) {
			mapping(clazz);
		}
	}

	private static void mapping(Class<?> clazz) {
		// 1.class->table
		Table table = clazz.getAnnotation(Table.class);
		if (table == null) {
			return;
		}

		String tableName = table.value();
		if (Tools.isEmpty(tableName)) {
			tableName = Tools.firstToLower(clazz.getSimpleName());
		}
		System.out.println("table : " + tableName);
		classTableMap.put(clazz, tableName);

		// 2.field->column
		Map<String, String> fieldIdMap = new HashMap<>();
		Map<String, String> fieldColumnMap = new HashMap<>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String filedName = field.getName();

			// 2-1.id
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				String idName = id.value();
				if (Tools.isEmpty(idName)) {
					idName = Tools.firstToLower(filedName);
				}
				System.out.println("\tid : " + idName);
				fieldIdMap.put(filedName, idName);
				fieldColumnMap.put(filedName, idName);
				continue;
			}

			// 2-2.column
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				String columnName = column.value();
				if (Tools.isEmpty(columnName)) {
					columnName = Tools.firstToLower(filedName);
				}
				fieldColumnMap.put(filedName, columnName);
				System.out.println("\tcolumn : " + columnName);
			}
		}
		classFieldIdMap.put(clazz, fieldIdMap);
		classFieldColumnMap.put(clazz, fieldColumnMap);
	}

	public static void load(/* ActiveRecordPlugin arp */) {
		Class<?>[] clazzs = new Class[] { User.class, Student.class };// TODO
		parse(clazzs);
		// Db模式无需记载...?
		// classTableMap.forEach((c, t) -> {
		// Map<String, String> fieldIdMap = classFieldIdMap.get(c);
		// if (fieldIdMap.isEmpty()) {
		// arp.addMapping(t, c);
		// } else {
		// String ids = Tools.concatValues(fieldIdMap);
		// System.out.println("ids : " + ids);
		// arp.addMapping(t, ids, c);
		// }
		// });
	}

	public static <Entity> Record toRecord(Entity entity) {
		Record record = new Record();
		Class<?> clazz = entity.getClass();
		if (!classTableMap.containsKey(clazz)) {
			System.out.println("未映射表...");
			return null;
		}
		// String tableName = classTableMap.get(clazz);
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);
		System.out.println("columns size:" + fieldColumnMap.size());
		if (fieldColumnMap.isEmpty()) {
			System.out.println("无映射字段...");
			return record;
		}

		fieldColumnMap.forEach((field, column) -> {
			String getMethodName = Tools.getMethodName(field);
			try {
				Method method = clazz.getMethod(getMethodName);
				// Class<?> returnType = method.getReturnType();//TODO
				Object value = method.invoke(entity);
				System.out.println("data: " + field + " , " + value);
				record.set(column, value);
			} catch (Exception e) {
				System.out.println(field + "没有对应的get方法");
				e.printStackTrace();
			}
		});

		return record;
	}

	public static <Entity> Entity toEntity(Record record, Class<Entity> clazz) {
		Map<String, Object> map = record.getColumns();
		map.forEach((k, v) -> {

		});
		return null;
	}

	public static void main(String[] args) {
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost/test", "root", "root");
		cp.start();

		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		arp.start();

		load();

		Record r = new Record().set("id", 1).set("age", 33).set("name", "zhsy");
		User user = toEntity(r, User.class);
		System.out.println(user);
		arp.stop();

	}

	public static void testSave() {
		Student student = new Student();
		student.setId(47);
		student.setName("none");
		student.setScore(123);
		Record r = toRecord(student);

		System.out.println(r.getColumns().get("id"));
		System.out.println(r.getColumns().get("name"));

		Db.save("student", r);
	}
}
