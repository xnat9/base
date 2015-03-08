package org.xnat.base.entity.dao;

import java.util.LinkedList;
import java.util.List;

import org.xnat.base.entity.Record;

/**
 * 公共dao
 * 这一层主在的功能做缓存, 方法声明
 * @author xnat
 */
public abstract class PersonDao extends BaseDao {
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
	 * 同步(有的实体的字段更改了不会立即被持久化,即同步到数据库)
	 * @return
	 * Jan 18, 2015 3:30:29 PM
	 */
	private boolean sync() {
		return true;
	}
	public abstract boolean exsit(String name, Integer age);
//	public abstract boolean exsitPerson(String name, Integer age);
}
