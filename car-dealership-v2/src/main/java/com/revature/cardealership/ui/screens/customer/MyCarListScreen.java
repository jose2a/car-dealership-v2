package com.revature.cardealership.ui.screens.customer;

import java.util.Iterator;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.services.CarService;
import com.revature.cardealership.services.OfferService;
import com.revature.cardealership.services.PaymentService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class MyCarListScreen extends BaseScreen implements Screen {

	private Screen listPaymentCustomerScreen;

	private CarService carService;
	private String username;

	public MyCarListScreen(Screen previousScreen, String username) {
		super(previousScreen);

		this.carService = ServiceUtilities.getCarService();
		this.username = username;
	}

	@Override
	public void renderScreen() {

		try {

			printCustomerCarsTable();
		} catch (Exception e) {
			handleException(e);

			setMessage(e.getMessage());
			goBackToPreviousScreen();
			return;
		}

		System.out.println("MENU");
		System.out.println("-----------------------------------------------");
		System.out.println("1. View remaining payments for a car.");
		System.out.println("2. Go back to main menu.");
		System.out.println("-----------------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 2);

		} while (opt < 1 || opt > 2);

		switch (opt) {
		case 1:
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("Enter the vin number of the car that you want to see the remaining payments:");
			String vin = InputUtilities.getString();

			listPaymentCustomerScreen = new ListPaymentCustomerScreen(this, vin);
			listPaymentCustomerScreen.display();
			break;
		case 2:
			break;
		}

		goBackToPreviousScreen();

	}

	private void printCustomerCarsTable() throws NotFoundRecordException {
		String textAlignFormat = "| %-17s | %-10s | %-11s | %11s | %11s |%n";

		System.out.format("+--------------------------------------------------------------------------+%n");
		System.out.format("|                               YOUR CARS                                  |%n");
		System.out.format("+-------------------+------------+-------------+-------------+-------------+%n");
		System.out.format("| VIN               | Make       | Model       | Final Price | Amount Owed |%n");
		System.out.format("+-------------------+------------+-------------+-------------+-------------+%n");

		Iterator<Car> carIterator = carService.getCarsByCustomerUsername(username).iterator();
		OfferService offerService = ServiceUtilities.getOfferService();
		PaymentService paymentService = ServiceUtilities.getPaymentService();

		while (carIterator.hasNext()) {
			Car car = carIterator.next();

			Offer offer = offerService.getOfferByVinAndStatusApproved(car.getVin());
			double finalPrice = offer.getAmount();
			double amountOwed = paymentService.getRemainingBalanceForOffer(offer.getOfferId());

			System.out.format(textAlignFormat, car.getVin(), car.getMake(), car.getModel(),
					String.format("%.2f", finalPrice), String.format("%.2f", amountOwed));

		}

		System.out.format("+-------------------+------------+-------------+-------------+-------------+%n");
	}

	@Override
	public void closeOpenedScreens() {
		if (listPaymentCustomerScreen != null) {
			listPaymentCustomerScreen = null;
		}

	}

}
