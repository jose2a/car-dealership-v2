package com.revature.cardealership.drivers;

import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.ui.screens.WelcomeScreen;

public class Driver {

	public static void main(String[] args) {

		Screen welcomeScreen = new WelcomeScreen(null);
		welcomeScreen.display();
	}

}
