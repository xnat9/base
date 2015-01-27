package org.xnat.dal.term;

import java.util.ArrayList;
import java.util.List;

import org.xnat.dal.sql.Util_sql;

/**
 * 
 * @author xnat
 */
public final class Where {
	private And and;
	private Or or;
	private In in;
	
	public Where(){}
	public Where(And and) { this.and = and; }
	public Where(Or or) { this.or = or; }
	public Where(In in) { this.in = in; }

	/**===============自定义快捷功能区==================**/
	public static Where andEq(String fieldName, Object fieldValue) {
		return Where.andEq(new String[]{fieldName}, fieldValue);
	}
	
	public static Where andEq(String[] fields, Object... values) {
		Where where = new Where();
		And and = new And();
		for (int i=0; i<fields.length; i++) and.addEq(fields[i], values[i]);
		where.setAnd(and);
		return where;
	}
	/**^^^^^^^^^^^^^自定义快捷功能区^^^^^^^^^^^^^^^^**/
	
	
	@Override
	public String toString() {
		int count = Util_sql.countNotNull(and, or, in);
		if (count < 1) return null;
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE ");
		if (count == 1) sb.append(and+""+or+in);
		else {
			if (and != null) sb.append("("); sb.append(and); sb.append(")");
			if (in != null) {
				sb.append(" AND "); // and, or, in 之间用 "AND" 连接
				sb.append("("); sb.append(in); sb.append(")");
			}
			if (or != null) {
				sb.append(" AND ");
				sb.append("("); sb.append(or); sb.append(")");
			}
		}
		return sb.toString();
	}
	
	public List<Object> getValues() {
		List<Object> values = new ArrayList<Object>();
		if (and != null) values.addAll(and.getValues());
		if (or != null) values.addAll(or.getValues());
		return values;
	}
	
	/**===========getter and setter============**/
	public And getAnd() {
		return and;
	}
	public Where setAnd(And and) {
		this.and = and; return this;
	}
	public Or getOr() {
		return or;
	}
	public Where setOr(Or or) {
		this.or = or; return this;
	}
	public In getIn() {
		return in;
	}
	public Where setIn(In in) {
		this.in = in; return this;
	}
}
