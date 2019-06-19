package com.revature.cardealership.ui.screens;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Employee;
import com.revature.cardealership.model.User;
import com.revature.cardealership.services.UserService;
import com.revature.cardealership.ui.screens.customer.CustomerMenuScreen;
import com.revature.cardealership.ui.screens.employee.EmployeeMenuScreen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.LogUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class LoginScreen extends BaseScreen implements Screen {

	private Screen employeeMenuScreen;
	private Screen customerMenuScreen;

	private UserService userService;

	public LoginScreen(Screen previousScreen) {
		super(previousScreen);

		userService = ServiceUtilities.getUserService();
	}

	@Override
	public void renderScreen() {

		User user = null;

		String opt = null;

		do {

			System.out.println("LOGIN");
			System.out.println("----------------------------------------------");

			System.out.println("Enter username:");
			String username = InputUtilities.getString();

			System.out.println("Enter password:");
			String password = InputUtilities.getString();

			try {
				user = userService.login(username, password);

				if (user != null) {

					LogUtilities.trace("User authenticated.");

					userLoggedIn(user);
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			} catch (NotFoundRecordException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("----------------------------------------------");
			System.out.println("Login failed. Do you want to try again? y/n");
			opt = InputUtilities.getString();

			ScreenUtilities.cleanScreen();

		} while (user == null && (opt.equals(null) || opt.toLowerCase().equals("y")));

		if (opt != null) {
			if (!opt.toLowerCase().equals("y")) {
				goBackToPreviousScreen();
			}
		}

	}

	private void userLoggedIn(User user) {

		if (user instanceof Employee) {
			employeeMenuScreen = new EmployeeMenuScreen(this, user.getUsername());
			employeeMenuScreen.display();
		} else if (user instanceof Customer) {
			customerMenuScreen = new CustomerMenuScreen(this, user.getUsername());
			customerMenuScreen.display();
		}
	}

	@Override
	public void closeOpenedScreens() {

		if (employeeMenuScreen != null) {
			employeeMenuScreen = null;
		}

		if (customerMenuScreen != null) {
			customerMenuScreen = null;
		}

	}

}
