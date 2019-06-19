package com.revature.cardealership.ui.screens;

import com.revature.cardealership.utils.InputUtilities;

public class WelcomeScreen extends BaseScreen implements Screen {
	
	private Screen loginScreen;
	private Screen registerUserScreen;

	public WelcomeScreen(Screen previousScreen) {
		super(previousScreen);
	}

	@Override
	public void renderScreen() {
		
		System.out.println("==============================================");
		System.out.println("           WELCOME TO MY DEALERSHIP");
		System.out.println("==============================================");
		System.out.println("\nMENU");
		System.out.println("----------------------------------------------");
		System.out.println("1. Login.");
		System.out.println("2. Register.");
		System.out.println("3. Exit.");
		System.out.println("----------------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 3);
		} while (opt < 1 && opt > 3);

		optionSelected(opt);

	}

	private void optionSelected(int opt) {
		switch (opt) {
		case 1:
			loginScreen = new LoginScreen(this);
			loginScreen.display();
			break;
		case 2:
			registerUserScreen = new RegisterUserScreen(this);
			registerUserScreen.display();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			break;
		}
	}

	@Override
	public void closeOpenedScreens() {
		if (loginScreen != null) {
			loginScreen = null;
		}
		
		if (registerUserScreen != null) {
			registerUserScreen = null;
		}
		
	}

}
