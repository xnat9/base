package org.xnat.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.xnat.base.dao.util.AutoMap;
import org.xnat.base.dao.util.Page;


/**
 * 不直接用此baseDao 外部访问数据　用baseDaoUtil_v?
 * @author xnat
 *
 */
public interface BaseDao {
	/**
	 * 存入一条记录
	 */
	public Integer insert(@Param("tableName")String tableName, @Param("list")List<AutoMap> list);
	/**
	 * 存入一条记录 保存 自增长键的值到map 中的autoKey
	 */
	public Integer insert_v1_2(Map<String, Object> map);
	/**
	 * select_v4
	 * @param tableName 表名
	 * @param selectFields 要查询的字段
	 * @param conditions 查询的条件
	 * @param group　分组
	 * @param havingSql 分组having Sql语句
	 * @param sort 排序条件
	 * @param page 分页查询
	 * @return
	 */
	public List<Map<String, Object>> select_v4(@Param("tableName") String tableName, 
			@Param("selectFields")List<String> selectFields,
			@Param("conditions")List<AutoMap> conditions, @Param("group")List<String> group, @Param("havingSql") String havingSql, 
			@Param("sort") List<AutoMap> sort, @Param("page")Page page);
	
	public List<Map<String, Object>> select_v4_2(@Param("tableName") String tableName, 
			@Param("selectFields")List<String> selectFields,
			@Param("conditionSql")String conditionSql, @Param("group")List<String> group, @Param("havingSql") String havingSql, 
			@Param("sort") List<AutoMap> sort, @Param("page")Page page);
	
	/**
	 * 自定义sql查询
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> selectSql(@Param("sql")String sql);
	
	
	public int delete_v3(@Param("tableName")String tableName, 
			@Param("conditions")List<AutoMap> conditions);
	public int delete_v3_2(@Param("tableName")String tableName, 
			@Param("conditionSql")String conditionSql);

	public int update_v2(@Param("tableName")String tableName, @Param("list") List<AutoMap> list, @Param("conditions")List<AutoMap> conditions);
	public int update_v2_2(@Param("tableName")String tableName, @Param("list") List<AutoMap> list, @Param("conditionSql")String conditionSql);
	/**
	 * 根据一个实体查询总数
	 * @param 
	 * @return
	 */
	public int getTotal(@Param("tableName")String tableName, @Param("conditions") List<AutoMap> conditions);
	/**
	 * 根据一个实体查询总数
	 * @param 
	 * @return
	 */
	public int getTotal_v1_2(@Param("tableName")String tableName, @Param("conditionSql")String conditionSql);
	
	/**
	 * 统计一个字段所有值的和
	 * @param tableName
	 * @param fieldName
	 * @param conditions
	 * @return
	 */
	public long countField(@Param("tableName")String tableName, @Param("fieldName")String fieldName, @Param("conditions") List<AutoMap> conditions);
}
