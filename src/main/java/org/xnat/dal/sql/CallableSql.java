package org.xnat.jdbc.sql;

import java.util.List;

/**
 * callable sql
 * @author xnat
 */
public class CallableSql extends PreparedSql {

	@Override
	public String build() {
		// TODO Auto-generated method stub
		return getSql();
	}

	@Override
	public List<Object> getValues() {
		// TODO Auto-generated method stub
		return null;
	}
}
