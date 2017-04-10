package org.lifepoem.samples.jdbc.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lifepoem.samples.jdbc.JDBCTranUtils;
import org.lifepoem.samples.jdbc.dbutils.Department;

public abstract class JDBCUtils implements IJDBCUtils {
	protected Connection conn;
	
	public JDBCUtils(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Connection getConnection() {
		return conn;
	}

	@Override
	public void startTransaction() {
		try {
			conn.setAutoCommit(false);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commit() {
		try {
			if(conn != null) {
				conn.commit();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollback() {
		try {
			if(conn != null) {
				conn.rollback();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		QueryRunner runner = new QueryRunner();
		return runner.query(conn, sql, rsh);
	}
}
