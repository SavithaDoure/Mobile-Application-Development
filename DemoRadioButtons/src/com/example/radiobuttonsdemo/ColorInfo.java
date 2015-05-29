package com.example.radiobuttonsdemo;

public class ColorInfo {
	String label;
	int rgb;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getRgb() {
		return rgb;
	}
	public void setRgb(int rgb) {
		this.rgb = rgb;
	}
	public ColorInfo(String label, int rgb) {
		super();
		this.label = label;
		this.rgb = rgb;
	}
	
}
