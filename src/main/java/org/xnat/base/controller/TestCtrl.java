package org.xnat.base.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.xnat.base.dao.DynamicDataSource;
import org.xnat.base.util.DataUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

/**
 * @author xnat
 */
//@Path("test")
@Controller
@RequestMapping("test")
public class TestCtrl {
	
	@Autowired
	private DynamicDataSource dynamicDataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="m4")
	public void m4(HttpServletRequest req) {
		try {
			JsonObject jo = DataUtils.jsonParser.parse(new JsonReader(new InputStreamReader(req.getInputStream()))).getAsJsonObject();
			System.out.println(jo);
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="m3")
	public void m3(@RequestParam Map<String, Object> params, @RequestParam("date") Date date, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		System.out.println(params.get("a"));
		System.out.println(params.get("b"));
		System.out.println(params.get("date"));
		System.out.println(params.get("date") instanceof Date);
		System.out.println(date);
	}
	
	@RequestMapping("m2")
	public void m2(HttpServletResponse resp) throws SQLException, IOException {
		Map<String, Object> map = jdbcTemplate.queryForMap("select * from laladb.user_account where uid=1");
		System.out.println(map);
	}
	@RequestMapping("m1")
	public void m1(HttpServletResponse resp) throws SQLException, IOException {
		Connection con = dynamicDataSource.getConnection();
		System.out.println(con == null);
		System.out.println(con.getCatalog()+" ================================");
		resp.getWriter().write(con.getSchema());
	}
	
}
