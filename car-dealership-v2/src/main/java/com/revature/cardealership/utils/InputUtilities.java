package com.revature.cardealership.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtilities {

	private static final Scanner SCAN = new Scanner(System.in);

	public static int getNumber(int min, int max) {

		int input = -1;

		while (input == -1) {
			try {
				input = SCAN.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number between " + min + " and " + max);
				SCAN.next();
			}
		}

		return input;
	}

	public static double getDouble() {
		double input = -1;

		while (input == -1) {
			try {
				input = SCAN.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("Please enter a decimal value");
				SCAN.next();
			}
		}

		return input;

	}

	public static String getString() {
		return SCAN.next();
	}

}