package com.revature.cardealership.ui.screens;

import com.revature.cardealership.utils.LogUtilities;
import com.revature.cardealership.utils.ScreenUtilities;

public abstract class BaseScreen implements Screen {

	protected Screen previousScreen;
	private String message;

	public BaseScreen(Screen previousScreen) {
		this.previousScreen = previousScreen;
	}

	@Override
	public void display() {
		ScreenUtilities.cleanScreen(); // Clean console

		printMessage(); // Print message to screen if any

		closeOpenedScreens(); // Setting opened screen from this screen to save resources

		renderScreen(); // Render actual implementation of this screens
	}

	public abstract void renderScreen();

	public abstract void closeOpenedScreens();

	// Getting previous screen
	public Screen getScreen() {
		return previousScreen;
	}

	public void setMessage(String message) {
		this.message = message;

	}

	public void printMessage() {
		if (message != null && !message.isEmpty()) {
			System.out.println(message + "\n");
		}

		message = null;
	}

	// Helper to go back to previous screen
	protected void goBackToPreviousScreen() {
		if (previousScreen != null) {
			previousScreen.setMessage(message);
			previousScreen.display();
		}
	}

	protected void handleException(Exception e) {
		LogUtilities.error(e.getMessage());
	}

}
