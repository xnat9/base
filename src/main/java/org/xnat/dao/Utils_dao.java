package org.xnat.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.xnat.dao.annotation.Entity;

/**
 * dao 层工具
 * @author xnat
 * Oct 31, 2014 5:02:20 PM
 */
public final class Utils_dao {
	private Utils_dao() {}
	//缓存实体属性(表字段)
	private final static Map<String, List<String>> cache_fields = new HashMap<String, List<String>>(); 
	
	/**
	 * pojo 获取所有字段
	 * @param clazz
	 * @return List<String>
	 */
	public static <T> List<String> getAllFields(Class<T> clazz) {
		List<String> fields = cache_fields.get(clazz.getName());
		if (fields != null) return fields;
		fields = new ArrayList<String>();
		Field[] fs = clazz.getDeclaredFields();
		for (int i=0; i<fs.length; i++) {
			Column col = fs[i].getAnnotation(Column.class);
			if (col == null) continue;
			fields.add(fs[i].getName());
		}
		cache_fields.put(clazz.getName(), fields);
		return fields;
	}
	/**
	 * 得到实体的所对应的表名
	 * @param clazz
	 * @return 数据库中对应的表名
	 * Oct 9, 2014 2:50:15 PM
	 */
	public static <T> String getTableName(Class<T> clazz) {
		return clazz.getAnnotation(Table.class).name();
//		return clazz.getAnnotation(Entity.class).tableName();
	}
	/**
	 * 得到数据库名
	 * @param clazz
	 * @return
	 * Nov 1, 2014 3:42:52 PM
	 */
	public static<T> String getDbName(Class<T> clazz) {
		String dbName = "";
		Table t = clazz.getAnnotation(Table.class);
		if (t != null) dbName = t.schema();
		if (dbName.isEmpty()) {
			dbName = DynamicDatabase.getDbname();
//			return 用静态类的静态字段表示当前数据库
		}
		return dbName;
	}
	
	
	/**
	 * 得到注解为javax.persistence.Id的字段
	 * @param clazz
	 * @return
	 * Oct 21, 2014 11:28:16 AM
	 */
	public static <T> Field getIdField(Class<T> clazz) {
		for (Field f : clazz.getDeclaredFields()) {
			if (f.getAnnotation(javax.persistence.Id.class) == null) continue;
			return f;
		}
		try {
			throw new Exception("此类没有注解为javax.persistence.Id的字段");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
