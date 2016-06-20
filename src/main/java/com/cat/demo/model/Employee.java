package com.cat.demo.model;

import java.util.Date;

public class Employee {

	private int id;

	private String name;

	private double salary;

	private Date hiretime;

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

	public Date getHiretime() {
		return hiretime;
	}

	public void setHiretime(Date hiretime) {
		this.hiretime = hiretime;
	}

}
