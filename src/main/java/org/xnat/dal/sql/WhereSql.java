package org.xnat.dal.sql;

import org.xnat.dal.term.Where;

/**
 * 有条件的sql curd中: urd都有可能有条件
 * @author xnat
 */
abstract class WhereSql extends CURDSql {
	private Where where;

	public Where getWhere() {
		return where;
	}
	
	public void setWhere(Where where) {
		this.where = where;
	}
}
