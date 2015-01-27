package org.xnat.dal.term;

import java.util.ArrayList;
import java.util.List;

/**
 * where 连接操作: and,or
 * @author xnat
 */
class Connector {
	//
	private List<Term> terms;
	
	/**
	 * 
	 * @param term
	 * @param count 指定初始化多少term,默认为7个
	 * @return
	 * Jan 24, 2015 11:05:52 AM
	 */
	public Connector add(Term term, int count) {
		if (terms == null) terms = new ArrayList<Term>(count);
		terms.add(term); return this;
	}
	public Connector add(Term term) {
		return add(term, 7);
	}
	/**
	 * 添加一个term默认是 "=" 号
	 */
	public Connector addEq(String fieldName, Object fieldValue) {
		return add(new Term(fieldName, "=", fieldValue));
	}
	/**
	 * 添加一个大于term ">="
	 */
	public Connector addGe(String fieldName, Object fieldValue) {
		return add(new Term(fieldName, ">=", fieldValue));
	}
	/**
	 * 添加一个term
	 * @param fieldName
	 * @param sign
	 * @param fieldValue
	 * @return
	 * Jan 24, 2015 11:52:56 AM
	 */
	public Connector add(String fieldName, String sign, Object fieldValue) {
		return add(new Term(fieldName, sign, fieldValue));
	}
	
	public List<Object> getValues() {
		List<Object> objs = new ArrayList<Object>(terms.size());
		for (Term term : terms) objs.add(term.getFieldValue());
		return objs;
	}
	
	public List<Term> getTerms() {
		return terms;
	}
	
	/**
	 * 
	 * @param connector 值为:"AND", "OR"
	 * @return
	 * Jan 24, 2015 11:47:23 AM
	 */
	public String toString(String connector) {
		List<Term> terms = getTerms();
		if (terms == null) return null;
		StringBuilder sb = new StringBuilder();
		for (int i=0, size = terms.size(); i<size; i++) {
			if (i==(size-1)) sb.append(terms.get(i));
			else sb.append(terms.get(i)+" "+connector+" ");
		}
		return sb.toString();
	}
	
}
