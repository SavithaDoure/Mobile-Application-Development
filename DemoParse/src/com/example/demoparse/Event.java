package com.example.demoparse;

import java.io.Serializable;

public class Event implements Serializable {
	String ename, eDesc, eLocation, eDate;

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String geteDesc() {
		return eDesc;
	}

	public void seteDesc(String eDesc) {
		this.eDesc = eDesc;
	}

	public String geteLocation() {
		return eLocation;
	}

	public void seteLocation(String eLocation) {
		this.eLocation = eLocation;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	public Event(String ename, String eDesc, String eLocation, String eDate) {
		super();
		this.ename = ename;
		this.eDesc = eDesc;
		this.eLocation = eLocation;
		this.eDate = eDate;
	}

	@Override
	public String toString() {
		return "Event [ename=" + ename + ", eDesc=" + eDesc + ", eLocation="
				+ eLocation + ", eDate=" + eDate + "]";
	}

}
