package org.lifepoem.samples.jdbc.dbutils;

public class Employee {
	private int id;
	private String name;
	private double salary;
	private int department_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	
	public String toString() {
		return "{ id = " + id + ", name = " + name + ", salary = " + salary + ", department_id = " + department_id + " }";
	}
}
