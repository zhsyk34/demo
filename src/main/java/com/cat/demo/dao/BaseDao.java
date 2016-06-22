package com.cat.demo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.cat.demo.util.ParseMapping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class BaseDao<Entity> {

	private Class<Entity> entityClass;
	private boolean hasMapping;
	private String tableName;
	private String primaryKey;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.hasMapping = ParseMapping.classTableMap.containsKey(entityClass);
		if (this.hasMapping) {
			this.tableName = ParseMapping.getTable(entityClass);
			this.primaryKey = ParseMapping.getId(entityClass);
		} else {
			throw new RuntimeException(entityClass.getSimpleName() + "未映射...");
		}
	}

	public boolean save(Entity entity) {
		Record record = ParseMapping.toRecord(entity, true);
		if (Db.save(tableName, primaryKey, record)) {
			ParseMapping.loadPKToEntity(record, entity);
		}
		return true;
	}

	public boolean deleteById(Object id) {
		return Db.deleteById(tableName, primaryKey, id);
	}

	public boolean delete(Entity entity) {
		Record record = ParseMapping.toRecord(entity, false);
		return Db.delete(tableName, primaryKey, record);
	}

	public boolean update(Entity entity) {
		Record record = ParseMapping.toRecord(entity, false);
		return Db.update(tableName, primaryKey, record);
	}

	public Entity find(Object id) {
		Record record = Db.findById(tableName, primaryKey, id);
		return ParseMapping.toEntity(record, entityClass);
	}

	public List<Entity> findList() {
		List<Record> records = Db.find("select * from " + tableName);
		return ParseMapping.convertList(records, entityClass);
	}

	public int count() {
		return Db.queryLong("select count(1) from " + tableName).intValue();
	}

}
