package org.xnat.dao.util;

public enum Dir {
	DESC("降序"), ASC("升序");
	private String comment;
	private Dir(String comment) {
		this.setComment(comment);
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
