package org.xnat.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.xnat.base.dao.BaseDao_v2;
import org.xnat.base.dao.util.AutoMap;
import org.xnat.base.dao.util.Page;

public class BaseDaoImpl implements BaseDao_v2 {

	@Override
	public int insert(String dbName, String tableName, List<AutoMap> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert_v1_2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> select(String dbName, String tableName, List<String> selectFields,
			List<AutoMap> conditions, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> select_v1_2(String dbName, String tableName, List<String> selectFields,
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectSql(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(String dbName, String tableName, List<AutoMap> conditions) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete_v1_2(String dbName, String tableName, String conditionSql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String dbName, String tableName, List<AutoMap> list, List<AutoMap> conditions) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update_v1_2(String dbName, String tableName, List<AutoMap> list, String conditionSql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotal(String dbName, String primarykey, String tableName, List<AutoMap> conditions) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotal_v1_2(String dbName, String primarykey, String tableName, String conditionSql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countField(String dbName, String tableName, String fieldName, List<AutoMap> conditions) {
		// TODO Auto-generated method stub
		return 0;
	}
}
