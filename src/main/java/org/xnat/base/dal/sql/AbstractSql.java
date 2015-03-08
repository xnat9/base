package org.xnat.base.dal.sql;

/**
 * sql
 * @author xnat
 */
abstract class AbstractSql implements BuildSql,PreparedSql {
	//终极sql字符串
	private String sql;
	//mysql中是数据库名
	private String schema;
	//表名
	private String tabName;
	private boolean builded;
	
	public AbstractSql() {}
	public AbstractSql(String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		//sql只构建一次
//		if (!isBuilded()) 
//		build();
		return getSql();
	}
	/**==============getter and setter=============**/
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public boolean isBuilded() {
		return builded;
	}
	public void setBuilded(boolean builded) {
		this.builded = builded;
	}
}
