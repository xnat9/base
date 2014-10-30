package org.xnat.test;

import java.lang.reflect.Field;
import java.rmi.Remote;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.PrePersist;
import javax.transaction.TransactionManager;

import org.springframework.aop.framework.ProxyConfig;
import org.springframework.remoting.support.RemotingSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.xnat.dao.util.BaseDaoUtil_v3;
import org.xnat.entity.Person;
import org.xnat.util.DbUtil;

public class Test {
	public static void main(String[] args) {
//		RemotingSupport
//		Remote
//		UnicastRemoteObject
//		PlatformTransactionManager
//		TransactionStatus
//		TransactionManager
//		ProxyConfig
	}
	
	/**
	 * 两表相同字段 resultset 都可以取到
	 * Oct 24, 2014 11:43:23 AM
	 */
	@org.junit.Test
	public void testTwoTableSameFieldResultsetHandler() {
		Connection con = DbUtil.getDB().getCon();
		String sql = "select t1.email, t2.email from user t1, user_account t2 where t1.uid=t2.uid limit 5";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
//				Array arr = rs.getArray("email");
				System.out.println(rs.getString(1)+" : "+rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.getDB().close(rs, ps, con);
		}
	}
}
