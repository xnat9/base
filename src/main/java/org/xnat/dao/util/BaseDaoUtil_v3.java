package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xnat.dao.BaseDao;
import org.xnat.dao.DataSourceContextHolder;
import org.xnat.dao.annotation.Entity;
import org.xnat.dao.annotation.ForeignKey;
import org.xnat.util.Utils;

@Component
public class BaseDaoUtil_v3 {
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 切换数据源
	 * @param clazz
	 * Oct 16, 2014 4:19:27 PM
	 */
	public static <T> void setDataSource(Class<T> clazz) {
		if (DataSourceContextHolder.DATASOURCE1.equals(clazz.getAnnotation(Entity.class).dataSourceId())) {
			DataSourceContextHolder.setDbType(DataSourceContextHolder.DATASOURCE1);
		}
		else if (DataSourceContextHolder.DATASOURCE2.equals(clazz.getAnnotation(Entity.class).dataSourceId())) {
			DataSourceContextHolder.setDbType(DataSourceContextHolder.DATASOURCE2);
		}
		
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
	public Integer insert(Object obj) {
		setDataSource(obj.getClass());
		return baseDao.insert(Utils_dao.getTableName(obj.getClass()), AutoMap.configSelf(obj));
	}
	/**
	 * 插入一条记录 返回自增长id
	 * @param obj
	 * @param map　(保存tableName, autoMaps)
	 * @return
	 */
	public Integer insert(Object obj, Map<String, Object> map) {
		setDataSource(obj.getClass());
		map.put("tableName", Utils_dao.getTableName(obj.getClass()));
		map.put("autoMaps", AutoMap.configSelf(obj));
		return baseDao.insert_v1_2(map);
	}
	
	/**
	 * 自定义条件删除
	 * @param clazz
	 * @param conditions
	 * @return
	 */
	public <T> int delete(Class<T> clazz, List<AutoMap> conditions) {
		setDataSource(clazz);
		return baseDao.delete_v3(Utils_dao.getTableName(clazz), conditions);
	}
	public <T> int delete(Object obj) {
		return delete(obj.getClass(), AutoMap.configSelf(obj));
	}
	public <T> int delete(Class<T> clazz, String conditionSql) {
		setDataSource(clazz);
		return baseDao.delete_v3_2(Utils_dao.getTableName(clazz), conditionSql);
	}
	/**
	 * 根据一个字段的值删除
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public <T> int deleteByField(Class<T> clazz, String fieldName, Object fieldValue) {
		List<AutoMap> conditions = new ArrayList<AutoMap>();
		conditions.add(new AutoMap(fieldName, "=", fieldValue));
		return delete(clazz, conditions);
	}
	/**
	 * 根据id字段删除
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> int deleteById(Class<T> clazz, Object id) {
		return deleteByField(clazz, Utils_dao.getIdField(clazz).getName(), id);
	}
	
	
	
	/**
	 * sql update区
	 */
	
	/**
	 * 根据自定义(多个)条件更新
	 * @param clazz
	 * @param setFields
	 * @param conditions
	 * @return
	 * Oct 10, 2014 5:53:49 PM
	 */
	public <T> int update(Class<T> clazz, List<AutoMap> setFields, List<AutoMap> conditions) {
		setDataSource(clazz);
		return baseDao.update_v2(Utils_dao.getTableName(clazz), setFields, conditions);
	}
	public int update(Object obj, List<AutoMap> setFields, String conditionSql) {
		setDataSource(obj.getClass());
		return baseDao.update_v2_2(Utils_dao.getTableName(obj.getClass()), setFields, conditionSql);
	}
	
	/**
	 * 根据自定义(多个)条件更新
	 * @param obj
	 * @param conditions
	 * @return
	 */
	public int update(Object obj, List<AutoMap> conditions) {
		return update(obj.getClass(), AutoMap.configSelf(obj), conditions);
	}
	
	/**
	 * 根据任意一个字段更新
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public int updateByField(Object obj, String fieldName) {
		ArrayList<AutoMap> conditions = new ArrayList<AutoMap>();
		conditions.add(new AutoMap(fieldName, "=", DataUtils.bean_getFieldValue(obj, fieldName)));
		return update(obj, conditions);
	}
	/**
	 * 根据id字段更新
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public int updateById(Object obj) {
		return updateByField(obj, Utils_dao.getIdField(obj.getClass()).getName());
	}
	
	/**
	 * 查询专区 包含各种便捷查询
	 */
	
	/**
	 * baseDao select_v4
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
	private <T> List<Map<String, Object>> select(Class<T> clazz, 
			List<String> selectFields, List<AutoMap> conditions, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		setDataSource(clazz);
		return baseDao.select_v4(Utils_dao.getTableName(clazz), selectFields, conditions, group, havingSql, sort, page);
	}
	/**
	 * baseDao select_v4_2
	 * @param tableName
	 * @param selectFields
	 * @param conditionSql 条件为字符串 以 "where 开头"
	 * @param group
	 * @param havingSql
	 * @param sort
	 * @param page
	 * @return
	 * Oct 16, 2014 3:13:49 PM
	 */
	private <T> List<Map<String, Object>> select(Class<T> clazz, List<String> selectFields,
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		setDataSource(clazz);
		return baseDao.select_v4_2(Utils_dao.getTableName(clazz), selectFields, conditionSql, group, havingSql, sort, page);
	}
	/**
	 * 默认查询所有字段
	 * @param clazz
	 * @param conditions 条件为List<AutoMap>
	 * @param group
	 * @param havingSql
	 * @param sort
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			List<AutoMap> conditions, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditions, group, havingSql, sort, page);
	}
	/**
	 * 默认查询所有字段
	 * @param clazz
	 * @param conditionSql 条件为字符串 以 "where 开头"
	 * @param group
	 * @param havingSql
	 * @param sort
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditionSql, group, havingSql, sort, page);
	}
	/**
	 * 
	 * @param clazz
	 * @param conditionSql 条件为字符串 以 "where 开头"
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, String conditionSql, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditionSql, null, null, null, page);
	}
	/**
	 * 
	 * @param clazz
	 * @param selectFields 自定义要查询的字段
	 * @param conditionSql 条件为字符串 以 "where 开头"
	 * @param page
	 * @return
	 * Oct 16, 2014 3:33:22 PM
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			List<String> selectFields, String conditionSql, Page page) {
		return select(clazz, selectFields, conditionSql, null, null, null, page);
	}
	/**
	 * 
	 * @param clazz
	 * @param conditionSql
	 * @param sort
	 * @param page
	 * @return
	 * Oct 16, 2014 3:34:34 PM
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			String conditionSql, List<AutoMap> sort, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditionSql, null, null, sort, page);
	}
	/**
	 * 
	 * @param clazz
	 * @param conditions
	 * @param sorts
	 * @param page
	 * @return
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, List<AutoMap> conditions, List<AutoMap> sorts, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditions, null, null, sorts, page);
	}
	
	/**
	 * 
	 * @param clazz
	 * @param conditions
	 * @param sorts
	 * @param page
	 * @param lookupForeignKey 是否查找其关联的外键
	 * @return
	 * Oct 16, 2014 9:38:43 PM
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			List<AutoMap> conditions, List<AutoMap> sorts, Page page, boolean lookupForeignKey) {
		List<Map<String, Object>> list = select(clazz, conditions, sorts, page);
		if (!lookupForeignKey) return list;
		
		for (Field field :clazz.getDeclaredFields()) {
			ForeignKey foreignKeyAnnotation = field.getAnnotation(ForeignKey.class);
			if (foreignKeyAnnotation == null) continue;
			
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> maps = getObjsByField(foreignKeyAnnotation.refEntity(), 
						foreignKeyAnnotation.refFiled(), map.get(field.getName()), page);
				if (maps.size() > 1) {
					map.put(Utils.firstLetterToLower(foreignKeyAnnotation.refEntity().getSimpleName()), maps);
				}
				else {
					map.put(Utils.firstLetterToLower(foreignKeyAnnotation.refEntity().getSimpleName()), DataUtils.listmap_getFirstMap(maps));
				}
			}
		}
		return list;
	}
	
	/**
	 * 分页,排序,条件
	 * @param obj
	 * @param page
	 * @param sorts
	 * @return
	 */
	public List<Map<String, Object>> select(Object obj, Page page, List<AutoMap> sorts) {
		return select(obj.getClass(), Utils_dao.getAllFields(obj.getClass()), 
				AutoMap.configSelf(obj), null, null, sorts, page);
	}
	
	/**
	 * 自定义sql(用程序拼装任意sql) 查询
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> select(String sql) {
		return baseDao.selectSql(sql);
	}
	/**
	 * 
	 * @param sql
	 * @param clazz 为了确定数据源
	 * @return
	 * Oct 16, 2014 2:21:45 PM
	 */
	public <T> List<Map<String, Object>> select(String sql, Class<T> clazz) {
		setDataSource(clazz);
		return baseDao.selectSql(sql);
	}
	/**
	 * 查询所有
	 * @param clazz
	 * @return
	 */
	public <T> List<Map<String, Object>> getAll(Class<T> clazz) {
		return getAllByPage(clazz, null, new Page(0, 500));
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
	 * 从返回的 List<Map<String, Object>> 中查找一个
	 * @param maps
	 * @param key
	 * @return
	 */
//	public Map<String, Object> getMapFromList(List<Map<String, Object>> maps, String key) {
//		for (Map<String, Object> map: maps) {
//			Iterator<String> it = map.keySet().iterator();
//			while (it.hasNext()) if (key.equals(it.next())) return map; 
//		}
//		return null;
//	}
	/**
	 * 根据一个字段查找一个对象
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public Object getObjByField(Object obj, String fieldName) {
		List<AutoMap> conditions = new ArrayList<AutoMap>();
		conditions.add(new AutoMap(fieldName, "=", DataUtils.bean_getFieldValue(obj, fieldName)));
		List<Map<String, Object>> objs = select(obj.getClass(), conditions, null, null);
		
		if (objs == null || objs.size() < 1) return null;
		try {
			BeanUtils.populate(obj, objs.get(0));
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj; 
	}
	public <T> Object getObjByField(Class<T> clazz, String fieldName, Object fieldValue) {
		List<AutoMap> conditions = new ArrayList<AutoMap>();
		conditions.add(new AutoMap(fieldName, "=", fieldValue));
		List<Map<String, Object>> objs = select(clazz, conditions, null, null);
		if (objs == null || objs.size() < 1) return null;
		T obj = null;
		try {
			obj = clazz.newInstance();
			BeanUtils.populate(obj, objs.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj; 
	}
	/**
	 * 根据id查找对象
	 * @param obj
	 * @return
	 */
	public <T> Object getObjById(Class<T> clazz, Integer id) {
		return getObjByField(clazz, Utils_dao.getIdField(clazz).getName(), id);
	}
	public Object getObjById(Object obj) {
		return getObjByField(obj, Utils_dao.getIdField(obj.getClass()).getName());
	}
	/**
	 * 根据一个字段查找多条记录
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public List<Map<String, Object>> getObjsByField(Object obj, String fieldName) {
		return getObjsByField(obj.getClass(), fieldName, DataUtils.bean_getFieldValue(obj, fieldName));
	}
	/**
	 * 根据一个字段查找多条记录
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @param page
	 * @return
	 * Oct 17, 2014 10:00:33 AM
	 */
	public <T> List<Map<String, Object>> getObjsByField(Class<T> clazz, String fieldName, Object fieldValue, Page page) {
		List<AutoMap> conditions = new ArrayList<AutoMap>();
		conditions.add(new AutoMap(fieldName, "=", fieldValue));
		return select(clazz, conditions, null, page);
	}
	/**
	 * 根据一个字段查找多条记录
	 * @param <T>
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public <T> List<Map<String, Object>> getObjsByField(Class<T> clazz, String fieldName, Object fieldValue) {
		return getObjsByField(clazz, fieldName, fieldValue, null);
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
		setDataSource(clazz);
		return baseDao.getTotal(Utils_dao.getTableName(clazz), conditions);
	}
	/**
	 * 根据条件 得到此条件下的总数
	 * @param clazz
	 * @param conditionSql 
	 * @return
	 */
	public <T> int getTotal(Class<T> clazz, String conditionSql) {
		setDataSource(clazz);
		return baseDao.getTotal_v1_2(Utils_dao.getTableName(clazz), conditionSql);
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
		setDataSource(clazz);
		return baseDao.countField(Utils_dao.getTableName(clazz), fieldName, conditions);
	}
}
