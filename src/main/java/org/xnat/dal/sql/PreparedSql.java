package org.xnat.jdbc.sql;

import java.util.List;

/**
 * prepared sql
 * @author xnat
 */
public interface PreparedSql extends Sql {

	/**
	 * 得到占位符的值
	 * @return
	 * Jan 24, 2015 4:43:28 PM
	 */
	List<Object> getValues();
}
