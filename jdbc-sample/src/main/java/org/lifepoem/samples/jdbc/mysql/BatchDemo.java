package org.lifepoem.samples.jdbc.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.lifepoem.samples.jdbc.JDBCUtils;


/**
 * JDBC提供的批处理可以同时执行多条SQL语句。
 * Statement和PreparedStatement都实现了批处理。
 * 需要注意的是，批处理只是把多个语句提交数据库服务器依次执行，并没有实现事务控制
 * 如果某个步骤失败，则之前的语句还是会成功执行
 * [此示例同时适用于MySQL, MSSQL等]
 * @author Vincent Ke
 *
 */
public class BatchDemo {

	public static void main(String[] args) {
		//statementBatchDemo();
		preparedStatementBatchDemo();
	}

	private static void statementBatchDemo() {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = JDBCUtils.getMySQLConnection();
			stmt = conn.createStatement();
			
			String sql1 = "DROP TABLE IF EXISTS school";
			String sql2 = "CREATE TABLE school(id int, name varchar(20))";
			String sql3 = "INSERT INTO school VALUES(2, '测试')";
			String sql4 = "UPDATE school SET id = 1";
			
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.addBatch(sql4);
			stmt.executeBatch();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(stmt, conn);
		}
	}

	/*
	 * 当向同一个表中批量更新数据时，使用PreparedStatement可以避免重复代码
	 */
	private static void preparedStatementBatchDemo() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = JDBCUtils.getMySQLConnection();
			String sql = "INSERT INTO users(name, password, email, birthday) VALUES(?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			for(int i = 0; i < 5; i++) {
				stmt.setString(1, "name" + i);
				stmt.setString(2, "password" + i);
				stmt.setString(3,  "email" + i + "@sample.com");
				stmt.setDate(4, Date.valueOf("2000-01-20"));
				stmt.addBatch();
			}
			stmt.executeBatch();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(stmt, conn);
		}
	}

}
