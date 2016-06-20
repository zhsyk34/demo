/**
 * 
 */
package com.cat.demo.util;

import java.lang.reflect.Field;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author Cat
 *
 */
public class DaoUtil {

	public static <M extends Model<?>> void save(M model) {
		Class<?> clazz = model.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			System.out.println(name);
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(model);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println(value);

			model.set(name, value);
		}
		model.save();
	}

	public static void main(String[] args) {
		// User user = new User("zhsy", 33, "xm");
		// DaoUtil.save(user);
	}

}
