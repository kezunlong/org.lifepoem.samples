package org.lifepoem.samples.jdbc.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.lifepoem.samples.jdbc.JDBCUtils;

/**
 * [此示例同时适用于MySQL, MSSQL等]
 * @author Vincent Ke
 *
 */
public class TransactionDemo {

	public static void main(String[] args) {
		transferAccountDemo();
	}

	/**
	 * 此方法模拟两个账号之间的转账业务，为保证业务安全，需要使用数据库事务处理
	 */
	private static void transferAccountDemo() {
		String outAccount = "aaa";
		String inAccount = "bbb";
		double amount = 200;
		
		Connection conn = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		
		try {
			conn = JDBCUtils.getMySQLConnection();
			
			//1. 关闭事务的自动提交
			conn.setAutoCommit(false);
			
			//账号转出200
			String sql = "UPDATE account SET money = money - ? WHERE name = ? AND money >= 200";
			stmt1 = conn.prepareStatement(sql);
			stmt1.setDouble(1, amount);
			stmt1.setString(2, outAccount);
			stmt1.executeUpdate();
			
			//账号转入200
			String sql2 = "UPDATE account SET money = money + ? WHERE name = ?";
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setDouble(1, amount);
			stmt2.setString(2, inAccount);
			stmt2.executeUpdate();
			
			//2. 提交事务
			conn.commit();
		}
		catch(Exception e) {
			try {
				//3.出错时回滚事务
				conn.rollback();
				System.out.println("转账失败");
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			if(stmt1 != null) { 
				try {
					stmt1.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(stmt2 != null) { 
				try {
					stmt2.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(conn != null) { 
				try {
					conn.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
