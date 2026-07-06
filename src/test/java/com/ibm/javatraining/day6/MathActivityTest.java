package com.ibm.javatraining.day6;
import static  org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.*;

import com.ibm.javatraining.day6.MathActivity;

import org.junit.jupiter.api.Test;


class MathActivityTest {

	// tests for addition case
	@Test
	void exec001() {
		int a = 5,  b = 2;
		float result = MathActivity.add(a, b);
		float expected = 7;
		
		assertEquals(result, expected);
	}

	// tests for subtraction function
	@Test
	void exec002() {
		int a = 5,  b = 2;
		
		float result = MathActivity.subtract(a, b);
		float expected = 3;
		
		assertEquals(result, expected);
	}
	
	// tests for multiplication function
	@Test
	void exec003() {
		int a = 5,  b = 2;
		
		float result = MathActivity.multiply(a, b);
		float expected = 10;
		
		assertEquals(result, expected);
	}
	
	//Tests division
	@Test
	void exec004() {
		int a = 4,  b = 2;
		
		float result = MathActivity.divide(a, b);
		float expected = 2;
		
		assertEquals(result, expected);
	}

	// Test for arithmetic exception
	@Test
	void exec005() {
		float a = 5,  b = 0;

		Executable executable = () -> MathActivity.divide(a, b);
		assertThrows(ArithmeticException.class, executable);

	}
}
