package org.lifepoem.samples.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
	/*
	 * Get MySQL Connection
	 */
	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		// 1. 注册数据库驱动
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 2.通过DriverManager获取数据库连接
		String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
		String username = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, username, password);
		
		return conn;
	}
	
	/*
	 * Get MSSQL Connection
	 */
	public static Connection getMSSQLConnection() throws SQLException, ClassNotFoundException {
		//1.注册数据库驱动
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		//2.通过DriverManager获取数据库连接
		String url = "jdbc:sqlserver://localhost:1433;databaseName=library;username=sa;password=1qaz@WSX;";
		Connection conn = DriverManager.getConnection(url);
		
		return conn;
	}
	
	public static void release(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		release(stmt, conn);
	}
}
