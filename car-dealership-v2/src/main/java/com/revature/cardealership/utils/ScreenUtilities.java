package com.revature.cardealership.utils;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.User;
import com.revature.cardealership.services.UserService;

public class ScreenUtilities {

	public static void showFatalErrorMessage() {
		System.out.println("Something unexpected happened. We're working on resolving it.");
//		System.exit(0);
	}

	public static void cleanScreen() {
		System.out.print("\033[H\033[2J");
	}

	public static void askEnterToContinue() {
		int op = 0;
		do {
			System.out.println("Press 1. to continue");
			InputUtilities.getNumber(1, 1);
		} while (op < 0 && op > 1);
	}

	public static void printUserWelcomeMessage(String username) throws NotFoundRecordException {
		try {
			UserService userService = ServiceUtilities.getUserService();
			User user = userService.getUserByUsername(username);
			userService = null;

			System.out.println("WELCOME: " + user);
			System.out.println("-------------------------------------------");
		} catch (NotFoundRecordException e) {
			throw e;
		}
	}

}
