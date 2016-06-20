package com.cat.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Id;
import com.cat.demo.annotation.Table;
import com.jfinal.plugin.activerecord.Record;

public final class ParseMapping {

	public final static Map<Class<?>, String> classTableMap = new HashMap<>();
	public final static Map<Class<?>, Map<String, String>> classFieldIdMap = new HashMap<>();
	public final static Map<Class<?>, Map<String, String>> classFieldColumnMap = new HashMap<>();

	public static void parseAnnotation(Class<?>[] clazzs) {
		for (Class<?> clazz : clazzs) {
			parseAnnotation(clazz);
		}
	}

	public static void parseAnnotation(Class<?> clazz) {
		// 1.class->table
		Table table = clazz.getAnnotation(Table.class);
		if (table == null) {
			return;
		}

		String tableName = table.value();
		if (Tools.isEmpty(tableName)) {
			tableName = Tools.firstToLower(clazz.getSimpleName());
		}
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
			}
		}
		classFieldIdMap.put(clazz, fieldIdMap);
		classFieldColumnMap.put(clazz, fieldColumnMap);
	}

	public static void parsePackage(String packageName) {
		List<Class<?>> classes = ScanClass.getClasses("com.cat.demo.model");
		for (Class<?> clazz : classes) {
			// 1.table
			classTableMap.put(clazz, clazz.getSimpleName());
			// 2.field->column
			Map<String, String> fieldIdMap = new HashMap<>();
			Map<String, String> fieldColumnMap = new HashMap<>();

			fieldIdMap.put("id", "id");// TODO 粗糙处理,默认id
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String filedName = field.getName();
				fieldColumnMap.put(filedName, filedName);
			}
			classFieldIdMap.put(clazz, fieldIdMap);
			classFieldColumnMap.put(clazz, fieldColumnMap);
		}
	}

	public static void load() {

		// Db模式无需载入...

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

	public static <Entity> Record toRecord(Entity entity, boolean skipPK) {
		Record record = new Record();
		Class<?> clazz = entity.getClass();
		if (!classTableMap.containsKey(clazz)) {
			System.out.println("未映射表...");
			return record;
		}
		// String tableName = classTableMap.get(clazz);
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);
		Map<String, String> fieldIdMap = classFieldIdMap.get(clazz);
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
				// System.out.println("data: " + field + " , " + value);
				if (fieldIdMap.containsKey(field) && skipPK) {

					System.out.println("pk : " + value);
					if (value == null) {
						System.out.println("没有主键...");// TODO
					}
				} else {
					record.set(column, value);
				}
			} catch (Exception e) {
				System.out.println(field + "没有对应的get方法");
				e.printStackTrace();
			}
		});

		return record;
	}

	public static <Entity> Entity toEntity(Record record, Class<Entity> clazz) {
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);
		Map<String, Object> map = record.getColumns();

		Entity entity;
		try {
			entity = (Entity) clazz.newInstance();
			map.forEach((k, v) -> {
				fieldColumnMap.forEach((f, c) -> {
					if (k.equals(c)) {
						try {
							Field field = clazz.getDeclaredField(f);
							field.setAccessible(true);
							field.set(entity, v);
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException("...");
						}
					}
				});
			});
			return entity;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("初始化对象失败...");
		}

	}

}
