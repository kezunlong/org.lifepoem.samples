package org.lifepoem.samples.jdbc.dbutils;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lifepoem.samples.jdbc.JDBCTranUtils;

/*
 * DBUtils实现增删改查(DAO)
 */
public class DepartmentDao {
	public List<Department> findAll() throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "SELECT * FROM department";
		List<Department> list = (List<Department>) runner.query(JDBCTranUtils.getConnection(), sql, new BeanListHandler<Department>(Department.class));
		return list;
	}

	public Department find(int id) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "SELECT * FROM department WHERE id = ?";
		Department department = (Department) runner.query(JDBCTranUtils.getConnection(), sql, new BeanHandler<Department>(Department.class),
				new Object[] { id });
		return department;
	}

	public Boolean insert(Department department) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "INSERT INTO department(name) VALUES(?)";
		int num = runner.update(JDBCTranUtils.getConnection(), sql, new Object[] { department.getName() });

		if (num > 0) {
			String sql2 = "SELECT LAST_INSERT_ID()";
			int id = runner.query(JDBCTranUtils.getConnection(), sql2, new ScalarHandler<BigInteger>()).intValue();
			department.setId(id);
			return true;
		}
		return false;
	}

	public Boolean update(Department department) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "UPDATE department SET name = ? WHERE id = ?";
		int num = runner.update(JDBCTranUtils.getConnection(), sql, new Object[] { department.getName(), department.getId() });
		if (num > 0)
			return true;
		return false;
	}

	public Boolean delete(int id) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "DELETE FROM department WHERE id = ?";
		int num = runner.update(JDBCTranUtils.getConnection(), sql, new Object[] { id });
		if (num > 0)
			return true;
		return false;
	}
}
