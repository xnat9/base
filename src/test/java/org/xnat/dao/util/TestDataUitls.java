package org.xnat.dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.xnat.base.dao.util.Dir;
import org.xnat.base.dao.util.Page;
import org.xnat.base.entity.Person;
import org.xnat.base.util.DataUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class TestDataUitls {
	
	@Test
	public void testBean_setValueForField() {
		Person p = new Person();
		String name = "sssssssssssssssss";
		DataUtils.bean_setValueForField(p, Person.Field.name, name);
		Assert.assertArrayEquals("相等", new String[]{name}, new String[]{p.getName()});
	}
	
	@Test
	public void testBean_getFieldValue() {
		Person p = new Person();
		p.setName("test");
		Object v = DataUtils.bean_getFieldValue(p, Person.Field.name);
		System.out.println(v);
	}
	
	@Test
	public void testJa_getByPage() {
		JsonArray ja = new JsonArray();
		JsonObject jo = new JsonObject();
		jo.addProperty("name", "abcd"); jo.addProperty("age", 1);
		ja.add(jo);
		jo = new JsonObject();
		jo.addProperty("name", "abc"); jo.addProperty("age", 2);
		ja.add(jo);
		
		Page page = new Page();
		
		page.setStart(1); page.setLimit(1);
		JsonArray newJa = DataUtils.ja_getByPage(ja, page);
		Assert.assertArrayEquals("相等", new Integer[]{1}, new Integer[]{newJa.size()});
		
		page.setStart(1); page.setLimit(10);
		newJa = DataUtils.ja_getByPage(ja, page);
		Assert.assertArrayEquals("相等", new Integer[]{1}, new Integer[]{newJa.size()});
		
		page.setStart(0); page.setLimit(10);
		newJa = DataUtils.ja_getByPage(ja, page);
		Assert.assertArrayEquals("相等", new Integer[]{2}, new Integer[]{newJa.size()});
		
		page.setStart(0); page.setLimit(2);
		newJa = DataUtils.ja_getByPage(ja, page);
		Assert.assertArrayEquals("相等", new Integer[]{2}, new Integer[]{newJa.size()});
		
		page.setStart(3); page.setLimit(2);
		newJa = DataUtils.ja_getByPage(ja, page);
		Assert.assertArrayEquals("相等", new Integer[]{0}, new Integer[]{newJa.size()});
		
		page.setStart(1); page.setLimit(0);
		newJa = DataUtils.ja_getByPage(ja, page);
		Assert.assertArrayEquals("相等", new Integer[]{0}, new Integer[]{newJa.size()});
	}
	
	@Test
	public void testJa_sort() {
		JsonArray ja = new JsonArray();
		JsonObject jo = new JsonObject();
		jo.addProperty("name", "abcd"); jo.addProperty("age", 1);
		ja.add(jo);
		jo = new JsonObject();
		jo.addProperty("name", "abc"); jo.addProperty("age", 2);
		ja.add(jo);
		JsonArray newJa = DataUtils.ja_sort(ja, "name", Dir.DESC.name());
		System.out.println(newJa);
	}
	
	@Test
	public void testToMap() {
		JsonObject jo = new JsonObject();
		jo.addProperty("name", "xnat"); jo.addProperty("age", 23);
		JsonObject subJo = new JsonObject();
		subJo.addProperty("account", "xxb"); subJo.addProperty("uid", 007);
		jo.add("info", subJo);
		Map<String, Object> map = DataUtils.toMap(jo);
		Assert.assertArrayEquals("name=xnat", new String[]{map.get("name").toString()}, new String[]{"xnat"});
		Assert.assertArrayEquals("account=xxb", 
				new String[]{((Map<String, Object>)map.get("info")).get("account").toString()}, new String[]{"xxb"});
	}
	
	@Test
	public void testToXmlStr() {
		JsonObject jo = new JsonObject();
		jo.addProperty("name", "xnat"); jo.addProperty("age", 23);
		String s = DataUtils.toXmlStr(jo);
		System.out.println(s);
	}
	
	@Test
	public void testToJsonArray() {
		/**
		 * List<Map<String, Object>>　测试深度解析　=== object 为 Map<String, Object>　或　List<Map<String, Object>>
		 */
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("name", "xxb"); map.put("age", 20);
		list.add(map);
		HashMap<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("like", "linux"); subMap.put("skill", "java");
		map.put("info", subMap);
		
		
		map = new HashMap<String, Object>();
		map.put("name", "xnat"); map.put("age", 23);
		list.add(map);
		List<Map<String, Object>> subList = new ArrayList<Map<String,Object>>();
		subMap = new HashMap<String, Object>();
		subMap.put("bookName", "linux内核"); subMap.put("bookPrice", 60);
		subList.add(subMap);
		subMap = new HashMap<String, Object>();
		subMap.put("bookName", "设计模式"); subMap.put("bookPrice", 30);
		subList.add(subMap);
		map.put("myBook", subList);
		
		JsonArray jo = DataUtils.toJsonArray(list);
		System.out.println(jo);
	}
}
