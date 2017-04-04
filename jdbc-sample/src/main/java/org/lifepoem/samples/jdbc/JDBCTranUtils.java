package org.lifepoem.samples.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 支持事务的JDBCUtils
 * 可以和DBUtils框架组合使用，简化JDBC编程
 */
public class JDBCTranUtils {
	
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static DataSource ds = new ComboPooledDataSource();
	
	public static DataSource getDataSource() {
		return ds;
	}
	
	public static Connection getConnection() throws SQLException {
		Connection conn = threadLocal.get();
		if(conn == null) {
			conn = ds.getConnection();
			threadLocal.set(conn);
		}
		return conn;
	}

	public static void startTransaction() {
		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit() {
		try {
			Connection conn = threadLocal.get();
			if(conn != null) {
				conn.commit();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback() {
		try {
			Connection conn = threadLocal.get();
			if(conn != null) {
				conn.rollback();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close() {
		Connection conn = threadLocal.get();
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				threadLocal.remove();
				conn = null;
			}
		}
	}
	
}
