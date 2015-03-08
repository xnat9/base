package org.xnat.base.dao.util;

/**
 * 用以保存一对数据
 * @author xnat
 */
public class Pair {
	private String key;
	private Object value;
	
	
	public Pair() {}
	public Pair(String key, Object value) {
		this.key = key; this.value = value;
	}
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
