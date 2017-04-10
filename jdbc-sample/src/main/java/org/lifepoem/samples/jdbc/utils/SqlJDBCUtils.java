package org.lifepoem.samples.jdbc.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang.StringUtils;

public class SqlJDBCUtils extends JDBCUtils {

    private static final String SELECT_PAGING = "WITH C AS"
+ "("
+ "    SELECT *, ROW_NUMBER() OVER (ORDER BY %s) AS Row"
+ "    FROM ("
+ "        %s"
+ "    ) AS T1" 
+ ")"
+ "SELECT TOP %d *"
+ "FROM C"
+ "WHERE Row >= %d and Row < %d";
    
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM ("
            + "   (%s) SUBQ"
            + ")";
	
	public SqlJDBCUtils(Connection conn) {
		super(conn);
		
	}

	@Override
	public String pagingSql(String sql, int start, int length, String orderBy) {
		String sqlPaging = String.format(SELECT_PAGING, 
				StringUtils.isEmpty(orderBy) ? "(SELECT 0)" : orderBy,
				sql, 
				start, 
				start + length);
		
		return sqlPaging;
	}

	@Override
	public String countingSql(String sql) {
		return String.format(SELECT_COUNT, sql);
	}
	
	
}
