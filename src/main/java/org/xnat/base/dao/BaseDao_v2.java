package org.xnat.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.xnat.base.dao.util.AutoMap;
import org.xnat.base.dao.util.Page;


/**
 * 不直接用此baseDao_v2(basedao第二版) 外部访问数据　用baseDaoUtil_v?
 * @author xnat
 * Nov 1, 2014 2:52:13 PM
 */
public interface BaseDao_v2 {
	/**
	 * 存入一条记录
	 * @param dbName 数据库名
	 * @param tableName　表名
	 * @param list listmap
	 * @return
	 * Nov 1, 2014 2:54:48 PM
	 */
	public int insert(@Param("dbName")String dbName, @Param("tableName")String tableName, @Param("list")List<AutoMap> list);
	/**
	 * 存入一条记录 保存 自增长键的值到map 中的autoKey
	 * @param map　{dbName: '数据名', tableName: '表名',　autoMaps:　'key:value的list'}
	 * @return
	 * Nov 1, 2014 2:54:41 PM
	 */
	public int insert_v1_2(Map<String, Object> map);
	/**
	 * @param dbName 数据名
	 * @param tableName 表名
	 * @param selectFields 要查询的字段
	 * @param conditions listmap 查询的条件
	 * @param group　分组
	 * @param havingSql 分组having Sql语句
	 * @param sort 排序条件
	 * @param page 分页查询
	 * @return
	 * Nov 1, 2014 3:03:22 PM
	 */
	public List<Map<String, Object>> select(@Param("dbName")String dbName, 
			@Param("tableName") String tableName, 
			@Param("selectFields")List<String> selectFields,
			@Param("conditions")List<AutoMap> conditions, 
			@Param("group")List<String> group, @Param("havingSql") String havingSql, 
			@Param("sort") List<AutoMap> sort, @Param("page")Page page);
	/**
	 * 
	 * @param conditionSql　条件字符串　格式:　"where+条件(如:　in/or ...)"
	 * @return
	 * Nov 1, 2014 3:05:44 PM
	 */
	public List<Map<String, Object>> select_v1_2(@Param("dbName")String dbName, 
			@Param("tableName") String tableName, 
			@Param("selectFields")List<String> selectFields,
			@Param("conditionSql")String conditionSql, @Param("group")List<String> group, @Param("havingSql") String havingSql, 
			@Param("sort") List<AutoMap> sort, @Param("page")Page page);
	
	/**
	 * 自定义sql查询　注意: select 时要加库名
	 * @param sql
	 * @return
	 * Nov 1, 2014 3:23:43 PM
	 */
	public List<Map<String, Object>> selectSql(@Param("sql")String sql);
	
	/**
	 * 删除v1
	 * @param dbName 数据库名
	 * @param tableName　表名
	 * @param conditions　listmap
	 * @return
	 * Nov 1, 2014 3:24:16 PM
	 */
	public int delete(@Param("dbName")String dbName, 
			@Param("tableName")String tableName, 
			@Param("conditions")List<AutoMap> conditions);
	/**
	 * 删除v1_2
	 * @param conditionSql　条件字符串　格式:　"where+条件(如:　in/or ...)"
	 * @return
	 * Nov 1, 2014 3:25:35 PM
	 */
	public int delete_v1_2(@Param("dbName")String dbName, 
			@Param("tableName")String tableName, 
			@Param("conditionSql")String conditionSql);

	/**
	 * 更新v1
	 * @param dbName
	 * @param tableName
	 * @param list
	 * @param conditions
	 * @return
	 * Nov 1, 2014 3:27:23 PM
	 */
	public int update(@Param("dbName")String dbName, 
			@Param("tableName")String tableName, 
			@Param("list") List<AutoMap> list, 
			@Param("conditions")List<AutoMap> conditions);
	/**
	 * 更新v1_2
	 * @param conditionSql 条件字符串　格式:　"where+条件(如:　in/or ...)"
	 * @return
	 * Nov 1, 2014 3:28:00 PM
	 */
	public int update_v1_2(@Param("dbName")String dbName, 
			@Param("tableName")String tableName, 
			@Param("list") List<AutoMap> list, 
			@Param("conditionSql")String conditionSql);
	/**
	 * 根据一个实体查询总数v1
	 * @param 
	 * @return
	 */
	public int getTotal(@Param("dbName")String dbName, 
			@Param("primarykey")String primarykey,
			@Param("tableName")String tableName, 
			@Param("conditions") List<AutoMap> conditions);

	/**
	 * 根据一个实体查询总数v1_2
	 * @param conditionSql 条件字符串　格式:　"where+条件(如:　in/or ...)"
	 * @return
	 * Nov 1, 2014 3:29:55 PM
	 */
	public int getTotal_v1_2(@Param("dbName")String dbName, 
			@Param("primarykey")String primarykey,
			@Param("tableName")String tableName, 
			@Param("conditionSql")String conditionSql);
	
	/**
	 * 统计一个字段所有值的和
	 * @param dbName
	 * @param tableName
	 * @param fieldName
	 * @param conditions
	 * @return
	 * Nov 1, 2014 3:30:56 PM
	 */
	public long countField(@Param("dbName")String dbName, 
			@Param("tableName")String tableName, 
			@Param("fieldName")String fieldName, 
			@Param("conditions") List<AutoMap> conditions);
}
