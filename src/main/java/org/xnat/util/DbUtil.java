package org.xnat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbUtil {
	private static DbUtil me = null;
	private DbUtil() {}
	
	public static DbUtil getDB() {
		if (me == null) {
			synchronized (DbUtil.class) {
				if (me == null) me = new DbUtil();
			}
		}
		return me;
	}
	
	public Connection getCon() {
		Connection con = null;
		try {
//			com.mysql.jdbc.Driver.class.newInstance();
			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://112.124.50.179:9110/fom", "bcdev", "@ppvii2014");
//			con = DriverManager.getConnection("jdbc:mysql://112.124.50.179:9110/lala_authority", "bcdev", "@ppvii2014");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lala_statistics", "root", "");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
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
