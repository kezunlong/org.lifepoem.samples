package org.lifepoem.samples.jdbc.dbutils;

import java.sql.SQLException;
import java.util.List;

import org.lifepoem.samples.jdbc.JDBCTranUtils;

/**
 * 在Service层中，通过JDBCTranUtils进行事务管理和多表操作
 * @author Vincent Ke
 *
 */
public class DepartmentService {
	public List<Department> findAll() throws SQLException {
		DepartmentDao dd = new DepartmentDao();
		EmployeeDao ed = new EmployeeDao();
		
		List<Department> items = dd.findAll();
		for(Department department : items) {
			department.setEmployees(ed.findForDepartment(department.getId()));
		}
		
		return items;
	}
	
	public void insert(Department department) {
		try {
			JDBCTranUtils.startTransaction();
			
			DepartmentDao dd = new DepartmentDao();
			EmployeeDao ed = new EmployeeDao();
			
			dd.insert(department);
			if(department.getEmployees() != null) {
				for(Employee employee : department.getEmployees()) {
					employee.setDepartment_id(department.getId());
					ed.insert(employee);
				}
			}
			
			JDBCTranUtils.commit();
		}
		catch(Exception e) {
			JDBCTranUtils.rollback();
			
			e.printStackTrace();
		}
		finally {
			JDBCTranUtils.close();
		}
	}
}
