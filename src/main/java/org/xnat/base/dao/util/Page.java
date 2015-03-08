package org.xnat.base.dao.util;

public class Page {
	private int start;
	private int limit;
	private int page;
	
	public Page() {}
	
	public Page(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}
	public Page(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
