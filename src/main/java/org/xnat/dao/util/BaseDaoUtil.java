package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xnat.dao.BaseDao_v2;
import org.xnat.dao.Utils_dao;
import org.xnat.dao.annotation.ForeignKey;
import org.xnat.util.DataUtils;
import org.xnat.util.Utils;

/**
 * dao 层通用　用的是　BaseDao_v2(第二版)
 * @author xnat
 * Nov 1, 2014 3:45:09 PM
 */
@Component
public class BaseDaoUtil {
	@Autowired
	private BaseDao_v2 baseDao_v2;
	
	/**
	 * 切换数据源
	 * @param clazz
	 * Oct 16, 2014 4:19:27 PM
	 */
	public static <T> void setDataSource(Class<T> clazz) {
		
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
		return delete(clazz, "WHERE "+fieldName+"="+fieldValue);
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
		return baseDao_v2.update(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), setFields, conditions);
	}
	public <T> int update(Class<T> clazz, List<AutoMap> setFields, String conditionSql) {
		return baseDao_v2.update_v1_2(Utils_dao.getDbName(clazz), Utils_dao.getTableName(clazz), setFields, conditionSql);
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
	public int update(Object obj, String conditionSql) {
		return update(obj.getClass(), AutoMap.configSelf(obj), conditionSql);
	}
	
	/**
	 * 根据任意一个字段更新
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public int updateByField(Object obj, String fieldName) {
		return update(obj, "WHERE "+fieldName+"="+DataUtils.bean_getFieldValue(obj, fieldName));
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
	 * baseDao select v1
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
		if (page == null) page = new Page(50);
		if (selectFields == null) selectFields = Utils_dao.getAllFields(clazz);
		return baseDao_v2.select(Utils_dao.getDbName(clazz), 
				Utils_dao.getTableName(clazz), selectFields, conditions, group, havingSql, sort, page);
	}
	/**
	 * @param conditionSql 条件字符串　格式:　"where+条件(如:　in/or ...)"
	 * @return
	 * Nov 1, 2014 7:21:40 PM
	 */
	private <T> List<Map<String, Object>> select(Class<T> clazz, List<String> selectFields,
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		if (page == null) page = new Page(100);
		if (selectFields == null) selectFields = Utils_dao.getAllFields(clazz);
		return baseDao_v2.select_v1_2(Utils_dao.getDbName(clazz), 
				Utils_dao.getTableName(clazz), selectFields, conditionSql, group, havingSql, sort, page);
	}
	/**
	 * 默认查询所有字段
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			List<AutoMap> conditions, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditions, group, havingSql, sort, page);
	}
	/**
	 * 默认查询所有字段
	 * @param conditionSql 条件为字符串 以 "where 开头"
	 */
	public <T> List<Map<String, Object>> select(Class<T> clazz, 
			String conditionSql, List<String> group, String havingSql, List<AutoMap> sort, Page page) {
		return select(clazz, Utils_dao.getAllFields(clazz), conditionSql, group, havingSql, sort, page);
	}

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
	 * 尽量少用 因为条件不能用preperedStatment
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
	@Deprecated
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
	 * @return
	 * Oct 31, 2014 5:38:49 PM
	 */
	public <T> Object getObjByField(Class<T> clazz, String fieldName, Object fieldValue, List<String> selectFields) {
		String conditionSql = "WHERE "+fieldName+"="+fieldValue;
		List<Map<String, Object>> objs = select(clazz, selectFields, conditionSql, new Page(1));
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
	public Object getObjByField(Object obj, String fieldName) {
		List<Map<String, Object>> objs = select(obj.getClass(), 
				"WHERE "+fieldName+"="+DataUtils.bean_getFieldValue(obj, fieldName), null, null);
		
		if (objs == null || objs.size() < 1) return null;
		try {
			BeanUtils.populate(obj, objs.get(0));
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj; 
	}
	
	public <T> Object getObjByField(Class<T> clazz, String fieldName, Object fieldValue) {
		return getObjByField(clazz, fieldName, fieldValue, Utils_dao.getAllFields(clazz));
	}
	/**
	 * 根据id查找对象
	 * @param obj
	 * @return
	 */
	public <T> Object getObjById(Class<T> clazz, int id) {
		return getObjByField(clazz, Utils_dao.getIdField(clazz).getName(), id);
	}
	/**
	 * 根据id查找对象
	 * @param clazz
	 * @param selectFields 自定义要查询的字段
	 * @param id
	 * @return
	 * Nov 14, 2014 12:02:18 PM
	 */
	public <T> Object getObjById(Class<T> clazz, int id, List<String> selectFields) {
		return getObjByField(clazz, Utils_dao.getIdField(clazz).getName(), id, selectFields);
	}
	public Object getObjById(Object obj) {
		return getObjByField(obj, Utils_dao.getIdField(obj.getClass()).getName());
	}
	
	/**
	 * 
	 * @param clazz
	 * @param conditions 条件
	 * @param selectFields 自定义要查询的字段
	 * @return
	 * Nov 14, 2014 1:20:15 PM
	 */
	public <T> Object getObj(Class<T> clazz, List<AutoMap> conditions, List<String> selectFields) {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
		}
		DataUtils.toBeanByInject(obj, DataUtils.listmap_getFirstMap(
				select(clazz, selectFields, conditions, null, null, null, null)));
		return obj;
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
		return select(clazz, "WHERE "+fieldName+"="+fieldValue, null, page);
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
	 * 根据一个字段查找多条记录(最多500条记录)
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * Nov 1, 2014 7:39:43 PM
	 */
	public <T> List<Map<String, Object>> getObjsByField(Class<T> clazz, String fieldName, Object fieldValue) {
		return getObjsByField(clazz, fieldName, fieldValue, new Page(500));
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
