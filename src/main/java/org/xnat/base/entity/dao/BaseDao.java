package org.xnat.base.entity.dao;

import java.util.ArrayList;
import java.util.List;

import org.xnat.base.dal.sql.RSql;
import org.xnat.base.dal.term.And;
import org.xnat.base.dal.term.Where;
import org.xnat.base.dao.util.Pair;
import org.xnat.base.dao.util.Pairs;
import org.xnat.base.entity.Record;

public abstract class BaseDao {
	/**
	 * 插入一条记录
	 * @param rec
	 * @return
	 * Jan 20, 2015 10:58:55 PM
	 */
	public boolean insert(Record rec) {
		return true;
	}
	
//	protected abstract String getTabName();
//	protected abstract String getIdField();
//	protected abstract String getAllFieldStr();
	protected abstract DaoConfig daoConf();
	public class DaoConfig {
		String tabName;
		String idField;
		String allFieldStr;
		public DaoConfig(String tabName, String idField, String allFieldStr) {
			this.tabName = tabName; this.idField = idField; this.allFieldStr = allFieldStr;
		}
		public DaoConfig() {}
	}
	
	/**
	 * 以一个或多个字段为并列条件查询
	 * @param fields
	 * @param values
	 * @return
	 * Jan 18, 2015 3:59:15 PM
	 */
	public List<Record> getByField(Pairs params) {
		List<Record> records = new ArrayList<Record>();
		RSql sql = new RSql(daoConf().allFieldStr);
		sql.setTabName(daoConf().tabName);
		sql.where(new Where().setAnd(pairsToAnd(params)));
		return records;
	}
	private And pairsToAnd(Pairs params) {
		And and = new And();
		for (Pair p :params.getList()) {
			and.addEq(p.getKey(), p.getValue());
		}
		return and;
	}
	public Record getById(Object id) {
		List<Record> records = getByField(new Pairs(1).add(daoConf().idField, id));
		return records != null && records.size() > 0 ? records.get(0) : null;
	}
}
