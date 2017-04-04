package org.lifepoem.samples.jdbc.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * 数据库连接池：DBCP数据源
 * 
 * @author Vincent Ke
 *
 */
public class DBCPDataSource {
	public static DataSource ds = null;
	static {
		ds = createDataSourceFromConfiguration();
	}

	/*
	 * 通过BasicDataSource 直接创建数据源对象，手动设置属性值。
	 */
	private static BasicDataSource createDataSourceManually() {
		// 获取DBCP数据源实现类对象
		BasicDataSource bds = new BasicDataSource();
		// 设置数据库连接信息
		bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost:3306/lms?useSSL=false");
		bds.setUsername("root");
		bds.setPassword("123456");
		// 设置连接池参数
		bds.setInitialSize(5); 
		bds.setMaxTotal(5);		//在DBCP1中，使用setMaxActive
		return bds;
	}

	/*
	 * 使用工厂类BasicDataSourceFactory的createDataSource()方法，
	 * 通过读取配置文件的信息生成数据源对象
	 * 需要注意的是：对于MAVEN项目，配置文件要放在src\main\resources目录下才会被拷到target目录中
	 */
	private static BasicDataSource createDataSourceFromConfiguration() {
		BasicDataSource ds = null;
		Properties properties = new Properties();
		try {
			String configFile = "dbcpconfig.properties";
			InputStream in = new DBCPDataSource().getClass().getClassLoader().getResourceAsStream(configFile);
			properties.load(in);
			ds = BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public static void main(String[] args) throws SQLException {
		Connection conn = ds.getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		System.out.println(metaData.getURL() + ", UserName=" + metaData.getUserName() + "," + metaData.getDriverName());
	}

}
