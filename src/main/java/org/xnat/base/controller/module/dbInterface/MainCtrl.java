package org.xnat.base.controller.module.dbInterface;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("m")
public class MainCtrl {
	@RequestMapping("doSql")
	public void doSql(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonObject result = new JsonObject();
		resp.getWriter().write(result.toString());
	}
}
