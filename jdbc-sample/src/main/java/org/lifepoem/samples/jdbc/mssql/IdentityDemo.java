package org.lifepoem.samples.jdbc.mssql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.lifepoem.samples.jdbc.JDBCUtils;

/**
 * 
 * MSSQL中获取自动增长的字段值有如下3种方式： 
 * 1。getGeneratedKeys 
 * 2。SELECT SCOPE_IDENTITY()
 * 3.Updatable ResultSet
 * 
 * @author Vincent Ke
 *
 */
public class IdentityDemo {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		identityFromGetGeneratedKeys();
		identityFromScopeIdentity();
		identityFromRS();
	}

	private static void identityFromGetGeneratedKeys() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = JDBCUtils.getMSSQLConnection();
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

	private static void identityFromScopeIdentity() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = JDBCUtils.getMSSQLConnection();
			stmt = conn.createStatement();

			String sql = "INSERT INTO users(name) VALUES('newuser-" + new Random().nextInt() + "')";

			// Insert one row that will generate an AUTO INCREMENT key in the
			// 'id' field
			stmt.executeUpdate(sql);

			int autoIncKeyFromFunc = -1;
			rs = stmt.executeQuery("SELECT SCOPE_IDENTITY()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
			} else {
				// throw an exception from here
			}

			System.out.println("Key returned from " + "'SELECT SCOPE_IDENTITY()': " + autoIncKeyFromFunc);
		} finally {
			JDBCUtils.release(rs, stmt, conn);
		}
	}

	private static void identityFromRS() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = JDBCUtils.getMSSQLConnection();
			stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

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
