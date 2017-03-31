package org.lifepoem.samples.jdbc.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class JDBCObjectsDemo {

	public static void main(String[] args) throws SQLException {
		modifyResultSet();
	}

	/*
	 * 使用PreparedStatement对象
	 * 相对于Statement对象，PreparedStatement更加高效和灵活，并且，它支持参数的使用。
	 */
	private static void preparedStatementDemo() throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);

			String sql = "INSERT INTO users(name, password, email, birthday) VALUES(?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "newuser");
			stmt.setString(2, "123456");
			stmt.setString(3, "newuser@sample.com");
			stmt.setString(4, "1988-08-10");
			stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
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
	}

	/*
	 * 使用CallableStatement对象调用存储过程 JDBC API提供了一个存储过程转义语法，它允许对所有RDBMS使用标准方式调用存储过程
	 * 可以使用包含结果参数和不包含结果参数的调用方式 {?=call<procedure-name>[(<arg1>,<arg2>,...)]}
	 * {call<procedure-name>[(<arg1>,<arg2>,...)]} 假如数据库中存在如下存储过程： PROCEDURE
	 * `add_pro`(a INT, b INT, OUT sum INT) BEGIN SET SUM = a + b; END
	 */
	private static void callableStatementDemo() throws SQLException {
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);

			String sql = "{call add_pro(?,?,?)}";
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, 10);
			stmt.setInt(2, 20);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.execute();
			int sum = stmt.getInt(3);
			System.out.println("Sum = " + sum);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
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
	}

	/*
	 * ResultSet主要用于存储结果集，并且只能通过next()方法向前遍历数据
	 * 如果想要任意定位结果集中的数据，需要在创建Statement/PreparedStatement时指定如下结果集常量特性
	 * ResultSet.TYPE_SCROLL_INSENSITIVE : 可滚动 ResultSet.CONCUR_READ_ONLY :
	 * 只读方式打开
	 */
	private static void resultSetDemo() throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);

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
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
	}

	/*
	 * 
	 */
	private static void modifyResultSet() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);

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
			displayRow("ADDED ROW", rs);

			// Update the row of data.
			rs.first();
			rs.updateString("birthday", "2008-08-08");
			rs.updateRow();

			// Retrieve the updated row of data and display it.
			rs = stmt.executeQuery(SQL);
			displayRow("UPDATED ROW", rs);

			// Delete the row of data.
			rs.first();
			rs.deleteRow();
			System.out.println("ROW DELETED");
		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
	}

	private static void displayRow(String title, ResultSet rs) {
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
