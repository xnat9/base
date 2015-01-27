package org.xnat.dal.sql;

import java.util.ArrayList;
import java.util.List;

import org.xnat.dal.exception.SqlBuildException;
import org.xnat.dal.term.Term;
import org.xnat.dal.term.Where;

/**
 * 
 * @author xnat
 */
public class USql extends WhereSql {
	private List<Term> sets;

	public USql() { init(null, null, null); }
	public USql(String tabName) { init(tabName, null, null); }
	public USql(String tabName, Where where) { init(tabName, null, where); }
	public USql(String tabName, List<Term> sets, Where where) { init(tabName, sets, where); }
	
	private void init(String tabName, List<Term> sets, Where where) {
		setTabName(tabName);
		setWhere(where);
		setSets(sets);
		setCurd(CURD.UPDATE);
	}
	
	@Override
	public String build() {
		if (getTabName() == null || getTabName().trim().isEmpty()) {
			throw new SqlBuildException("表名不存在!");
		}
		if (sets == null) {
			throw new SqlBuildException("update sql sets 列表为空!");
		}
		StringBuilder sb = new StringBuilder(50);
		sb.append(getCurd());
		if (getSchema() != null && !getSchema().trim().isEmpty()) {
			sb.append(" "+getSchema()+"."+getTabName());
		} else {
			sb.append(" "+getTabName());
		}
		sb.append(" SET");
		for (Term term : sets) {
			sb.append(" "+term.getFieldName()+"=?,");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" "+getWhere());
		setSql(sb.toString());
		setBuilded(true);
		return getSql();
	}
	/**====================add 添加set term的简便方法===========================**/
	public USql add(String fieldName, Object fieldValue) {
		return add(new Term(fieldName, null, fieldValue));
	}
	public USql add(String[] fields, Object... values) {
		for (int i=0; i<fields.length; i++) add(fields[i], values[i]);
		return this;
	}
	public USql add(Term setTerm) {
		return add(setTerm, 10);
	}
	public USql add(Term setTerm, int count) {
		if (sets == null) sets = new ArrayList<Term>(count);
		sets.add(setTerm);
		return this;
	}
	/**^^^^^^^^^^^^^^^^^^^^add 添加set term的简便方法^^^^^^^^^^^^^^^^^^^^^^**/
	
	
	public void setSets(List<Term> sets) {
		this.sets = sets;
	}
	@Override
	public List<Object> getValues() {
		List<Object> values = new ArrayList<Object>(sets.size());
		for (Term term : sets) values.add(term.getFieldValue()); 
//		if (where != null) 
		return values;
	}
	
	/**
	 * setWhere的简写
	 */
	public USql where(Where where) {
		setWhere(where); return this;
	}
}
