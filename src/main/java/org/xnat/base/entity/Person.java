package org.xnat.base.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.xnat.base.dao.util.Pairs;
import org.xnat.base.entity.dao.BaseDao.DaoConfig;
import org.xnat.base.entity.dao.PersonDao;

/**
 * 设计的实体模板
 * @author xnat
 * Oct 21, 2014 10:45:38 AM
 */
@Table(name=Person.tabName, catalog="dbname")
//@Entity(tableName=Person.tabName)
//@Table(name="person", catalog="d1") // name=表名, catalog=数据库名 use entity
public class Person extends BaseEntity {
	private static final long serialVersionUID = -5509060101810976486L;

	public static final String tabName = "person";
	
	@Id //方便操作与id字段有关的
	@Column //注解column　表明此字段是一个与此实体对应的表中的字段 是为了与没有@column的注解字段区分
	private Integer id;
	@Column
	private Integer age;
	@Column
	private String name;
	@Column
	private Integer status;
	
	public Person() {}
	public Person(Integer id, Integer age, String name, Integer status) {
		this.id = id;
		this.age = age;
		this.name = name;
		this.status = status;
	}
	
	public enum Status {
		ABLE("可用", 1),DEL("被删除", 2);
		public final String desc;
		public final Integer value;
		private Status(String desc, Integer value) {
			this.desc = desc;
			this.value = value;
		}
	}
	
	/**
	 * 由bean的字段组成的一个类 方便 直接表达 此bean 的字段
	 * @author xnat
	 * Oct 18, 2014 2:55:00 PM
	 */
	public static final class Field {
		public final static String id = "id";
		public final static String age = "age";
		public final static String name = "name";
		public final static String status = "status";
		
	}
	/**
	 * 实体所有字段由逗号分割组成的字符串, select * 代替这个"*" 
	 */
	public static final String FIELDS = "id,age,name,status";
	public static final int field_count = 4;
	
	/**
	 * 数据访问通道
	 */
	public static PersonDao dao = new PersonDao () {
		/**
		 * 用于配置. 
		 */
		private DaoConfig conf = new DaoConfig(tabName, Field.id, FIELDS);
		@Override
		public DaoConfig daoConf() { return conf; }
		@Override
		public boolean exsit(String name, Integer age) {
			List<Record> list = getByField(new Pairs(2).add(Field.name, name).add(Field.age, age));
			return list != null && list.size() > 0 ? true : false;
		}
//		public List<Record> getByField(List<String> fields, List<Object> values) {
//			return null;
//		};

	};
	
	
	/**=============validate 添加验证================**/
	public boolean validate1() {
		//if .... return false
		return true;
	}
	public boolean validate2() {
//		if ... return false
		return true;
	}
	
	/**================setter and getter=================**/
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**==========================**/
	@Override
	public String toString() {
		return "["+"id="+this.id+"; "+"age="+this.age+"; "+"name="+this.name+"; "+"status="+this.status+"]";
	}
}
