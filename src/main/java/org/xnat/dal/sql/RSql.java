package org.xnat.dal.sql;

import java.util.List;

import org.xnat.dal.term.Where;

/**
 * @see CSql,USql,DSql
 * @author xnat
 */
public class RSql extends WhereSql {
	private boolean distinct = false; 
	//要查询的字段
	private List<String> fieldsList;
	private String fieldsStr;
	
	public RSql() { init(); }
	public RSql(String fieldsStr) { setFieldsStr(fieldsStr); init(); }
	
	private void init() {
		setCurd(CURD.SELECT);
	}
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCurd()+" ");
		if (isDistinct()) sb.append(" DISTINCT ");
		if (getFieldsList() != null) {
			for (String s : getFieldsList()) sb.append(s+",");
			sb.deleteCharAt(sb.length()-1);
		} else if (getFieldsStr() != null && !getFieldsStr().isEmpty()) {
			sb.append(getFieldsStr());
		} else {
			sb.append("*");
		}
		sb.append(" "+getWhere());
		
		setSql(sb.toString()); setBuilded(true);
		return getSql();
	}
	
	@Override
	public List<Object> getValues() {
		return getWhere().getValues();
	}
	

	/**
	 * setWhere的简写
	 */
	public RSql where(Where where) {
		setWhere(where); return this;
	}
	
	
	/**=============getter and setter=============**/
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	public boolean isDistinct() {
		return distinct;
	}
	public List<String> getFieldsList() {
		return fieldsList;
	}
	public void setFieldsList(List<String> fieldsList) {
		this.fieldsList = fieldsList;
	}
	public String getFieldsStr() {
		return fieldsStr;
	}
	public void setFieldsStr(String fieldsStr) {
		this.fieldsStr = fieldsStr;
	}
}
