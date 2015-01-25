package org.xnat.dao.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 多对pair
 * @author xnat
 */
public final class Pairs {
	private List<Pair> me;
	
	public Pairs() { this(5); }
	public Pairs(int initCount) {
		me = new ArrayList<Pair>(initCount);
	}
	public Pairs add(Pair p) {
		me.add(p);
		return this;
	}
	public Pairs add(String key, Object value) {
		me.add(new Pair(key, value));
		return this;
	}
}
