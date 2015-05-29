package com.example.inclass04;

import java.util.Random;

public class HeavyWork {
	static final int COUNT = 900000;
	static double getNumber(){
		double num = 0;
		Random rand = new Random();
		for(int i=0;i<COUNT; i++){
			num = num + rand.nextDouble();
		}
		return num / ((double) COUNT);
	}
}