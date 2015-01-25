package org.xnat.jdbc.sql;

import java.util.List;

import org.xnat.jdbc.term.Where;

/**
 * @see CSql,USql,DSql
 * @author xnat
 */
public class RSql extends WhereSql implements PreparedSql {
	private boolean distinct = false; 
	//要查询的字段
	private List<String> selectFields;
	
	public RSql(String tabName) { init(tabName, null, null); }
	public RSql(String tabName, List<String> selectFields) { init(tabName, selectFields, null); }
	public RSql(String tabName, List<String> selectFields, Where where) { init(tabName, selectFields, where); }
	
	private void init(String tabName, List<String> selectFields, Where where) {
		setTabName(tabName);
		setSelectFields(selectFields);
		setWhere(where);
		setCurd(CURD.SELECT);
	}
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCurd()+" ");
		if (isDistinct()) sb.append(" DISTINCT ");
		for (String s : selectFields) sb.append(s+",");
		sb.deleteCharAt(sb.length()-1);
		sb.append(getWhere());
		
		setSql(sb.toString()); setBuilded(true);
		return getSql();
	}
	
	@Override
	public List<Object> getValues() {
		return getWhere().getValues();
	}
	
	/**=============getter and setter=============**/
	public void setSelectFields(List<String> selectFields) {
		this.selectFields = selectFields;
	}
	public List<String> getSelectFields() {
		return selectFields;
	}
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	public boolean isDistinct() {
		return distinct;
	}
	
	/**
	 * setWhere的简写
	 */
	public RSql where(Where where) {
		setWhere(where); return this;
	}
}
