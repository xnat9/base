package org.xnat.base.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xnat.base.dao.DynamicDataSource;

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
	
	@RequestMapping("m4")
	public void m4(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		System.out.println(params.get("a"));
		System.out.println(params.get("b"));
	}
	
	@RequestMapping(value="m3")
	public void m3(@RequestParam Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		System.out.println(params.get("a"));
		System.out.println(params.get("b"));
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
