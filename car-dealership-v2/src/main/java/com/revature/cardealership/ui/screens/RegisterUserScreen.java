package com.revature.cardealership.ui.screens;

import com.revature.cardealership.exceptions.PreexistingRecordException;
import com.revature.cardealership.services.UserService;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.LogUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class RegisterUserScreen extends BaseScreen implements Screen {

	private UserService userService;

	public RegisterUserScreen(Screen previousScreen) {
		super(previousScreen);

		userService = ServiceUtilities.getUserService();
	}

	@Override
	public void renderScreen() {

		boolean isRegistered = false;

		String opt = null;

		do {
			ScreenUtilities.cleanScreen();

			System.out.println("REGISTER CUSTOMER");
			System.out.println("-----------------------------------------");

			System.out.println("Enter username:");
			String username = InputUtilities.getString();

			System.out.println("Enter password:");
			String password = InputUtilities.getString();

			System.out.println("Enter first name:");
			String firstName = InputUtilities.getString();

			System.out.println("Enter last name:");
			String lastName = InputUtilities.getString();

			try {
				isRegistered = userService.registerCustomer(username, password, firstName, lastName);

				if (isRegistered) {
					setMessage("Customer registered successfully!!!");
					LogUtilities.trace("Customer registered successfully.");

					goBackToPreviousScreen();
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			} catch (PreexistingRecordException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("-----------------------------------------");

			System.out.println("Do you want to continue? y/n");
			opt = InputUtilities.getString();
		} while (!isRegistered && (opt.equals(null) || opt.toLowerCase().equals("y")));

		if (opt != null) {
			if (!opt.toLowerCase().equals("y")) {
				goBackToPreviousScreen();
			}
		}

	}

	@Override
	public void closeOpenedScreens() {
		// Nothing to close
	}

}
