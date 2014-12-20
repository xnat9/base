package org.xnat.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xnat.dao.DynamicDataSource;

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
//		resp.getWriter().write(schema);
	}
	@RequestMapping("m3")
	public void m3(HttpServletResponse resp) throws SQLException, IOException {
		Connection con = dynamicDataSource.getConnection();
		System.out.println(con == null);
		System.out.println(con.getCatalog()+" ================================");
//		resp.getWriter().write(schema);
	}
}
