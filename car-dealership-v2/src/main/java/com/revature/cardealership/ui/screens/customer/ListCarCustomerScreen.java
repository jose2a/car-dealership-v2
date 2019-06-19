package com.revature.cardealership.ui.screens.customer;

import java.util.Iterator;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.services.CarService;
import com.revature.cardealership.services.OfferService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class ListCarCustomerScreen extends BaseScreen implements Screen {

	private CarService carService;
	private String username;

	public ListCarCustomerScreen(Screen previousScreen, String username) {
		super(previousScreen);

		this.username = username;
		this.carService = ServiceUtilities.getCarService();
	}

	@Override
	public void renderScreen() {

		printCarsInventoryTable();

		System.out.println("MENU");
		System.out.println("-----------------------------------------");
		System.out.println("1. Make an offer for a car.");
		System.out.println("2. Go back to main menu.");
		System.out.println("-----------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 2);

		} while (opt < 1 || opt > 2);

		switch (opt) {
		case 1:
			makeAnOffer();
			break;
		case 2:
			break;
		}
		
		goBackToPreviousScreen();

	}

	private void printCarsInventoryTable() {
		String textAlignFormat = "| %-17s | %-10s | %-11s | %9s |%n";

		System.out.format("+----------------------------------------------------------+%n");
		System.out.format("|                              CARS                        |%n");
		System.out.format("+-------------------+------------+-------------+-----------+%n");
		System.out.format("| VIN               | Make       | Model       | Price     |%n");
		System.out.format("+-------------------+------------+-------------+-----------+%n");

		Iterator<Car> carIterator = carService.getCarsInInvertory().iterator();

		while (carIterator.hasNext()) {
			Car car = carIterator.next();

			System.out.format(textAlignFormat, car.getVin(), car.getMake(), car.getModel(),
					String.format("%.2f", car.getPrice()));
		}

		System.out.format("+-------------------+------------+-------------+-----------+%n");
	}

	private void makeAnOffer() {

		try {

			OfferService offerService = ServiceUtilities.getOfferService();

			System.out.println("MAKE AN OFFER");
			System.out.println("------------------------------------------------------------");

			System.out.println("Enter VIN number of the car that you want a make an offer:");
			String vin = InputUtilities.getString();

			System.out.println("Enter the amount of your offer:");
			double amount = InputUtilities.getDouble();

			offerService.makeAnOffer(username, vin, amount);

			setMessage("The offer was successfully made!!!\n"
					+ "The car will be in your list of cars if your offer gets accepted.");

			display();
		} catch (IllegalArgumentException | NotFoundRecordException e) {
			System.out.println(e.getMessage());
			ScreenUtilities.askEnterToContinue();
		}

	}

	@Override
	public void closeOpenedScreens() {
		// No screens opened from here

	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	

}
