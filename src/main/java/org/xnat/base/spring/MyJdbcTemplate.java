package org.xnat.base.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

public class MyJdbcTemplate extends JdbcTemplate {
	
	public int update(final String sql, KeyHolder generatedKeyHolder, final Object... args) throws DataAccessException {
		return update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql);
				newArgPreparedStatementSetter(args).setValues(ps);
				return ps;
			}
		} , generatedKeyHolder);
	}
}
