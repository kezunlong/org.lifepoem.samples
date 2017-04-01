package org.lifepoem.samples.jdbc.mysql;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.lifepoem.samples.jdbc.JDBCUtils;

/**
 * 
 * 大数据处理指的是对CLOB和BLOB类型数据的操作
 * CLOB用于存储大文本数据，BLOB用于存储二进制数据
 * 在应用程序中，必须使用PreparedStatement完成，并且所有的操作都要以IO流的形式进行读取和存放。
 * 对于MySQL而言，CLOB对应于TEXT类型，BLOB对应于blob类型
 * 
 * @author irfgoy
 *
 */
public class LargeDataDemo {
	
	public static void main(String[] args) {
		writeCLOBData();
		readCLOBData();
		writeBLOBData();
		readBLOBData();
	}

	/*
	 * Create table testclob in sample.sql before run this method
	 */
	private static void writeCLOBData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JDBCUtils.getMySQLConnection();
			String sql = "INSERT INTO testclob(name, resume) values(?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "ke");
			File file = new File("D:" + File.separator + "test.txt");
			Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			stmt.setCharacterStream(2, reader, (int)file.length());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(stmt, conn);
		}
	}
	
	private static void readCLOBData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getMySQLConnection();
			String sql = "SELECT * FROM testclob";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString("name");
				Reader reader = rs.getCharacterStream("resume");
				Writer out = new FileWriter("D:" + File.separator + "test2.txt");
				int temp;
				while((temp = reader.read()) != -1) {
					out.write(temp);
				}
				out.close();
				reader.close();
				
				System.out.println(name);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(stmt, conn);
		}
	}
	
	/*
	 * Create table testblob in sample.sql before run this method
	 */
	private static void writeBLOBData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JDBCUtils.getMySQLConnection();
			String sql = "INSERT INTO testblob(name, img) values(?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "ke");
			File file = new File("D:" + File.separator + "test.jpg");
			InputStream in = new FileInputStream(file);
			stmt.setBinaryStream(2, in, (int)file.length());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(stmt, conn);
		}
	}
	
	private static void readBLOBData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getMySQLConnection();
			String sql = "SELECT * FROM testblob";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString("name");
				InputStream in = new BufferedInputStream(rs.getBinaryStream("img"));
				OutputStream out = new BufferedOutputStream(new FileOutputStream("D:" + File.separator + "test2.jpg"));
				int temp;
				while((temp = in.read()) != -1) {
					out.write(temp);
				}
				out.close();
				in.close();
				
				System.out.println(name);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.release(stmt, conn);
		}
	}
}
