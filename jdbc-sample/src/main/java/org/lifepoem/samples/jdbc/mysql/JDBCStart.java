package org.lifepoem.samples.jdbc.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 描述是用JDBC连接MySQL数据库并读取数据的基本步骤
 * @author irfgoy
 *
 */
public class JDBCStart {

	public static void main(String[] args) throws SQLException {
		readUsers2();
	}

	/**
	 * 使用JDBC连接和操作数据库的一般方法
	 * 但是此方法有待改进的项目有：
	 * 	1。虽然DriverManager.registerDriver可以完成数据库驱动的注册
	 *    但是该方法会导致数据库驱动被注册两次，因为在Driver类的静态代码块中完成了对数据库驱动的注册
	 *    为了避免重复注册，只需在程序中加载驱动类即可：
	 *    Class.forName("com.mysql.cj.jdbc.Driver");
	 *  2。为了保证资源的释放，需要将关闭操作放在finally代码块中
	 *    详见readUsers2()方法
	 * @throws SQLException
	 */
	private static void readUsers() throws SQLException {
		//1.注册数据库驱动
		//从mysql-connector-java 6开始，使用com.mysql.cj.jdbc.Driver代替com.mysql.jdbc.Driver
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		
		//2.通过DriverManager获取数据库连接
		String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
		String username = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, username, password);
		//3.通过Connection对象创建Statement对象
		Statement stmt = conn.createStatement();
		//4.使用Statement执行SQL语句
		String sql = "SELECT * FROM users";
		ResultSet rs = stmt.executeQuery(sql);
		//5.操作ResultSet结果集
		System.out.println("id|name|password|email|birthday");
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String psw = rs.getString("password");
			String email = rs.getString("email");
			Date birthday = rs.getDate("birthday");
			System.out.println(id + "|" + name + "|" + psw + "|" + email + "|" + birthday);
		}
		//回收数据库资源
		rs.close();
		stmt.close();
		conn.close();
	}
	
	private static void readUsers2() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//1.注册数据库驱动
			//从mysql-connector-java 6开始，使用com.mysql.cj.jdbc.Driver代替com.mysql.jdbc.Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//2.通过DriverManager获取数据库连接
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);
			//3.通过Connection对象创建Statement对象
			stmt = conn.createStatement();
			//4.使用Statement执行SQL语句
			String sql = "SELECT * FROM users";
			rs = stmt.executeQuery(sql);
			//5.操作ResultSet结果集
			System.out.println("id|name|password|email|birthday");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String psw = rs.getString("password");
				String email = rs.getString("email");
				Date birthday = rs.getDate("birthday");
				System.out.println(id + "|" + name + "|" + psw + "|" + email + "|" + birthday);
			}
		}
		catch(ClassNotFoundException e) {
			
		}
		finally {
			//回收数据库资源
			if(rs != null) {
				try {
					rs.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}

}
