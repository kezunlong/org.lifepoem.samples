package org.lifepoem.samples.jdbc.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.lifepoem.samples.jdbc.JDBCUtils;

/**
 * [此示例同时适用于MySQL, MSSQL等]
 * @author Vincent Ke
 *
 */
public class ResultSetsDemo {

	public static void main(String[] args) {
		resultSetDemo();
		modifyResultSet();
	}
	
	/*
	 * ResultSet主要用于存储结果集，并且只能通过next()方法向前遍历数据
	 * 如果想要任意定位结果集中的数据，需要在创建Statement/PreparedStatement时指定如下结果集常量特性
	 * ResultSet.TYPE_SCROLL_INSENSITIVE : 可滚动 
	 * ResultSet.CONCUR_READ_ONLY : 只读方式打开
	 */
	private static void resultSetDemo() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getMySQLConnection();

			String sql = "SELECT * FROM users";
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();

			// 定位到第二条记录(数据行从1开始编号)
			rs.absolute(2);
			System.out.println(rs.getString("name"));

			// 定位到第一条记录
			rs.beforeFirst();
			rs.next();
			System.out.println(rs.getString("name"));

			// 定位到第一条记录
			rs.afterLast();
			rs.previous();
			System.out.println(rs.getString("name"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(rs, stmt, conn);
		}
	}

	/*
	 * 
	 */
	private static void modifyResultSet() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = JDBCUtils.getMySQLConnection();

			// Create and execute an SQL statement, retrieving an updateable result set.
			String SQL = "SELECT * FROM users;";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(SQL);

			// Insert a row of data.
			rs.moveToInsertRow();
			rs.updateString("name", "Accounting");
			rs.updateString("password", "123456");
			rs.updateString("birthday", "2006-01-10");
			rs.insertRow();

			// Retrieve the inserted row of data and display it.
			SQL = "SELECT * FROM users WHERE name = 'Accounting';";
			rs = stmt.executeQuery(SQL);
			displayRows("ADDED ROW", rs);

			// Update the row of data.
			rs.first();
			rs.updateString("birthday", "2008-08-08");
			rs.updateRow();

			// Retrieve the updated row of data and display it.
			rs = stmt.executeQuery(SQL);
			displayRows("UPDATED ROW", rs);

			// Delete the row of data.
			rs.first();
			rs.deleteRow();
			System.out.println("ROW DELETED");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(rs, stmt, conn);
		}
	}

	private static void displayRows(String title, ResultSet rs) {
		try {
			System.out.println(title);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String psw = rs.getString("password");
				String email = rs.getString("email");
				Date birthday = rs.getDate("birthday");
				System.out.println(id + "|" + name + "|" + psw + "|" + email + "|" + birthday);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
