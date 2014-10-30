package org.xnat.dao.util;

public enum Order {
	ASC("ASC", "升序") {
		
	}, 
	DESC("DESC", "降序") {
		
	};
	private String order;
	private String comment;
	
	private Order() {}

	private Order(String order, String comment) {
		this.order = order;
		this.comment = comment;
	}
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
