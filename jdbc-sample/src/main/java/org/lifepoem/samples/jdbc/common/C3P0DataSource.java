package org.lifepoem.samples.jdbc.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSource {

	public static DataSource ds = null;
	static {
		ds = createDataSourceFromConfiguration();
	}
	
	private static ComboPooledDataSource createDataSourceManually() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			// 设置数据库连接信息
			cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost:3306/lms?useSSL=false");
			cpds.setUser("root");
			cpds.setPassword("123456");
			// 设置连接池参数
			cpds.setInitialPoolSize(5);
			cpds.setMaxPoolSize(15);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return cpds;
	}
	
	private static ComboPooledDataSource createDataSourceFromConfiguration() {
		ComboPooledDataSource cpds = new ComboPooledDataSource("library");
		return cpds;
	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn = ds.getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		System.out.println(metaData.getURL() + ", UserName=" + metaData.getUserName() + "," + metaData.getDriverName());

	}
	
	

}
