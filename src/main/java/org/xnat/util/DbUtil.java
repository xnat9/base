package org.xnat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class DbUtil {
	private static DbUtil DB = null;
	private DbUtil() {}
	
	public static DbUtil getDB() {
		if (DB == null) {
			synchronized (DbUtil.class) {
				if (DB == null) DB = new DbUtil();
			}
		}
		return DB;
	}
	
	public Connection getCon() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://112.124.50.179:9110/fom", "bcdev", "@ppvii2014");
//			con = DriverManager.getConnection("jdbc:mysql://112.124.50.179:9110/lala_authority", "bcdev", "@ppvii2014");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lala_statistics", "root", "");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/laladb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public void close(Statement st) {
		try {
			if (st != null) st.close();
		} catch (SQLException e) {
		}
	}
	public void close(Connection con) {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
		}
	}
	public void close(ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
		}
	}
	public void close(Statement st, Connection conn) {
		try {
			if (st != null) st.close();
		} catch (SQLException e) {
		}
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
		}
		
	}
	public void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {}
		try {
			if (st != null) st.close();
		} catch (SQLException e) {}
		
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {}
	}
}
