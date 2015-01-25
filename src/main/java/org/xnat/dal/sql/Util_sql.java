package org.xnat.jdbc.sql;

/**
 * 
 * @author xnat
 */
public final class Util_sql {
	private Util_sql() {}
	/**
	 * 统计有多少个对象不为空
	 * @param objs
	 * @return
	 * Jan 24, 2015 10:45:53 AM
	 */
	public static int countNotNull(Object... objs) {
		int count = 0;
		for (Object o : objs) if (o != null) count++;
		return count;
	}
}
