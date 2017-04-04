package org.lifepoem.samples.jdbc.dbutils;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lifepoem.samples.jdbc.JDBCTranUtils;

/*
 * DBUtils实现增删改查(DAO)
 */
public class EmployeeDao {
	public List<Employee> findForDepartment(int department_id) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "SELECT * FROM employee WHERE department_id = ?";
		List<Employee> list = (List<Employee>) runner.query(JDBCTranUtils.getConnection(), sql, new BeanListHandler<Employee>(Employee.class), new Object[] { department_id });
		return list;
	}

	public Boolean insert(Employee employee) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "INSERT INTO employee(name, salary, department_id) VALUES(?, ?, ?)";
		int num = runner.update(JDBCTranUtils.getConnection(), 
				sql,
				new Object[] { employee.getName(), employee.getSalary(), employee.getDepartment_id() });
		if (num > 0)
			return true;
		return false;
	}

	public Boolean update(Employee employee) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "UPDATE employee SET name = ?, salary = ?, department_id = ? WHERE id = ?";
		int num = runner.update(JDBCTranUtils.getConnection(), 
				sql,
				new Object[] { employee.getName(), employee.getSalary(), employee.getDepartment_id(), employee.getId() });
		if (num > 0)
			return true;
		return false;
	}

	public Boolean delete(int id) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "DELETE FROM employee WHERE id = ?";
		int num = runner.update(JDBCTranUtils.getConnection(), sql, new Object[] { id });
		if (num > 0)
			return true;
		return false;
	}
}
