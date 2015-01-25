package org.xnat.entity.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xnat.entity.Person;
import org.xnat.entity.Record;
import org.xnat.jdbc.sql.Sql;

public abstract class PersonDao extends BaseDao<Person> {
	/**
	 * 模拟表结构
	 */
//	public static final List<Map<String, List<Object>>> table = new ArrayList<Map<String, List<Object>>>(field_count);
//	public void initTable() {
//		table.add(new HashMap<String, List<Object>>)
//	}
	/**
	 * 数据保存策略
	 */
	public static final List<Record> data = new LinkedList<Record>();
	
	/**
	 * 下面三个方法属于配置方法, 用于配置. 
	 * 比如tabName, 可以在查询之前被更改; id字段,也可从entity中的属性字段的annotation配置中得到
	 */
	@Override
	public String getTabName() { return Person.tabName; }
	public String getIdField() { return Person.Field.id; }
	
	/**
	 * 同步(有的实体的字段更改了不会立即被持久化,即同步到数据库)
	 * @return
	 * Jan 18, 2015 3:30:29 PM
	 */
	private boolean sync() {
		return true;
	}
	
	public abstract boolean exsit(String name, Integer age);
}
