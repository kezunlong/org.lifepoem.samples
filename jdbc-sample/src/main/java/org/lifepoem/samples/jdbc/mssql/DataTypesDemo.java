package org.lifepoem.samples.jdbc.mssql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;
import microsoft.sql.DateTimeOffset;

public class DataTypesDemo {

	public static void main(String[] args) {

	}

	/*
	 * Before run this demo, create
	 */
	private static void basicDataType() {
		// Create a variable for the connection string.
		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=library;integratedSecurity=true;";

		// Declare the JDBC objects.
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			// Create and execute an SQL statement that returns some data
			// and display it.
			String SQL = "SELECT * FROM DataTypesTable";
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(SQL);
			rs.next();
			displayRow("ORIGINAL DATA", rs);

			// Update the data in the result set.
			rs.updateString(2, "B");
			rs.updateString(3, "Some updated text.");
			rs.updateBoolean(4, true);
			rs.updateDouble(5, 77.89);
			rs.updateDouble(6, 1000.01);
			long timeInMillis = System.currentTimeMillis();
			Timestamp ts = new Timestamp(timeInMillis);
			rs.updateTimestamp(7, ts);
			rs.updateDate(8, new Date(timeInMillis));
			rs.updateTime(9, new Time(timeInMillis));
			rs.updateTimestamp(10, ts);

			// -480 indicates GMT - 8:00 hrs
			((SQLServerResultSet) rs).updateDateTimeOffset(11, DateTimeOffset.valueOf(ts, -480));

			rs.updateRow();

			// Get the updated data from the database and display it.
			rs = stmt.executeQuery(SQL);
			rs.next();
			displayRow("UPDATED DATA", rs);
		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}

			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}

			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	private static void displayRow(String title, ResultSet rs) {
		try {
			System.out.println(title);
			System.out.println(rs.getInt(1) + " , " + // SQL integer type.
					rs.getString(2) + " , " + // SQL char type.
					rs.getString(3) + " , " + // SQL varchar type.
					rs.getBoolean(4) + " , " + // SQL bit type.
					rs.getDouble(5) + " , " + // SQL decimal type.
					rs.getDouble(6) + " , " + // SQL money type.
					rs.getTimestamp(7) + " , " + // SQL datetime type.
					rs.getDate(8) + " , " + // SQL date type.
					rs.getTime(9) + " , " + // SQL time type.
					rs.getTimestamp(10) + " , " + // SQL datetime2 type.
					((SQLServerResultSet) rs).getDateTimeOffset(11)); // SQL datetimeoffset type.

			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
