package org.xnat.base.dao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

/**
 * 
 * @author xnat
 *
 */
public class AutoMap {
	//键
	private String key;
	//符号
	private String denotation;
	//值
	private Object value;
	
	public AutoMap() {}
	public AutoMap(String key, String denotation, Object value) {
		this.key = key;
		this.denotation = denotation;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDenotation() {
		return denotation;
	}
	public void setDenotation(String denotation) {
		this.denotation = denotation;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 默认符号为"="
	 * @param obj
	 * @return
	 */
	public static List<AutoMap> configSelf(Object obj) {
		return configSelf(obj, "=");
	}
	public static List<AutoMap> configSelf(Object obj, String operator) {
		List<AutoMap> maps = new ArrayList<AutoMap>();
		try {
			for (Field field : obj.getClass().getDeclaredFields()) {
				if (field.getAnnotation(Column.class) == null) continue;
				field.setAccessible(true);
				if (field.get(obj) != null) maps.add(new AutoMap(field.getName(), operator, field.get(obj)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}
	
}
