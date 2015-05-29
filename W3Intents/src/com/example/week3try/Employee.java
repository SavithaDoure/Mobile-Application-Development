package com.example.week3try;

import java.io.Serializable;

public class Employee implements Serializable {
	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", email=" + email
				+ ", phone=" + phone + ", department=" + department + "]";
	}

	private String name, age, email, phone, department;

	public Employee(String name, String age, String email, String phone,
			String department) {
		super();
		this.name = name;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
