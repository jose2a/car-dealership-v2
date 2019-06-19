package com.revature.cardealership.ui.screens.customer;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;

public class CustomerMenuScreen extends BaseScreen implements Screen {

	private Screen listCarCustomerScreen;
	private Screen myCarListScreen;

	private String username;

	public CustomerMenuScreen(Screen previousScreen, String username) {
		super(previousScreen);

		this.username = username;
	}

	@Override
	public void renderScreen() {

		try {
			ScreenUtilities.printUserWelcomeMessage(username);
		} catch (NotFoundRecordException e) {
			handleException(e);
		}

		System.out.println("CUSTOMER MENU");
		System.out.println("----------------------------------------");
		System.out.println("1. See cars.");
		System.out.println("2. See the cars you own.");
		System.out.println("3. Logout.");
		System.out.println("----------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 3);
		} while (opt < 1 || opt > 3);

		precessOptionSelected(opt);

	}

	private void precessOptionSelected(int opt) {
		switch (opt) {
		case 1:
			listCarCustomerScreen = new ListCarCustomerScreen(this, this.username);
			listCarCustomerScreen.display();
			break;
		case 2:
			myCarListScreen = new MyCarListScreen(this, username);
			myCarListScreen.display();
			break;
		case 3:
			if (previousScreen != null && previousScreen.getScreen() != null) {
				previousScreen.getScreen().display();
			}
		default:
			break;
		}
	}

	@Override
	public void closeOpenedScreens() {
		if (listCarCustomerScreen != null) {
			listCarCustomerScreen = null;
		}

		if (myCarListScreen != null) {
			myCarListScreen = null;
		}

	}

}
