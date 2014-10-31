package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.xnat.dao.annotation.Entity;

/**
 * dao 层工具
 * @author xnat
 * Oct 31, 2014 5:02:20 PM
 */
public final class Utils_dao {
	private Utils_dao() {}
	
	/**
	 * pojo 获取所有字段
	 * @param clazz
	 * @return List<String>
	 */
	public static <T> List<String> getAllFields(Class<T> clazz) {
		List<String> fields = new ArrayList<String>();
		Field[] fs = clazz.getDeclaredFields();
		for (int i=0; i<fs.length; i++) {
			Column col = fs[i].getAnnotation(Column.class);
			if (col == null) continue;
			fields.add(fs[i].getName());
		}
		return fields;
	}
	/**
	 * 得到实体的所对应的表名
	 * @param clazz
	 * @return 数据库中对应的表名
	 * Oct 9, 2014 2:50:15 PM
	 */
	public static <T> String getTableName(Class<T> clazz) {
//		return clazz.getAnnotation(Table.class).name();
		return clazz.getAnnotation(Entity.class).tableName();
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
