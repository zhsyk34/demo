package com.cat.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Id;
import com.cat.demo.annotation.Table;
import com.jfinal.plugin.activerecord.Record;

import pl.jsolve.typeconverter.TypeConverter;

public final class ParseMapping {

	// class->table
	public final static Map<Class<?>, String> classTableMap = new HashMap<>();
	// class->(field->column)
	public final static Map<Class<?>, Map<String, String>> classFieldIdMap = new HashMap<>();
	// class->(filed->column)
	public final static Map<Class<?>, Map<String, String>> classFieldColumnMap = new HashMap<>();

	public static String getTable(Class<?> clazz) {
		return classTableMap.get(clazz);
	}

	public static String getColumn(Class<?> clazz, String field) {
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);
		return fieldColumnMap.get(field);
	}

	public static String getId(Class<?> clazz) {
		Map<String, String> fieldIdMap = classFieldIdMap.get(clazz);
		StringBuilder result = new StringBuilder();
		fieldIdMap.forEach((field, column) -> {
			result.append(column).append(",");
		});
		return result.toString().replaceAll(",$", "");
	}

	public static void parseAnnotation(List<Class<?>> clazzs) {
		for (Class<?> clazz : clazzs) {
			parseAnnotation(clazz);
		}
	}

	public static void parseAnnotation(Class<?>[] clazzs) {
		for (Class<?> clazz : clazzs) {
			parseAnnotation(clazz);
		}
	}

	// 注解解析...
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

		if (fieldIdMap.isEmpty()) {
			System.err.println("未指定主键...");
		}
	}

	// 包扫描...//TODO
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

	// bean -> record
	// skippk忽略主键的映射,以支持数据库自增...
	public static <Entity> Record toRecord(Entity entity, boolean skipPK) {
		Record record = new Record();
		Class<?> clazz = entity.getClass();
		if (!classTableMap.containsKey(clazz)) {
			System.err.println("未映射表...");
			return record;
		}
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);
		Map<String, String> fieldIdMap = classFieldIdMap.get(clazz);
		if (fieldColumnMap.isEmpty()) {
			System.err.println("无映射字段...");
			return record;
		}

		System.out.println(entity);
		fieldColumnMap.forEach((field, column) -> {
			String getMethodName = Tools.getMethodName(field);
			try {
				Method method = clazz.getMethod(getMethodName);
				Object value = method.invoke(entity);
				// 对主键的处理...
				if (fieldIdMap.containsKey(field) && skipPK) {
					// System.out.println("略过主键 : " + field);
				} else {
					record.set(column, value);
				}
			} catch (Exception e) {
				System.err.println(field + "没有对应的get方法 : " + getMethodName);
				e.printStackTrace();
			}
		});

		return record;
	}

	// record-> bean(直接赋值...)
	public static <Entity> Entity toEntity(Record record, Class<Entity> clazz) {
		Entity entity = Tools.createInstance(clazz);
		if (record == null) {
			return entity;
		}
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);
		Map<String, Object> map = record.getColumns();

		map.forEach((k, v) -> {
			if (v == null) {
				return;
			}
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
	}

	// record-> bean(通过set方法...)
	public static <Entity> Entity toEntity2(Record record, Class<Entity> clazz) {
		Map<String, String> fieldColumnMap = classFieldColumnMap.get(clazz);

		Entity entity = Tools.createInstance(clazz);

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (!methodName.startsWith("set") || methodName.length() <= 3) {
				continue;
			}
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1) {
				continue;
			}
			// 验证参数合法性,为启用
			// Class<?> type = types[0];

			String field = Tools.firstToLower(methodName.substring(3));

			Object value = record.get(fieldColumnMap.get(field));
			if (value == null) {
				continue;
			}
			try {
				method.invoke(entity, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.err.println("参数错误...");
				e.printStackTrace();
			}
		}

		return entity;
	}

	// 初始化特定字段,主要用于接收保存数据后自动生成的主键...
	@SuppressWarnings("unchecked")
	public static <Entity> Entity initEntityInField(Record record, Entity entity, String fieldName) {
		Class<Entity> clazz = (Class<Entity>) entity.getClass();
		String column = getColumn(clazz, fieldName);
		Object value = record.get(column);

		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(entity, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return entity;
	}

	// 加载主键...
	@SuppressWarnings("unchecked")
	public static <Entity> Entity loadPKToEntity(Record record, Entity entity) {
		Class<Entity> clazz = (Class<Entity>) entity.getClass();
		classFieldIdMap.get(clazz).forEach((fieldName, idName) -> {
			// System.out.println(fieldName + "-" + idName);

			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				Class<?> type = field.getType();
				Object value = record.get(idName);
				System.out.println("加载主键:" + idName + ":" + value);
				if (value.getClass() != type) {
					value = TypeConverter.convert(value, type);// TODO 完善类型转换...
				}

				field.set(entity, value);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		return entity;
	}

	public static <Entity> List<Entity> convertList(List<Record> records, Class<Entity> clazz) {
		if (Tools.isEmpty(records)) {
			return null;
		}
		List<Entity> list = new ArrayList<>();
		records.forEach(record -> {
			list.add(ParseMapping.toEntity(record, clazz));
		});
		return list;
	}
}
