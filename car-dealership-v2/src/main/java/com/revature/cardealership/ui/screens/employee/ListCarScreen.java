package com.revature.cardealership.ui.screens.employee;

import java.util.Iterator;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.services.CarService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class ListCarScreen extends BaseScreen implements Screen {

	private CarService carService;

	public ListCarScreen(Screen previousScreen) {
		super(previousScreen);

		this.carService = ServiceUtilities.getCarService();
	}

	@Override
	public void renderScreen() {

		printCarsTable();

		System.out.println("MENU");
		System.out.println("---------------------------------------------------------------------");
		System.out.println("1. Remove a car.");
		System.out.println("2. Go back to main menu.");
		System.out.println("---------------------------------------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 2);

		} while (opt < 1 || opt > 2);

		switch (opt) {
		case 1:
			removeCar();
			break;
		case 2:
			break;
		}

		goBackToPreviousScreen();
	}

	private void removeCar() {

		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Enter VIN number of the car to be deleted:");
			String vin = InputUtilities.getString();

			carService.removeCar(vin);

			setMessage("Car removed successfully!!!");
		} catch (IllegalArgumentException | NotFoundRecordException e) {
			System.out.println(e.getMessage());

			ScreenUtilities.askEnterToContinue();
		}

		display();
	}

	private void printCarsTable() {
		String textAlignFormat = "| %-17s | %-10s | %-11s | %9s | %6s |%n";

		System.out.format("+-------------------------------------------------------------------+%n");
		System.out.format("|                                CARS                               |%n");
		System.out.format("+-------------------+------------+-------------+-----------+--------+%n");
		System.out.format("| VIN               | Make       | Model       | Price     | Sold   |%n");
		System.out.format("+-------------------+------------+-------------+-----------+--------+%n");

		Iterator<Car> carIterator = carService.getAllCars().iterator();

		while (carIterator.hasNext()) {
			Car car = carIterator.next();

			System.out.format(textAlignFormat, car.getVin(), car.getMake(), car.getModel(),
					String.format("%.2f", car.getPrice()), (car.isSold()) ? "Yes" : "No");
		}

		System.out.format("+-------------------+------------+-------------+-----------+--------+%n");
	}

	@Override
	public void closeOpenedScreens() {
		// No screens opened from here

	}

}
