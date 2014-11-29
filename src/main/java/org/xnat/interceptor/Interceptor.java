package org.xnat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.google.gson.JsonObject;

public class Interceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding("utf8");
		request.setCharacterEncoding("utf8");
		String path = request.getServletPath();
		if ("/admin/login.do".equals(path)) return true;
//		Object m_user = WebUtils.getSessionAttribute(request, "m_user");
//		if (m_user == null) {
//			JsonObject jo = new JsonObject();
//			jo.addProperty("success", false);
//			jo.addProperty("msg", "会话失效, 请重新登录!");
//			response.addHeader("sessionstatus", "0");     
//			return false;
//		}
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
