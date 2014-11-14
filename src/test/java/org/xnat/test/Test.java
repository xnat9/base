package org.xnat.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.xnat.entity.BaseEntity;
import org.xnat.entity.Person;
import org.xnat.util.DbUtil;

import com.google.gson.Gson;

public class Test {
	public static void main(String[] args) {
		BaseEntity p = new Gson().fromJson("", Person.class);
//		MappingSqlQuery<T>
//		AuthenticationManager
//		AccessDecisionManager
	}
	/**
	 * 临时为一个类的方法添加新的执行程序
	 * 
	 * Oct 30, 2014 9:41:51 PM
	 */
	@org.junit.Test
	public void forObjAddTempFieldOrMethod() {
		Thread th = new Thread() {
			@Override
			public void run() {
				super.run();
			}
		};
		String name = "abcd";
		Person p = new Person() {
			private static final long serialVersionUID = 1L;
			public String name = "s";
			public void subMethod() {
				System.out.println("ssssssssss");
			}
			@Override
			public String getName() {
				System.out.println("here");
				subMethod();
				System.out.println(super.getName());
				return name;
			}
		};
		p.setName("private name");
		System.out.println(p.getName());
	}
	
	/**
	 * 两表相同字段 resultset 都可以取到
	 * Oct 24, 2014 11:43:23 AM
	 */
	@org.junit.Test
	public void testTwoTableSameFieldResultsetHandler() {
		Connection con = DbUtil.getDB().getCon();
		String sql = "select t1.email, t2.email from user t1, user_account t2 where t1.uid=t2.uid limit 5";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
//				Array arr = rs.getArray("email");
				System.out.println(rs.getString(1)+" : "+rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.getDB().close(rs, ps, con);
		}
	}
}