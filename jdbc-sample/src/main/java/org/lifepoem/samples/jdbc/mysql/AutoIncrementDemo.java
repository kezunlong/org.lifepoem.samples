package org.lifepoem.samples.jdbc.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * 获取自动增长的字段值有如下3种方式： 
 * 1。getGeneratedKeys 
 * 2。SELECT LAST_INSERT_ID()
 * 3.Updatable ResultSet
 * 
 * @author irfgoy
 *
 */
public class AutoIncrementDemo {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		autoIncKeyFromGetGeneratedKeys();
		autoIncKeyFromLastInsertId();
		autoIncKeyFromRS();
	}

	private static void autoIncKeyFromGetGeneratedKeys() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();

			String sql = "INSERT INTO users(name) VALUES('newuser-" + new Random().nextInt() + "')";

			// Insert one row that will generate an AUTO INCREMENT key in the
			// 'id' field
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

			//
			// Example of using Statement.getGeneratedKeys()
			// to retrieve the value of an auto-increment value
			//

			int autoIncKeyFromApi = -1;

			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				autoIncKeyFromApi = rs.getInt(1);
			} else {

				// throw an exception from here
			}

			System.out.println("Key returned from getGeneratedKeys():" + autoIncKeyFromApi);
		} finally {
			JDBCUtils.release(rs, stmt, conn);
		}
	}

	private static void autoIncKeyFromLastInsertId() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();

			String sql = "INSERT INTO users(name) VALUES('newuser-" + new Random().nextInt() + "')";

			// Insert one row that will generate an AUTO INCREMENT key in the
			// 'id' field
			stmt.executeUpdate(sql);

			int autoIncKeyFromFunc = -1;
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
			} else {
				// throw an exception from here
			}

			System.out.println("Key returned from " + "'SELECT LAST_INSERT_ID()': " + autoIncKeyFromFunc);
		} finally {
			JDBCUtils.release(rs, stmt, conn);
		}
	}

	private static void autoIncKeyFromRS() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery("SELECT * FROM users");

			rs.moveToInsertRow();

			rs.updateString("name", "newuser-" + new Random().nextInt());
			rs.insertRow();

			// the driver adds rows at the end
			rs.last();

			// We should now be on the row we just inserted
			int autoIncKeyFromRS = rs.getInt("id");

			System.out.println("Key returned for inserted row: " + autoIncKeyFromRS);

		} finally {
			JDBCUtils.release(rs, stmt, conn);
		}
	}
}
