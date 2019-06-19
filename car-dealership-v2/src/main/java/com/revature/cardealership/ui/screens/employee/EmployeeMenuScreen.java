package com.revature.cardealership.ui.screens.employee;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;

public class EmployeeMenuScreen extends BaseScreen implements Screen {

	private String username;

	private Screen listCarScreen;
	private Screen addCarScreen;
	private Screen listOfferScreen;
	private Screen listPaymentScreen;

	public EmployeeMenuScreen(Screen previousScreen, String username) {
		super(previousScreen);

		this.username = username;
	}

	@Override
	public void renderScreen() {

		try {
			ScreenUtilities.printUserWelcomeMessage(username);
		} catch (NotFoundRecordException e) {
			handleException(e);

			goBackToPreviousScreen();
		}

		System.out.println("EMPLOYEE MENU");
		System.out.println("-------------------------------------------");
		System.out.println(" 1. See cars.");
		System.out.println(" 2. Add car.");
		System.out.println(" 3. See offers.");
		System.out.println(" 4. See payments.");
		System.out.println(" 5. Logout.");
		System.out.println("-------------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 5);
		} while (opt < 1 || opt > 5);

		proccessSelectedOption(opt);

	}

	private void proccessSelectedOption(int opt) {
		switch (opt) {
		case 1:
			listCarScreen = new ListCarScreen(this);
			listCarScreen.display();
			break;
		case 2:
			addCarScreen = new AddCarScreen(this);
			addCarScreen.display();
			break;
		case 3:
			listCarScreen = new ListOfferScreen(this);
			listCarScreen.display();
			break;
		case 4:
			listPaymentScreen = new ListPaymentScreen(this);
			listPaymentScreen.display();
			break;
		case 5:
			if (previousScreen != null && previousScreen.getScreen() != null) {
				previousScreen.getScreen().display();
			}
		default:
			break;
		}
	}

	@Override
	public void closeOpenedScreens() {
		if (listCarScreen != null) {
			listCarScreen = null;
		}
		if (addCarScreen != null) {
			addCarScreen = null;
		}
		if (listOfferScreen != null) {
			listOfferScreen = null;
		}
		if (listPaymentScreen != null) {
			listPaymentScreen = null;
		}
	}

}
