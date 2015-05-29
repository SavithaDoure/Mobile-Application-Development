package com.example.week5try;

public class Weather {
	String temperature, humidity, pressure, wind, clouds, precipitation, id, name, country;

	@Override
	public String toString() {
		return "Weather [temperature=" + temperature + ", humidity=" + humidity
				+ ", pressure=" + pressure + ", wind=" + wind + ", clouds="
				+ clouds + ", preciitation=" + precipitation + "]";
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getClouds() {
		return clouds;
	}

	public void setClouds(String clouds) {
		this.clouds = clouds;
	}

	public String getPreciitation() {
		return precipitation;
	}

	public void setPreciitation(String preciitation) {
		this.precipitation = preciitation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
