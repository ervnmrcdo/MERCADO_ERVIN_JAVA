package com.example.ejpmercado.MERCADO_ERVIN_JAVA;
import java.util.Scanner;

enum Day {
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
}

public class Assignment1 {

	int askForDayOfTheWeek() {
		return 0;
	}
	
	int askForNumberFrom1to20() {
		Scanner scanner = new Scanner(System.in); 
		int given = 99;
		
		while (given < 1 || given > 20) {
			System.out.print("Enter valid number from 1-20: ");
			given = scanner.nextInt(); 
		}
		scanner.close();
		return given;
	}
	
	Day dayOfTheWeek(int x) {
		return switch (x) {
			case 1 -> Day.Sunday;
			case 2 -> Day.Monday;
			case 3 -> Day.Tuesday;
			case 4 -> Day.Wednesday;
			case 5 -> Day.Thursday;
			case 6 -> Day.Friday;
			case 7 -> Day.Saturday;
			default -> {
	            System.out.println("Invalid day: " + x);
	            yield null;
			}
		};
	}
	
	void numberPyramidForLoop () {
		int x = askForNumberFrom1to20();
		
		for(int i = 1; i < x + 1; i++) {
			for (int j = 1; j < i + 1; j++) {
				System.out.print(j + "\t");
			}
			System.out.println("");
		}
	}

	void numberPyramidWhileLoop () {
		int x = askForNumberFrom1to20();
		int i = 1, j = 1;
		while (i < x + 1) {
			while (j < i + 1) {
				System.out.print(j + "\t");
				j++;
			}
			System.out.println("");
			i++;
			j = 1;
		}
	}
	
	void numberPyramidDoWhileLoop () {
		int x = askForNumberFrom1to20();
		int i = 1, j = 1;
		do {
			do {
				System.out.print(j + "\t");
				j++;
			} while (j < i + 1);
			System.out.println("");
			i++;
			j = 1;
		} while (i < x + 1);
	}
	
	
}
