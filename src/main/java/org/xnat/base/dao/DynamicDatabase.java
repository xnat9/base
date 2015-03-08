package org.xnat.base.dao;

/**
 * 在一个数据源里动态切换数据库()
 * @author xnat
 * Nov 2, 2014 12:00:23 AM
 */
public class DynamicDatabase {
	private static final ThreadLocal<String> currentDb = new ThreadLocal<String>();

	public static String getDbname() {
		return currentDb.get();
	}
	public static void setDbname(String dbName) {
		currentDb.set(dbName);
	}
	public static void cleanCurrentDb() {
		currentDb.remove();
	}
}
