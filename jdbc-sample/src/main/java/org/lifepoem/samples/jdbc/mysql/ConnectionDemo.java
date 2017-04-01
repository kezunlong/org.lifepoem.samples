package org.lifepoem.samples.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
	public static void main(String[] args) {
		getConnection();
	}

	/**
	 * 使用JDBC连接和操作数据库的一般方法 但是此方法有待改进的项目有：
	 * 1。虽然DriverManager.registerDriver可以完成数据库驱动的注册
	 * 但是该方法会导致数据库驱动被注册两次，因为在Driver类的静态代码块中完成了对数据库驱动的注册 为了避免重复注册，只需在程序中加载驱动类即可：
	 * Class.forName("com.mysql.cj.jdbc.Driver");
	 * 2。为了保证资源的释放，需要将关闭操作放在finally代码块中 
	 */
	private static void getConnection() {
		Connection conn = null;
		try {
			// 1.注册数据库驱动
			// 从mysql-connector-java 6开始，使用com.mysql.cj.jdbc.Driver代替com.mysql.jdbc.Driver
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2.通过DriverManager获取数据库连接
			String url = "jdbc:mysql://localhost:3306/lms?useSSL=false";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, username, password);

			// Do something with the connection
			System.out.println(conn.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
