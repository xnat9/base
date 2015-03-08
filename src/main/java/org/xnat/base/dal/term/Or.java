package org.xnat.base.dal.term;

public class Or extends Connector {
	//or 中的 and
	private And and;
	
	public void setAnd(And and) {
		this.and = and;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString("OR"));
		if (and != null) sb.append(" OR ("+and+")");
		return sb.toString();
	}
}
