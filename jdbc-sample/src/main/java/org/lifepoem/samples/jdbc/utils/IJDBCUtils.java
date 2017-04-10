package org.lifepoem.samples.jdbc.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public interface IJDBCUtils {
	
	Connection getConnection();
	
	void startTransaction();
	void commit();
	void rollback();
	
	String pagingSql(String sql, int start, int length, String orderBy);
	String countingSql(String sql);
}
