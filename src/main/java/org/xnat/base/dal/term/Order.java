package org.xnat.base.dal.term;

/**
 * 排序方式
 * @author xnat
 */
public final class Order {
	private String fieldName;
	private String order;
	
	public Order() {}
	public Order(String fieldName, String order) {
		this.fieldName = fieldName;
		this.order = order;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}
