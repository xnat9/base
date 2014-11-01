package org.xnat.test.jdk8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.xnat.util.DbUtil;

public class Test {
	public static void main(String[] args) {
		Connection con = DbUtil.getDB().getCon();
		String sql = "select * from lala_statistics.m_role";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getObject(1)+": "+rs.getObject(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
