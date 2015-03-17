package org.xnat.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;


import org.apache.commons.collections.map.LRUMap;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xnat.base.entity.Person;
import org.xnat.base.util.LRUCache;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PublicTest {
	public static void main(String[] args) throws Exception {
//		DefaultAnnotationHandlerMapping
//		System.out.println("getName".indexOf("get"));
//		JdbcTemplate tmp = new JdbcTemplate();
//		URL url = new URL("http://api.map.baidu.com/location/ip?ak=E4805d16520de693a3fe707cdc962045&ip=202.198.16.3&coor=bd09ll");
//		URLConnection con = url.openConnection();
//		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//		System.out.println(br.readLine());
//		if (true) return;
		/**
		 * {
	"address", "CN|吉林|长春|None|CERNET|0|0"
    "content": {
        "address": "吉林省长春市",
        "address_detail": {
            "city": "长春市",
            "city_code": 53,
            "district": "",
            "province": "吉林省",
            "street": "",
            "street_number": ""
        },
        "point": {
            "x": "125.31364243",
            "y": "43.89833761"
        }
    },
    "status": 0
}
		 */
		Runtime rt = Runtime.getRuntime();
		System.out.println("totalMem: "+rt.totalMemory()+"freeMem:"+rt.freeMemory()+", diff:"+(rt.totalMemory()-rt.freeMemory()));
		JsonObject result = new JsonObject();
		result.addProperty("address", "CN|吉林|长春|None|CERNET|0|0");
		JsonObject content = new JsonObject();;
		result.add("content", content);
		content.addProperty("address", "吉林省长春市");
		JsonObject address_detail = new JsonObject();
		result.add("address_detail", address_detail);
		address_detail.addProperty("city", "长春市");
		address_detail.addProperty("city_code", 53);
		address_detail.addProperty("district", "");
		address_detail.addProperty("province", "吉林省");
		address_detail.addProperty("street", "");
		address_detail.addProperty("street_number", "");
		JsonObject point = new JsonObject();
		result.add("point", point);
		point.addProperty("x", "125.31364243");
		point.addProperty("y", "43.89833761");
		result.addProperty("status", 0);
		LRUCache lruMap = new LRUCache(1000);
		for (int i=0; i<100000; i++) {
			lruMap.put("a"+i, 1);
		}
		System.out.println("totalMem: "+rt.totalMemory()+"freeMem:"+rt.freeMemory()+", diff:"+(rt.totalMemory()-rt.freeMemory()));
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
