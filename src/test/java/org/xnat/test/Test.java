package org.xnat.test;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xnat.entity.Person;
import org.xnat.util.DataUtils;
import org.xnat.util.DbUtil;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jfinal.core.ActionKey;

public class Test {
	public static void main(String[] args) throws Exception {
		String s = WordUtils.wrap("我靠居然中文不行，那怎么办呢？", 10);
		System.out.println("("+s+")");
	}
	@org.junit.Test
	public void testVarInParentAndSubClass() {
		class Parent {
			int i;
			public Parent() {
				i = 100;
			}
		}
		class SubClass {
			int i;
			public SubClass() {
				super();
				i = 200;
			}
		}
		SubClass subClass = new SubClass();
		System.out.println(subClass.i);
	}
	
	
	@org.junit.Test
	public void testGuavaFunction() {
		@SuppressWarnings("serial")
		Map<String, Integer> map = new HashMap<String, Integer>() {
			{
				put("k1", 1);
				put("k2", 2);
			}
		};
		System.out.println(map.get("k1"));
		Function<String, Integer> fn = Functions.forMap(map);
		System.out.println(fn.apply("k2"));
		
		Function<Integer, Integer> fn1 = new Function<Integer, Integer>() {
//			String name = "名字";
//			@Override
//			public String apply(Date input) {
//				return name;
//			}
			@Override
			public Integer apply(Integer in) {
				return in * in;
			}
		};
		Function<String, Integer> result = Functions.compose(fn1, fn);
		System.out.println(result.apply("k1"));
//		System.out.println(result.apply("key"));
	}
	@org.junit.Test
	public void publicTest() {
		Person p = new Person();
		p.setStatus(Person.Status.DEL.value);
		System.out.println(Person.Status.DEL.value);
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
//	@org.junit.Test
//	public void testTwoTableSameFieldResultsetHandler() {
//		Connection con = DbUtil.getDB().getCon();
//		String sql = "select t1.email, t2.email from user t1, user_account t2 where t1.uid=t2.uid limit 5";
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ps = con.prepareStatement(sql);
//			rs = ps.executeQuery();
//			while (rs.next()) {
////				Array arr = rs.getArray("email");
//				System.out.println(rs.getString(1)+" : "+rs.getString(2));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DbUtil.getDB().close(rs, ps, con);
//		}
//	}
}
