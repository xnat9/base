package org.xnat.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用以表示一条记录(记录中也可包含记录)
 * @author xnat
 * @version 1
 */
public class Record implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 此条记录被创建的时间
	 */
	private long time_create;
	/**
	 * 此条记录被读取时间缀
	 */
	private long time_read;
	/**
	 * 此条记录被更新的时间缀
	 */
	private long time_update;
	
	/**
	 * 保存一条记录的字段名和其值
	 */
	private Map<String, Object> record = null;
	
	/**===========构造函数===================**/
	public Record() {
		this.record = new HashMap<String, Object>();
		afterBuild();
	}
	/**
	 * 
	 * @param size 此条记录有多少列(字段)
	 */
	public Record(int size) {
		this.record = new HashMap<String, Object>(size);
		afterBuild();
	}
	
	public Record(Map<String, Object> record) {
		this.record = record;
		afterBuild();
	}
	
	private void afterBuild() {
		time_create = System.currentTimeMillis();
	}
	
	/**===========操作==============**/
	public Record put(String key, Object value) {
		this.record.put(key, value);
		return this;
	}
	public Record put(String key, Map<String, Object> record) {
		this.record.put(key, record);
		return this;
	}
	public Record put(Map<String, Object> record) {
		this.record.putAll(record);
		return this;
	}
	
	public Record remove(String key) {
		this.record.remove(key);
		return this;
	}
	public Record clear() {
		this.record.clear();
		return this;
	}
	
	public Map<String, Object> getRecord() {
		if (this.record == null) this.record = new HashMap<String, Object>(10);
		return this.record;
	}
	
	public String toJsonStr() {
		return "";
	}
	
	/**============setter and getter =============**/
	public long getTime_create() {
		return time_create;
	}
	public void setTime_create(long time_create) {
		this.time_create = time_create;
	}
	public long getTime_read() {
		return time_read;
	}
	public void setTime_read(long time_read) {
		this.time_read = time_read;
	}
	public long getTime_update() {
		return time_update;
	}
	public void setTime_update(long time_update) {
		this.time_update = time_update;
	}
	
}
