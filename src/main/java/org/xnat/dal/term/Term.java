package org.xnat.dal.term;

import org.xnat.dal.exception.SqlBuildException;

/**
 * sql 语句 term 
 * @author xnat
 */
public class Term {
	private String fieldName;
	private String sign; //符号: =;>;>=;<;<=;!=
	private Object fieldValue;
	
	public Term() {}
	public Term(String fieldName, String sign, Object fieldValue) {
		this.fieldName = fieldName;
		this.sign = sign;
		this.fieldValue = fieldValue;
	}

	@Override
	public String toString() {
		if (fieldName == null) {
			throw new SqlBuildException("term 中的fieldName为空!");
		}
		return fieldName+sign+"?";
	}
	
	/**========getter and setter========**/
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
