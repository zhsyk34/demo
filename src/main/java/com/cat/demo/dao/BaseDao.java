package com.cat.demo.dao;

import java.lang.reflect.ParameterizedType;

import com.cat.demo.util.ParseMapping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class BaseDao<Entity> {

	private Class<Entity> entityClass;
	private boolean hasMapping;
	private String tableName;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		System.out.println(entityClass);
		this.hasMapping = ParseMapping.classTableMap.containsKey(entityClass);
		if (this.hasMapping) {
			this.tableName = ParseMapping.classTableMap.get(entityClass);
		}

	}

	public boolean save(Entity entity) {
		if (hasMapping) {
			Record record = ParseMapping.toRecord(entity, true);
			Db.save(tableName, record);
			System.out.println(record);
			entity = ParseMapping.toEntity(record, entityClass);
			return true;
		}
		return false;
	}
}
