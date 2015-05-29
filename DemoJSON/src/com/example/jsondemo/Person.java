package com.example.jsondemo;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
	String dept, name;
	int age, id;

	@Override
	public String toString() {
		return "Person [id=" + dept + ", name=" + name + ", age=" + age
				+ ", department=" + id + "]";
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Person createPerson(JSONObject jsonObject)
			throws JSONException {
		Person person = new Person();
		person.setName(jsonObject.getString("name"));
		person.setDept(jsonObject.getString("department"));
		person.setId(jsonObject.getInt("id"));
		person.setAge(jsonObject.getInt("age"));
		return person;
	}
}
