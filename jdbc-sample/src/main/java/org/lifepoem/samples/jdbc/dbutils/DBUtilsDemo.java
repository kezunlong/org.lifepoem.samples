package org.lifepoem.samples.jdbc.dbutils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtilsDemo {

	public static void main(String[] args) throws SQLException {
		Department department = new Department();
		department.setName("计划部");
		
		List<Employee> employees = new ArrayList<Employee>();
		Employee e1 = new Employee();
		e1.setName("Name1");
		e1.setSalary(100);
		Employee e2 = new Employee();
		e2.setName("Name2");
		e2.setSalary(200);
		employees.add(e1);
		employees.add(e2);
		
		department.setEmployees(employees);
		
		DepartmentService ds = new DepartmentService();
		ds.insert(department);
		
		List<Department> list = ds.findAll();
		for(Department item : list) {
			printDepartment(item);
		}
	}

	private static void printDepartment(Department item) {
		System.out.println(item);
		for(Employee employee : item.getEmployees()) {
			System.out.println(employee);
		}
	}

}
