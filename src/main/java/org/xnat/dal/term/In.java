package org.xnat.jdbc.term;

import java.util.ArrayList;
import java.util.List;

/**
 * in条件
 * @author xnat
 */
public final class In {
	private String fieldName;
	private List<Object> values;
	
	public In() {}
	public In(String fieldName, Object...values) {
		this.fieldName = fieldName;
		this.values = new ArrayList<Object>(values.length);
		for (Object o : values) this.values.add(o);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(30);
		sb.append(" "+fieldName+" IN (");
		for (Object o : values) sb.append(o+", ");
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
	
	/**========getter and setter========**/
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
