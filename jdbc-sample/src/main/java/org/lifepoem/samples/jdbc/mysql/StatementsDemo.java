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

/**
 * Demos for Statement, PreparedStatement, CallableStatement
 * 
 * @author irfgoy
 *
 */
public class StatementsDemo {

	public static void main(String[] args) {
		callableStatementDemo();
	}

	/*
	 * Statement接口用于向数据库发送SQL语句
	 */
	private static void statementDemo() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);

			stmt = conn.createStatement();
			String sql = "SELECT * FROM users";
			rs = stmt.executeQuery(sql);

			displayRows("users info", rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 回收数据库资源
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
	 * 使用PreparedStatement对象
	 * 相对于Statement对象，PreparedStatement更加高效和灵活，并且，它支持参数的使用。
	 */
	private static void preparedStatementDemo() {
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
		} catch (SQLException e) {
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
	 * 可以使用包含结果参数和不包含结果参数的调用方式 
	 * {?=call<procedure-name>[(<arg1>,<arg2>,...)]}
	 * {call<procedure-name>[(<arg1>,<arg2>,...)]} 
	 * 假如数据库中存在如下存储过程： 
	 * PROCEDURE `add_pro`(a INT, b INT, OUT sum INT) BEGIN SET SUM = a + b; END
	 */
	private static void callableStatementDemo() {
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
			stmt.setInt(1, 10);				//除了使用Index，CallableStatement也支持使用参数名的参数设置方法,如：stmt.setInt("a", 10);
			stmt.setInt(2, 20);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.execute();
			
			int sum = stmt.getInt(3);
			System.out.println("Sum = " + sum);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
