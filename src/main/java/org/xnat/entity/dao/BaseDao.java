package org.xnat.entity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xnat.dao.util.Pairs;
import org.xnat.entity.Record;

public abstract class BaseDao<T> implements Dao<T> {
	/**
	 * 插入一条记录
	 * @param rec
	 * @return
	 * Jan 20, 2015 10:58:55 PM
	 */
	public boolean insert(Record rec) {
		return true;
	}
	
	protected abstract String getTabName();
	protected abstract String getIdField();
	
	/**
	 * 以一个或多个字段为并列条件查询
	 * @param fields
	 * @param values
	 * @return
	 * Jan 18, 2015 3:59:15 PM
	 */
	public List<Record> getByField(Pairs params) {
		List<Record> records = new ArrayList<Record>();
		return records;
	}
	public List<Record> getByField(Map<String, Object> map) {
		List<Record> records = new ArrayList<Record>();
		return records;
	}
	public Record getById(Object id) {
		List<Record> records = getByField(new Pairs(1).add(getIdField(), id));
		return records != null && records.size() > 0 ? records.get(0) : null;
	}
}
