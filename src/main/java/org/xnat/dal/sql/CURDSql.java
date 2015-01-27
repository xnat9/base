package org.xnat.dal.sql;

/**
 * 分为: 增删查改sql
 * @see CSql, DSql, RSql, USql
 * @author xnat
 */
abstract class CURDSql extends AbstractSql {
	private CURD curd;
	
	
	public void setCurd(CURD curd) {
		this.curd = curd;
	}
	public CURD getCurd() {
		return curd;
	}
	@Override
	public String toString() {
		return curd.name();
	}
}
