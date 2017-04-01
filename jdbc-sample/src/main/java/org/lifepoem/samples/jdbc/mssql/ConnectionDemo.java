package org.lifepoem.samples.jdbc.mssql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class ConnectionDemo {

	public static void main(String[] args) throws SQLException {
		getConnectionByURL();
		getConnectionByDataSource();
	}
	
	private static void getConnectionByURL() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//1.注册数据库驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			//2.通过DriverManager获取数据库连接
			String url = "jdbc:sqlserver://localhost:1433;databaseName=library;username=sa;password=1qaz@WSX;";
			conn = DriverManager.getConnection(url);
			
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
	

	private static void getConnectionByDataSource() {
		Connection conn = null;
		
		try {
	        // Establish the connection by DataSource.  
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        
	        SQLServerDataSource ds = new SQLServerDataSource(); 
	        ds.setUser("sa"); 
	        ds.setPassword("1qaz@WSX"); 
	        ds.setServerName("localhost"); 
	        ds.setPortNumber(1433);  
	        ds.setDatabaseName("library"); 
	        conn = ds.getConnection();
	        
			// Do something with the connection
			System.out.println(conn.isClosed());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
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
