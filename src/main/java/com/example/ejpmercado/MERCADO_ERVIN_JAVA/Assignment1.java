package com.example.ejpmercado.MERCADO_ERVIN_JAVA;

public class Assignment1 {

	int askForDayOfTheWeek() {
		return 0;
	}
	
	int askForNumberFrom1to20() {
		return 0;
	}
	
	void dayOfTheWeek(int x) {
		switch (x) {
		//case 1 -> Day.Sunday;
		//case 2 -> Day.Monday;
		//case 3 -> Day.Tuesday;
		//case 4 -> Day.Wednesday;
		//case 5 -> Day.Thursday;
		//case 6 -> Day.Friday;
		//case 7 -> Day.Saturday;
		default: 
			//System.out.println("Invalid Day Number");
			break;
		}
	}
	
	void numberPyramidForLoop () {
		int x = 6;
		
		for(int i = 1; i < x; i++) {
			for (int j = 1; j < i; j++) {
				System.out.print(j + "\t");
			}
			System.out.println("");
		}
	}

	void numberPyramidWhileLoop () {
		
	}
	
	void numberPyramidDoWhileLoop () {
		
	}
	
	
}
