package org.xnat.base.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.json.XML;
import org.xnat.base.dao.util.Dir;
import org.xnat.base.dao.util.Page;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * 常见结构 map bean xml jsonObject(alias: jo) autoMap(有符号的map)
 * 	jsonArray(alias: ja) list list<Object>(alias: listbean) list<Map<String, Object>>(alias: listmap)
 * 	(list<AutoMap>)listAutomap
 * 	可以用类大打造任意数据结构
 * 此类提供 
 * 		数据类型转换: 方法名都以 "to"开头
 * 		常见结构的操作 方法名: 结构名称+"_"+动作
 * @author xnat
 * Oct 16, 2014 4:50:28 PM
 */
public final class DataUtils {
	public final static Gson gson = new Gson(); 
	public final static JsonParser jsonParser = new JsonParser(); 
//	static {}
	private DataUtils() {}
	/**
	 * 转换专区
	 */
	/**
	 * =====================================================================================
	 * toJsonObject 用的是Gson
	 */
	/**
	 * map to jsonObject
	 * @param map
	 * @return
	 * Oct 16, 2014 4:56:45 PM
	 */
	public static JsonObject toJsonObject(Map<String, Object> map) {
		JsonObject jo = new JsonObject();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			jo.addProperty(key, map.get(key).toString());
		}
		return jo;
	}
	/**
	 * xml字符串 to jsonObject
	 * @param xmlStr
	 * @return
	 */
	public static JsonObject toJsonObjectFromXmlStr(String xmlStr) {
		return jsonParser.parse(XML.toJSONObject(xmlStr).toString()).getAsJsonObject();
	}
	/**
	 * jsonStr to JsonObject 
	 * @param str
	 * @return
	 * Oct 6, 2014 10:56:44 AM
	 */
	public static JsonObject toJsonObject(String jsonStr) {
		try {
			 return jsonParser.parse(jsonStr).getAsJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonObject();
	}
	/**
	 * bean to jsonObject
	 * @param javaBean
	 * @return
	 */
	public static JsonObject toJsonObject(Object javaBean) {
		JsonObject jo = new JsonObject();
		try {
			for (Field field: javaBean.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(javaBean) != null) jo.addProperty(field.getName(), field.get(javaBean).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	/**
	 * =========================================================
	 * toMap
	 */
	/**
	 * bean to map by inject
	 * @param bean
	 * @param map
	 * Oct 6, 2014 11:16:50 AM
	 */
	public static void toMapByInject(Object bean, Map<String, Object> map) {
		try {
			for (Field f : bean.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.get(bean) != null) map.put(f.getName(), f.get(bean));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * bean to map
	 * @param bean
	 * @return
	 * Oct 16, 2014 5:13:58 PM
	 */
	public static Map<String, Object> toMap(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (Field f : bean.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.get(bean) != null) map.put(f.getName(), f.get(bean));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * jsonObject to map 当jsonObject里有嵌套jsonObject时 可以深度转换(已测试)
	 * @param jo
	 * @return
	 * Oct 16, 2014 5:44:27 PM
	 */
	public static Map<String, Object> toMap(JsonObject jo) {
		Map<String, Object> map = gson.fromJson(jo, new TypeToken<Map<String, Object>>(){}.getType());
		return map;
	}
	/**
	 * xmlStr to map 未实现
	 * @param xmlStr
	 * @return
	 * Oct 16, 2014 5:49:44 PM
	 */
	public static Map<String, Object> toMap(String xmlStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	
	/**
	 * =========================================================
	 * toBean
	 */
	
	/**
	 * jsonObject to bean
	 * @param <T>
	 * @param clazz
	 * @param jo
	 * @return
	 * Oct 16, 2014 10:33:19 PM
	 */
	public static <T> Object toBean(Class<T> clazz, JsonObject jo) {
		return gson.fromJson(jo, clazz);
	}
	public static <T> Object toBean(Class<T> clazz, Map<String, Object> map) {
		T instance = null;
		try {
			instance = clazz.newInstance();
			BeanUtils.populate(instance, map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	/**
	 * map to bean by inject
	 * @param bean
	 * @param map
	 * Oct 16, 2014 10:39:23 PM
	 */
	public static void toBeanByInject(Object bean, Map<String, Object> map) {
		try {
			BeanUtils.populate(bean, map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * =========================================================
	 * toXmlStr
	 */
	
	/**
	 * jsonObject to xmlStr
	 * @param jo
	 * @return
	 * Oct 16, 2014 5:53:24 PM
	 */
	public static String toXmlStr(JsonObject jo) {
		if (jo == null) return "";
		return XML.toString(new JSONObject(jo.toString()));
	}
	
	/**
	 * toJsonArray
	 */
	
	/**
	 * List<Map<String, Object>> 转换成JsonArray
	 * @param list
	 * @return
	 * Oct 6, 2014 11:14:12 AM
	 */
	public static JsonArray toJsonArray(List<Map<String, Object>> list) {
		return jsonParser.parse(gson.toJson(list)).getAsJsonArray();
	}
	/**
	 * string to jsonArray
	 * @param str
	 * @return
	 * Oct 18, 2014 4:43:22 PM
	 */
	public static JsonArray toJsonArray(String str) {
		if (str == null || str.isEmpty()) return new JsonArray();
		JsonArray ja = null;
		try {
			ja = jsonParser.parse(str).getAsJsonArray();
		} catch (Exception e) {
			ja = new JsonArray();
		}
		return ja;
	}
	
	
	/**
	 * =====================================================================================
	 * toListBean
	 */
	
	/**
	 * 把 List<Map<String, Object>> 转换成 List<Object>
	 * @param maps
	 * @param clazz
	 * @return
	 */
	public <T> List<Object> toListBean(List<Map<String, Object>> maps, Class<T> clazz) {
		List<Object> list = new ArrayList<Object>();
		try {
			for (Map<String, Object> map : maps) {
				T obj = clazz.newInstance();
				BeanUtils.populate(obj, map);
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	
	/**
	 * ======================================================================================
	 * 操作专区
	 */
	
	/**
	 * 从 List<Map<String, Object>> 中获取第一个map
	 * @param list
	 * @return
	 */
	public static Map<String, Object> listmap_getFirstMap(List<Map<String, Object>> list) {
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 从一个对象中获取指定字段的值 (已测试)
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object bean_getFieldValue(Object obj, String fieldName) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			if (fieldName.equals(field.getName())) return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 为bean 的某个字段设值 (已测试)
	 * @param obj
	 * @param fieldName
	 * @param value
	 * Oct 29, 2014 10:04:12 AM
	 */
	public static void bean_setValueForField(Object obj, String fieldName, String value) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			String type = field.getType().getSimpleName();
			Object v = value;
			switch (type) {
			case "Integer":
				v = Integer.parseInt(value); break;
			case "Long":
				v = Long.parseLong(value); break;
			}
 			if (fieldName.equals(field.getName())) field.set(obj, v);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JsonArray 去重
	 * @param ja
	 * @param attrName 属性名
	 * @return
	 */
	public static JsonArray ja_uniqueByAttr(JsonArray ja, String attrName) {
		attrName = attrName.trim();
		if (attrName == null || attrName.isEmpty()) return ja;
		JsonArray result = new JsonArray();
		Iterator<JsonElement> it = ja.iterator();
		Set<String> set = new HashSet<String>();
		while (it.hasNext()) {
			JsonObject jo = it.next().getAsJsonObject();
			if(jo.get(attrName) != null && !jo.get(attrName).toString().trim().isEmpty() && !set.contains(jo.get(attrName).toString())) {
				set.add(jo.get(attrName).toString());
				result.add(jo);
			}
		}
		return result;
	}
	/**
	 * jsonArray 删除元素
	 * @param ja
	 * @param attrName 属性名
	 * @param attrValue 属性值 如果为null就代表: 删除所有没有attrName这个属性的元素(jsonObject)
	 * @return
	 * Oct 6, 2014 10:01:02 AM
	 */
	public static JsonArray ja_removeJoFromJaByAttr(JsonArray ja, String attrName, Object attrValue) {
		attrName = attrName.trim();
		if (attrName == null || attrName.isEmpty()) return ja;
		JsonArray result = new JsonArray();
		Iterator<JsonElement> it = ja.iterator();
		while (it.hasNext()) {
			JsonObject jo = it.next().getAsJsonObject();
			if (attrValue == null) {
				if (jo.get(attrName) != null) result.add(jo);
			}
			else if(attrValue.equals(jo.get(attrName).getAsString())) {
				result.add(jo);
			}
		}
		return result;
	}
	/**
	 * 根据一个属性对jsonArray排序 已测试
	 * @param ja
	 * @param fieldName 值只能是
	 * @param order 默认 DESC
	 * @return
	 */
	public static JsonArray ja_sort(JsonArray ja, final String fieldName, final String order) {
		if (ja == null || fieldName == null || "".equals(fieldName)) return ja;
		Iterator<JsonElement> it = ja.iterator();
		List<JsonObject> tmpList = new ArrayList<JsonObject>();
		while (it.hasNext()) tmpList.add(it.next().getAsJsonObject());
		Collections.sort(tmpList, new Comparator<JsonObject>() {
			@Override
			public int compare(JsonObject o1, JsonObject o2) {
				String i = o1.get(fieldName).getAsString();
				String j = o2.get(fieldName).getAsString();
				if (null == order || Dir.DESC.name().equalsIgnoreCase(order)) return j.compareTo(i);
				else return i.compareTo(j);
			}
		});
		return jsonParser.parse(gson.toJson(tmpList)).getAsJsonArray();
	}
	/**
	 * 已测试
	 * @param ja
	 * @param page
	 * @return
	 * Oct 18, 2014 5:43:34 PM
	 */
	public static JsonArray ja_getByPage(JsonArray ja, Page page) {
//		Assert.notNull(page, "page 为空"); Assert.notNull(ja, "ja 为空");
		if (ja == null) return new JsonArray();
		if (page == null) return ja;
		int jaSize = ja.size();
		if (page.getStart() > jaSize) return new JsonArray(); 
		
		List<JsonObject> tmpList = new ArrayList<JsonObject>();
		Iterator<JsonElement> it = ja.iterator();
		while (it.hasNext()) tmpList.add(it.next().getAsJsonObject());
		
		List<JsonObject> resultList = null;
		if (page.getLimit() > jaSize) resultList = tmpList.subList(page.getStart(), jaSize);
		else resultList = tmpList.subList(page.getStart(), page.getStart()+page.getLimit());
		return jsonParser.parse(gson.toJson(resultList)).getAsJsonArray();
	}
	/**
	 * 反转JsonArray
	 * @param ja
	 * @return
	 */
	public static JsonArray ja_reverse(JsonArray ja) {
		Iterator<JsonElement> it = ja.iterator();
		List<JsonObject> tmpList = new ArrayList<JsonObject>();
		while (it.hasNext()) tmpList.add(it.next().getAsJsonObject());
		Collections.reverse(tmpList);
		return jsonParser.parse(gson.toJson(tmpList)).getAsJsonArray();
	}
}
