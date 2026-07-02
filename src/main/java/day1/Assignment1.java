package day1;

import java.util.Scanner;

enum Day {
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
}

public class Assignment1 {

	public static int askForNumberFrom1to20() {
		Scanner scanner = new Scanner(System.in);
		int given = 99;

		while (given < 1 || given > 20) {
			System.out.print("Enter valid number from 1-20: ");
			given = scanner.nextInt();
		}
		scanner.close();
		return given;
	}

	static Day dayOfTheWeekSwitchCase() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter valid number from 1-7: ");
		int given = scanner.nextInt();
		scanner.close();

		switch (given) {
			case 1:
				return Day.Monday;
			case 2:
				return Day.Tuesday;
			case 3:
				return Day.Wednesday;
			case 4:
				return Day.Thursday;
			case 5:
				return Day.Friday;
			case 6:
				return Day.Saturday;
			case 7:
				return Day.Sunday;
			default:
				System.out.println("Invalid day: " + given);
				return null;
		}
	}

	static Day dayOfTheWeekPatternMatching() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter valid number from 1-7: ");
		int given = scanner.nextInt();
		scanner.close();

		return switch (given) {
			case 1 -> Day.Monday;
			case 2 -> Day.Tuesday;
			case 3 -> Day.Wednesday;
			case 4 -> Day.Thursday;
			case 5 -> Day.Friday;
			case 6 -> Day.Saturday;
			case 7 -> Day.Sunday;
			default -> {
				System.out.println("Invalid day: " + given);
				yield null;
			}
		};
	}

	static void numberPyramidForLoop() {
		int x =  askForNumberFrom1to20();

		for (int i = 1; i < x + 1; i++) {
			for (int j = 1; j < i + 1; j++) {
				System.out.print(j + "\t");
			}
			System.out.println("");
		}
	}

	static void numberPyramidWhileLoop() {
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

	static void numberPyramidDoWhileLoop() {
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

	static int blackjack(int a, int b) {
		int i = a >= 22 ? 0 : a;
		int j = b >= 22 ? 0 : b;
		return (i > j) ? i : j;
	}

}
