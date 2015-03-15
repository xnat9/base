package org.xnat.base.spring;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 自定义异常处理
 * @author xnat
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
		String viewName = determineViewName(ex, req);
		System.out.println(viewName+"here======================================");
		
		if (ex instanceof MaxUploadSizeExceededException) {
			System.out.println("here-------------------------------");
			try {
				resp.getWriter().write("文件太大!");
				return null;
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		//返回json字符串
		if (viewName == null) {
			return null;
		}
		return super.doResolveException(req, resp, handler, ex);
	}
}
