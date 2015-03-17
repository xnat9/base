package org.xnat.base.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
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
	
	@Override
	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));
		return results == null ? null : (results.size() > 0 ? results.get(0) : null);
	}
}
