package com.cat.demo.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

//扫描路径下的类
public class ScanClass {

	// 默认迭代
	public static List<Class<?>> getClasses(String packageName) {
		return getClasses(packageName, true);
	}

	// 扫描包下类
	public static List<Class<?>> getClasses(String packageName, boolean recursive) {
		List<Class<?>> classes = new ArrayList<>();

		String dirName = packageName.replace(".", "/");

		Enumeration<URL> urls = null;
		try {
			urls = Thread.currentThread().getContextClassLoader().getResources(dirName);
		} catch (IOException e) {
			throw new RuntimeException("加载资源出错...");
		}
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String protocol = url.getProtocol();
			if ("file".equals(protocol)) {
				String filePath = url.getFile();// URLDecoder.decode(url.getFile(),"UTF-8");
				add(packageName, filePath, recursive, classes);
			}
		}

		return classes;
	}

	private static void add(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists()) {
			return;
		}
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : files) {
			if (file.isDirectory()) {// 目录继续扫描
				add(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				// 类名
				String className = file.getName().replaceAll(".class$", "");
				try {
					classes.add(Class.forName(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}