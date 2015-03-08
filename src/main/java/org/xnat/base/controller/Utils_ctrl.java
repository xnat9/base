package org.xnat.base.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.xnat.base.dao.util.AutoMap;
import org.xnat.base.util.DataUtils;

import com.google.gson.JsonObject;

/**
 * ctroller 层的工具
 * @author xnat
 * Oct 23, 2014 10:32:37 AM
 */
public final class Utils_ctrl {
	private Utils_ctrl() {}
	/**
	 * replaced by getConditions(HttpServletRequest req, String... keys)
	 * @param req
	 * @return
	 */
	public static List<AutoMap> getConditions(HttpServletRequest req) {
		return getConditions(req, (String[])null);
	}
	/**
	 * 从HttpServletRequest 中获取传过来的参数
	 * @param req
	 * @param keys
	 * @return
	 */
	public static List<AutoMap> getConditions(HttpServletRequest req, String... keys) {
		ArrayList<AutoMap> conditions = new ArrayList<AutoMap>();
		Map<String, String[]> map = req.getParameterMap();
		Iterator<String> it = map.keySet().iterator();
		if (keys == null || keys.length == 0) {
			while(it.hasNext()) {
				String key = it.next();
				conditions.add(new AutoMap(key, "=", req.getParameter(key)));
			}
		} else {
			while(it.hasNext()) {
				String key = it.next();
				boolean flag = false;
				for (int i=0; i<keys.length; i++) if (key.equals(keys[i])) flag = true;
				if (flag) conditions.add(new AutoMap(key, "=", req.getParameter(key)));
			}
		}
		return conditions;
	}
	/**
	 * 为List<AutoMap>参数 重新设置 中间的符号和值 (主要是为参数类型为String的比较符号改为 "like", 值改为: "%value%")
	 * @param condtitions
	 * @param clazz
	 * Oct 23, 2014 10:34:39 AM
	 */
	public static <T> void setDenotation(List<AutoMap> conditions, Class<T> clazz) {
		for (AutoMap map : conditions) {
			try {
				Field field = clazz.getDeclaredField(map.getKey());
				switch(field.getType().getSimpleName()) {
				case "String":
					map.setDenotation("like"); map.setValue("%"+map.getValue()+"%");
					break;
				}
			} catch (NoSuchFieldException | SecurityException e) {
				continue;
			}
		}
	}
	
	/**
	 * 从request中获取的所有参数 只获取一部分与 特定的entity字段相同的参数名 来创造一个entity
	 * (不把所有传过来的参数都注入到entity中, 只选择一部分注入)
	 * @param <T>
	 * @param req
	 * @param fields
	 * @return
	 * Oct 29, 2014 9:41:54 AM
	 */
	public static <T> T populateBean(Class<T> clazz, HttpServletRequest req, List<String> fields) {
		T obj = null;
		try {
			obj = clazz.newInstance();
			Map<String, String[]> map = req.getParameterMap();
			Iterator<String> it = map.keySet().iterator();
			
			if (fields == null || fields.size() == 0) {
				while (it.hasNext()) {
					String key = it.next();
					DataUtils.bean_setValueForField(obj, key, req.getParameter(key));
				}
			} else {
				while (it.hasNext()) {
					String key = it.next();
					for (String field : fields) {
						if (key.equals(field)) {
							DataUtils.bean_setValueForField(obj, key, req.getParameter(key));
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public static <T> T populateBean(Class<T> clazz, HttpServletRequest req, String... fields) {
		List<String> params = new ArrayList<String>(fields.length);
		for (String s : fields) params.add(s);
		return populateBean(clazz, req, params);
	}
	/**
	 * 获取会话, 并获取会话中的登录用户信息
	 * @param resp
	 * @param req
	 * @return
	 * @throws IOException
	 * Oct 28, 2014 9:27:42 AM
	 */
	public static Object getSessionM_user(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		JsonObject result = new JsonObject();
		
		HttpSession session = req.getSession(false);
		if (session == null) {
			result.addProperty("success", false);
			result.addProperty("msg", "会话失效, 请重新登录!");
			resp.getWriter().write(result.toString());
			return null;
		}
		Object person = session.getAttribute("m_user");
		if (person == null) {
			result.addProperty("success", false);
			result.addProperty("msg", "session中的用户信息为空(可能会话失效, 请重新登录!)");
			resp.getWriter().write(result.toString());
			return person;
		}
		return person;
	}
}
