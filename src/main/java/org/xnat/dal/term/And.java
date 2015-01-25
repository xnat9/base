package org.xnat.jdbc.term;

/**
 * sql 中的 and 
 * @author xnat
 */
public final class And extends Connector {
	//and 中的 or
	private Or or;
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString("AND"));
		if (or != null) sb.append(" AND ("+or+")");
		return sb.toString();
	}
	
	public void setOr(Or or) {
		this.or = or;
	}
}
