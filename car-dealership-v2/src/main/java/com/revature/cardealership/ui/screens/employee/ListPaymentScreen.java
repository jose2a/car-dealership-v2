package com.revature.cardealership.ui.screens.employee;

import java.util.Iterator;

import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Payment;
import com.revature.cardealership.services.PaymentService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class ListPaymentScreen extends BaseScreen implements Screen {

	private PaymentService paymentService;

	public ListPaymentScreen(Screen previousScreen) {
		super(previousScreen);

		paymentService = ServiceUtilities.getPaymentService();
	}

	@Override
	public void renderScreen() {

		try {

			printPaymentsTable();
		} catch (Exception e) {
			handleException(e);

			setMessage(e.getMessage());
			goBackToPreviousScreen();
		}

		System.out.println("MENU");
		System.out.println("-----------------------------------------");
		System.out.println("1. Go back to main menu.");
		System.out.println("-----------------------------------------");

		int opt = 0;

		do {
			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 1);

		} while (opt < 1 || opt > 1);

		goBackToPreviousScreen();
	}

	private void printPaymentsTable() {
		String textAlignFormat = "| %-11d | %-19s | %-17s | %-18s | %10s | %12s |%n";

		System.out.format(
				"+--------------------------------------------------------------------------------------------------------+%n");
		System.out.format(
				"|                                           PAYMENTS                                                     |%n");
		System.out.format(
				"+-------------+---------------------+-------------------+--------------------+------------+--------------+%n");
		System.out.format(
				"| Payment No. | Customer            | VIN               | Car                | Date       | Amount       |%n");
		System.out.format(
				"+-------------+---------------------+-------------------+--------------------+------------+--------------+%n");

		Iterator<Payment> paymentIterator = paymentService.getAllPayments().iterator();

		Car car = null;
		Customer customer = null;

		while (paymentIterator.hasNext()) {
			Payment payment = paymentIterator.next();

			car = ServiceUtilities.getCarService().getCarByVin(payment.getVin());
			customer = ServiceUtilities.getCustomerService().getCustomerByUsername(car.getUsername());

			System.out.format(textAlignFormat, payment.getPaymentNo(), customer, car.getVin(),
					car.getMake() + " " + car.getModel(), payment.getPaidDate(),
					String.format("%.2f", payment.getAmountPaid()));
		}

		System.out.format(
				"+-------------+---------------------+-------------------+--------------------+------------+--------------+%n");
	}

	@Override
	public void closeOpenedScreens() {
		// No screens opened from here

	}

}
