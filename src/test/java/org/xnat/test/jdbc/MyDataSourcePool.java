package org.xnat.test.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyDataSourcePool {
	private static LinkedList<Connection> connectionList = new LinkedList<>();

	//得到connection
	public Connection getConnection() {
		return connectionList.removeFirst();
	}
	//释放connection
	public boolean free(Connection conn) {
		try {
			if (conn == null || conn.isClosed()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return connectionList.add(conn);
	}
	//关闭connection
	public boolean close(Connection conn) {
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
