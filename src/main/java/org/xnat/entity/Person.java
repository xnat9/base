package org.xnat.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.xnat.dao.annotation.Entity;

/**
 * 设计的实体模板
 * @author xnat
 * Oct 21, 2014 10:45:38 AM
 */
@Table(name="person")
@Entity(tableName=Person.tabName)
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
	
	/**
	 * 由bean的字段组成的一个类 方便 直接表达 此bean 的字段
	 * @author xnat
	 * Oct 18, 2014 2:55:00 PM
	 */
	public static final class Fields {
		public final static String id = "id";
		public final static String age = "age";
		public final static String name = "name";
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
	
}
