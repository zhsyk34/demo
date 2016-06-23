package com.cat.demo.interceptor;

import java.lang.reflect.Field;

import com.cat.demo.controller.BaseController;
import com.cat.demo.util.ParseMapping;
import com.cat.demo.util.Tools;
import com.cat.demo.util.TypeConvert;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class PropertyInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		if (controller instanceof BaseController) {
			handle((BaseController) controller);
		}
		inv.invoke();
	}

	// TODO 自定义实体类只处理已经映射...
	private void handle(BaseController target) {
		Field[] fields = target.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			field.setAccessible(true);
			try {
				Class<?> clazz = field.getType();
				if (ParseMapping.classTableMap.containsKey(clazz)) {
					// System.out.print("model : " + name + "-");
					Object value = target.getBean(clazz, null);
					// System.out.println(value);
					field.set(target, value);
				} else {
					// System.out.print("基础属性 :" + name + "-");
					String param = target.getPara(name);
					// System.out.println(param);

					if (Tools.isNotEmpty(param)) {
						Object value = TypeConvert.convert(clazz, param);
						// System.out.println(" -> " + value);
						field.set(target, value);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(name + "赋值失败...");
			}
		}
	}

}
