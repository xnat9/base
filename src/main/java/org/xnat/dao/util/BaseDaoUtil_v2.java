package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xnat.dao.BaseDao_v2;
import org.xnat.dao.DataSourceContextHolder;
import org.xnat.dao.DynamicDataSource;
import org.xnat.dao.Utils_dao;
import org.xnat.dao.annotation.ForeignKey;
import org.xnat.util.Utils;


/**
 * dao 层通用　用的是　BaseDao_v2(第二版)
 * 主要解决getObj...等方法时,做了一些不必要的数据转换(从数据库中获取的数据结构为listmap)
 * 条件分为两种: 
 * 		List<AutoMap> conditions(如果条件不为in, or, is null等时推荐使用, 使用的是preperedStatement)
 * 		String conditionSql: 条件字符串　格式:　"where+条件(如:　in/or ...)(如果条件为in, or等时推荐使用, 使用的是statement)
 * 多数new list 都可以在初始化时确定大小
 * @author xnat
 * Nov 14, 2014 3:48:24 PM
 */
@Component
public class BaseDaoUtil_v2 {
	@Autowired
	private BaseDao_v2 baseDao_v2;
	
	//动态数据源
//	@Autowired
//	private DynamicDataSource dynamicDataSource;
	
	/**
	 * 切换数据源(保留)
	 * @param clazz
	 * Oct 16, 2014 4:19:27 PM
	 */
	public static <T> void setDataSource(Class<T> clazz) {
//		DataSourceContextHolder.DATASOURCE1
	}
	/**
	 * sql insert区
	 */
	
	/**
	 * 插入一条记录
	 * @param obj
	 * @return
	 * Oct 16, 2014 4:23:09 PM
	 */
	public int insert(Object obj) {
		return baseDao_v2.insert(Utils_dao.getDbName(obj.getClass()), 
				Utils_dao.getTableName(obj.getClass()), AutoMap.configSelf(obj));
	}
	/**
	 * 存入一条记录 保存 自增长键的值到map 中的autoKey
	 * @param obj
	 * @param map　{dbName: '数据名', tableName: '表名',　autoMaps:　'(key:value)的list'}
	 * @return
	 */
	public int insert(Object obj, Map<String, Object> map) {
		map.put("dbName", Utils_dao.getDbName(obj.getClass()));
		map.put("tableName", Utils_dao.getTableName(obj.getClass()));
		map.put("autoMaps", AutoMap.configSelf(obj));
		return baseDao_v2.insert_v1_2(map);
	}
	
	/**
	 * 自定义条件删除
	 * @param clazz
	 * @param conditions
	 * @return
	 */
	public <T> int delete(Class<T> clazz, List<AutoMap> conditions) {
		return baseDao_v2.delete(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), conditions);
	}
	/**
	 * @param clazz
	 * @param conditionSql 
	 * @return
	 */
	public <T> int delete(Class<T> clazz, String conditionSql) {
		return baseDao_v2.delete_v1_2(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), conditionSql);
	}
	public <T> int delete(Object obj) {
		return delete(obj.getClass(), AutoMap.configSelf(obj));
	}
	/**
	 * 根据一个字段的值删除
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public <T> int deleteByField(Class<T> clazz, String fieldName, Object fieldValue) {
		List<AutoMap> conditions = new ArrayList<AutoMap>(1);
		conditions.add(new AutoMap(fieldName, "=", fieldValue));
		return delete(clazz, conditions);
	}
	/**
	 * 根据id字段删除
	 * @param clazz
	 * @param id(Object 类型, 可以兼容int, long, string等)
	 * @return
	 */
	public <T> int deleteById(Class<T> clazz, Object id) {
		return deleteByField(clazz, Utils_dao.getIdField(clazz).getName(), id);
	}
	
	
	/**
	 * sql update区
	 */
	
	/**
	 * 根据自定义条件更新
	 * @param clazz
	 * @param setFields 要更新的字段
	 * @param conditions 更新的条件
	 * @return
	 */
	public <T> int update(Class<T> clazz, List<AutoMap> setFields, List<AutoMap> conditions) {
		return baseDao_v2.update(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), setFields, conditions);
	}
	public <T> int update(Class<T> clazz, List<AutoMap> setFields, String conditionSql) {
		return baseDao_v2.update_v1_2(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), setFields, conditionSql);
	}
	/**
	 * 根据实体更新
	 * @param obj
	 * @param conditions
	 * @return
	 */
	public int update(Object obj, List<AutoMap> conditions) {
		return update(obj.getClass(), AutoMap.configSelf(obj), conditions);
	}
	public int update(Object obj, String conditionSql) {
		return update(obj.getClass(), AutoMap.configSelf(obj), conditionSql);
	}
	
	/**
	 * 根据实体的任意一个字段更新
	 * @param entity
	 * @param fieldName
	 * @return
	 */
	public int updateByField(Object entity, String fieldName) {
		List<AutoMap> conditions = new ArrayList<AutoMap>(1);
		conditions.add(new AutoMap(fieldName, "=", DataUtils.bean_getFieldValue(entity, fieldName)));
		return update(entity, conditions); // 这里千万不能写成这样: update(entity.getClass(), conditions)
	}
	/**
	 * 根据id字段更新
	 * @param entity
	 * @return
	 */
	public int updateById(Object entity) {
		return updateByField(entity, Utils_dao.getIdField(entity.getClass()).getName());
	}
	
	/**
	 * 查询专区 包含各种便捷查询
	 */
	
	/**
	 * baseDao_v2中的原始查询 select v1
	 * @param <T>
	 * @param tableName 表名
	 * @param selectFields 要查询的字段
	 * @param conditions 查询的条件
	 * @param group　分组
	 * @param havingSql 分组having Sql语句
	 * @param sort 排序条件
	 * @param page 分页查询
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, List<String> selectFields, 
			List<AutoMap> conditions, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		if (page == null) page = new Page(50);
		if (selectFields == null) selectFields = Utils_dao.getAllFields(clazz);
		return baseDao_v2.select(Utils_dao.getDbName(clazz), 
				Utils_dao.getTableName(clazz), selectFields, conditions, group, havingSql, sort, page);
	}
	/**
	 * baseDao_v2中的原始查询 select_v1_2
	 * @param conditionSql
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, List<String> selectFields, 
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		if (page == null) page = new Page(50);
		if (selectFields == null) selectFields = Utils_dao.getAllFields(clazz);
		return baseDao_v2.select_v1_2(Utils_dao.getDbName(clazz), 
				Utils_dao.getTableName(clazz), selectFields, conditionSql, group, havingSql, sort, page);
	}
	
	/**
	 * 默认查询所有字段
	 * @param clazz
	 * @param conditions
	 * @param group
	 * @param havingSql
	 * @param sort
	 * @param page
	 * @return
	 * Nov 14, 2014 4:47:37 PM
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			List<AutoMap> conditions, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		return select(clazz, null, conditions, group, havingSql, sort, page);
	}
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		return select(clazz, null, conditionSql, group, havingSql, sort, page);
	}

	/**
	 * 根据条件分页查询(查询所有字段)
	 * @param clazz
	 * @param conditions
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, List<AutoMap> conditions, Page page) {
		return select(clazz, null, conditions, null, null, null, page);
	}
	public <T> List<Map<String, Object>> select(Class<T> clazz, String conditionSql, Page page) {
		return select(clazz, null, conditionSql, null, null, null, page);
	}
	
	
//	public <T> List<Map<String, Object>> select(Class<T> clazz, List<String> selectFields, String conditionSql, Page page) {
//		return select(clazz, selectFields, conditionSql, null, null, null, page);
//	}
	
	/**
	 * 
	 * @param clazz
	 * @param conditionSql
	 * @param sorts
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, String conditionSql, List<AutoMap> sorts, Page page) {
		return select(clazz, null, conditionSql, null, null, sorts, page);
	}
//	public <T> List<Map<String, Object>> select(Class<T> clazz, 
//		List<String> selectFields, List<AutoMap> conditions, Page page) {
//		return select(clazz, selectFields, conditions, null, null, null, page);
//	}
	/**
	 * 
	 * @param clazz
	 * @param conditions
	 * @param sorts
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, List<AutoMap> conditions, List<AutoMap> sorts, Page page) {
		return select(clazz, null, conditions, null, null, sorts, page);
	}
	
	/**
	 * 分页,排序,条件
	 * @param obj
	 * @param sorts
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> select(Object obj, List<AutoMap> sorts, Page page) {
		return select(obj.getClass(), null, AutoMap.configSelf(obj), null, null, sorts, page);
	}
	
	/**
	 * 自定义sql(用程序拼装任意sql) 查询 注意: select 时要加库名
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> select(String sql) {
		return baseDao_v2.selectSql(sql);
	}
	
	
	/**
	 * 查询所有(有排序, 分页)
	 * @param clazz
	 * @param sorts
	 * @return
	 */
	public <T> List<Map<String, Object>> getAllByPage(Class<T> clazz, List<AutoMap> sorts, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), (String)null, null, null, sorts, page);
	}
	/**
	 * 查询所有(默认只查询前500条)
	 * @param clazz
	 * @return
	 */
	public <T> List<Map<String, Object>> getAll(Class<T> clazz) {
		return getAllByPage(clazz, null, new Page(0, 500));
	}
	
	/**
	 * 根据一个字段查找一个对象
	 * @param clazz
	 * @param fieldName 字段名
	 * @param fieldValue　字段值
	 * @param selectFields　选择要查询的字段
	 * @return 返回一个由map表示的entity
	 * Oct 31, 2014 5:38:49 PM
	 */
	public <T> Map<String, Object> getMapByField(Class<T> clazz, String fieldName, Object fieldValue, List<String> selectFields) {
		List<AutoMap> conditions = new ArrayList<AutoMap>(1);
		conditions.add(new AutoMap(fieldName, "=", fieldValue));
		return DataUtils.listmap_getFirstMap(select(clazz, selectFields, conditions, null, null, null, new Page(1)));
	}
	//查询所有字段
	public <T> Map<String, Object> getMapByField(Class<T> clazz, String fieldName, Object fieldValue) {
		return getMapByField(clazz, fieldName, fieldValue, null);
	}
	public Map<String, Object> getMapByField(Object obj, String fieldName) {
		List<AutoMap> conditions = new ArrayList<AutoMap>(1);
		conditions.add(new AutoMap(fieldName, "=", DataUtils.bean_getFieldValue(obj, fieldName)));
		return DataUtils.listmap_getFirstMap(select(obj.getClass(), null, conditions, null, null, null, null));
	}
	
	/**
	 * 根据id查找对象
	 * @param clazz
	 * @param id
	 * @return 返回一个由map表示的entity
	 */
	public <T> Map<String, Object> getMapById(Class<T> clazz, int id) {
		return getMapByField(clazz, Utils_dao.getIdField(clazz).getName(), id);
	}
	/**
	 * 根据id查找对象
	 * @param clazz
	 * @param selectFields 自定义要查询的字段
	 * @param id
	 * @return 返回一个由map表示的entity
	 */
	public <T> Map<String, Object> getMapById(Class<T> clazz, int id, List<String> selectFields) {
		return getMapByField(clazz, Utils_dao.getIdField(clazz).getName(), id, selectFields);
	}
	public Map<String, Object> getMapById(Object obj) {
		return getMapByField(obj, Utils_dao.getIdField(obj.getClass()).getName());
	}
	
	/**
	 * 
	 * @param clazz
	 * @param conditions 条件
	 * @param selectFields 自定义要查询的字段
	 * @return 返回一个由map表示的entity
	 */
	public <T> Map<String, Object> getMap(Class<T> clazz, List<String> selectFields, List<AutoMap> conditions) {
		return DataUtils.listmap_getFirstMap(select(clazz, selectFields, conditions, null, null, null, null));
	}
	
	/**
	 * 根据一个字段查找多条记录
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> selectByField(Class<T> clazz, String fieldName, Object fieldValue, Page page) {
		List<AutoMap> conditions = new ArrayList<AutoMap>(1);
		conditions.add(new AutoMap(fieldName, "=", fieldValue));
		return select(clazz, conditions, null, page);
	}
	/**
	 * 根据一个字段查找多条记录
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public <T> List<Map<String, Object>> selectByField(Class<T> clazz, String fieldName, Object fieldValue) {
		return selectByField(clazz, fieldName, fieldValue, null);
	}
	/**
	 * 根据一个字段查找多条记录
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public List<Map<String, Object>> selectByField(Object obj, String fieldName) {
		return selectByField(obj.getClass(), fieldName, DataUtils.bean_getFieldValue(obj, fieldName));
	}
	
	/**
	 * getTotal 区
	 */
	
	/**
	 * 根据条件 得到此条件下的总数
	 * @param clazz
	 * @param conditions
	 * @return
	 */
	public <T> int getTotal(Class<T> clazz, List<AutoMap> conditions) {
		return baseDao_v2.getTotal(Utils_dao.getDbName(clazz), Utils_dao.getIdField(clazz).getName(), Utils_dao.getTableName(clazz), conditions);
	}
	/**
	 * 根据条件 得到此条件下的总数
	 * @param clazz
	 * @param conditionSql 
	 * @return
	 */
	public <T> int getTotal(Class<T> clazz, String conditionSql) {
		return baseDao_v2.getTotal_v1_2(Utils_dao.getDbName(clazz), Utils_dao.getIdField(clazz).getName(), Utils_dao.getTableName(clazz), conditionSql);
	}
	public <T> int getTotal(Class<T> clazz) {
		return getTotal(clazz, (String) null);
	}
	
	/**
	 * 其它查询
	 */
	
	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param conditions
	 * @return
	 * Oct 16, 2014 4:07:44 PM
	 */
	public <T> long countField(Class<T> clazz, String fieldName, List<AutoMap> conditions) {
		return baseDao_v2.countField(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), fieldName, conditions);
	}
}
