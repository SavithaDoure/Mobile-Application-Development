package com.mad.bean;

import java.io.Serializable;
import java.util.List;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class WeatherDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "WeatherDetail [cityName=" + cityName + ", country=" + country
				+ ", dailyTemp=" + dailyTemp + "]";
	}

	private String cityName,country;
	private List<DailyTemp> dailyTemp;
	public List<DailyTemp> getDailyTemp() {
		return dailyTemp;
	}

	public void setDailyTemp(List<DailyTemp> dailyTemp) {
		this.dailyTemp = dailyTemp;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	
	

}
