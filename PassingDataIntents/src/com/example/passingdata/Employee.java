package com.example.passingdata;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {
	private String name, address;
	private double age;

	public Employee(String name, String address, double age) {
		super();
		this.name = name;
		this.address = address;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(address);
		dest.writeDouble(age);

	}

	public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
		public Employee createFromParcel(Parcel in) {
			return new Employee(in);
		}

		public Employee[] newArray(int size) {
			return new Employee[size];
		}
	};

	private Employee(Parcel in) {
		this.name = in.readString();
		this.address = in.readString();
		this.age = in.readDouble();
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", address=" + address + ", age="
				+ age + "]";
	}
}
