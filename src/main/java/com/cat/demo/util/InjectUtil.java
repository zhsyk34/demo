package com.cat.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

//
public class InjectUtil {

	public final static InjectUtil me = new InjectUtil();

	private static <T> T createInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("无法实例化...");
		}
	}

	// 根据属性注入
	@SuppressWarnings("unchecked")
	public <T> T injectByField(Class<T> clazz, Map<String, String> map, boolean skipError) {
		Object bean = createInstance(clazz);
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			if (map.containsKey(field)) {
				try {
					Object value = map.get(field);
					field.setAccessible(true);
					field.set(bean, value);
				} catch (Exception e) {
					if (!skipError) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		return (T) bean;
	}

	// 根据set方法注入
	@SuppressWarnings("unchecked")
	public <T> T injectByMethod(Class<T> clazz, Map<String, String> map, boolean skipError) {
		Object bean = createInstance(clazz);
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			// 只允许setX...方法
			String methodName = method.getName();
			if (!methodName.startsWith("set") || methodName.length() <= 3) {
				continue;
			}
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1) {
				continue;
			}
			// Class<?> type = types[0];

			String field = Tools.firstToLower(methodName.substring(3));
			if (map.containsKey(field)) {
				try {
					Object value = TypeConvert.convert(types[0], map.get(field));// TODO
					method.invoke(bean, value);
				} catch (Exception e) {
					if (!skipError) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		return (T) bean;
	}
}
