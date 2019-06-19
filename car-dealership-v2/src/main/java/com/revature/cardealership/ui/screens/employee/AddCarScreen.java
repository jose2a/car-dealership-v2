package com.revature.cardealership.ui.screens.employee;

import com.revature.cardealership.exceptions.PreexistingRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.services.CarService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class AddCarScreen extends BaseScreen implements Screen {

	private CarService carService;

	public AddCarScreen(Screen previousScreen) {
		super(previousScreen);

		this.carService = ServiceUtilities.getCarService();
	}

	@Override
	public void renderScreen() {
		
		System.out.println("ADD CAR");
		System.out.println("-----------------------------------------");

		try {
			carService.addCar(getCarFromInput());

			setMessage("Car added successfully!!!");
		} catch (IllegalArgumentException | PreexistingRecordException e) {
			System.out.println(e.getMessage());
			ScreenUtilities.askEnterToContinue();
		}

		goBackToPreviousScreen();
	}

	private Car getCarFromInput() {
		String vin; // VIN number
		String make; // Make
		String model; // Model
		double price; // Price
		boolean isSold = false; // Is the car sold?
		boolean active = true; // The car is new, should be active

		System.out.println("Enter VIN number:");
		vin = InputUtilities.getString();

		System.out.println("Enter make:");
		make = InputUtilities.getString();

		System.out.println("Enter model:");
		model = InputUtilities.getString();

		System.out.println("Enter price:");
		price = InputUtilities.getDouble();

		Car car = new Car(vin, make, model, price, isSold, active);

		return car;
	}

	@Override
	public void closeOpenedScreens() {
		// no screens opened from here
		
	}

}
