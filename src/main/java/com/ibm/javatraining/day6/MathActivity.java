package com.ibm.javatraining.day6;

public class MathActivity {

	
	public static float add(float a, float b) {
		return a + b;
	}

	public static float subtract(float a, float b) {
		return a - b;
	}
	public static float multiply(float a, float b) {
		return a * b;
	}
	/*
	 * Division to
	 * */
	public static float divide(float a, float b) {
		if (b == 0) throw new ArithmeticException();
		return a / b;
		
	}

}
