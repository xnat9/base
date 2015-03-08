package org.xnat.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.xnat.base.entity.Person;

import com.google.common.base.Function;
import com.google.common.base.Functions;


public class PublicTest {
	public static void main(String[] args) throws Exception {
//		DefaultAnnotationHandlerMapping
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = dateTimeFormat.parse("2011-1-1 2:2:1");
		System.out.println(d);
		
	}
	
	@Test
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

	@Test
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
			// String name = "名字";
			// @Override
			// public String apply(Date input) {
			// return name;
			// }
			@Override
			public Integer apply(Integer in) {
				return in * in;
			}
		};
		Function<String, Integer> result = Functions.compose(fn1, fn);
		System.out.println(result.apply("k1"));
		// System.out.println(result.apply("key"));
	}

	@Test
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
	@Test
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
	 * 两表相同字段 resultset 都可以取到 Oct 24, 2014 11:43:23 AM
	 */
	// @org.junit.Test
	// public void testTwoTableSameFieldResultsetHandler() {
	// Connection con = DbUtil.getDB().getCon();
	// String sql =
	// "select t1.email, t2.email from user t1, user_account t2 where t1.uid=t2.uid limit 5";
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// try {
	// ps = con.prepareStatement(sql);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// // Array arr = rs.getArray("email");
	// System.out.println(rs.getString(1)+" : "+rs.getString(2));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// DbUtil.getDB().close(rs, ps, con);
	// }
	// }
}
