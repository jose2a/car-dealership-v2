package com.revature.cardealership.ui.screens.customer;

import java.util.Iterator;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.Payment;
import com.revature.cardealership.services.PaymentService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class ListPaymentCustomerScreen extends BaseScreen implements Screen {

	private PaymentService paymentService;

	private String vin;

	public ListPaymentCustomerScreen(Screen previousScreen, String vin) {
		super(previousScreen);

		paymentService = ServiceUtilities.getPaymentService();
		this.vin = vin;
	}

	@Override
	public void renderScreen() {

		try {

			printPaymentsTable();
		} catch (IllegalArgumentException | NotFoundRecordException e) {
			handleException(e);

			setMessage(e.getMessage());
			goBackToPreviousScreen();
			return;
		}

		System.out.println("MENU");
		System.out.println("---------------------------------------------------------");
		System.out.println("1. Make a payment to this car.");
		System.out.println("2. Go back.");
		System.out.println("---------------------------------------------------------");

		int opt = 0;

		do {
			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 1);

		} while (opt < 1 || opt > 2);

		switch (opt) {
		case 1:
			makePayment();
			break;
		case 2:
			break;
		}

		goBackToPreviousScreen();
	}

	private void makePayment() {
		try {
			paymentService.makePayment(vin);

			System.out.println("---------------------------------------------------------");
			setMessage("Your payment was succesfully approved!!!!");
		} catch (IllegalArgumentException | NotFoundRecordException e) {

			System.out.println(e.getMessage());
			ScreenUtilities.askEnterToContinue();
		}

		display();
	}

	private void printPaymentsTable() throws NotFoundRecordException {
		Car car = ServiceUtilities.getCarService().getCarByVin(vin);
		Offer offer = ServiceUtilities.getOfferService().getOfferByVinAndStatusApproved(car.getVin());

		System.out.println("CAR INFO");
		System.out.println("---------------------------------------------------------");
		System.out.println("Vin: " + car.getVin());
		System.out.println("Make/Model: " + car.getMake() + " " + car.getModel());
		System.out.println("Final Price: " + offer.getAmount());
		System.out.println("Amount Owed: " + paymentService.getRemainingBalanceForOffer(offer.getOfferId()));
		System.out.println("Payments (made / total): " + offer.getPaymentsMade() + "/" + offer.getTotalPayments());
		System.out.println("---------------------------------------------------------");

		String textAlignFormat = "| %-11d | %-17s | %-18s | %10s | %12s |%n";

		System.out.format("+----------------------------------------------------------------------------------+%n");
		System.out.format("|                                     PAYMENTS                                     |%n");
		System.out.format("+-------------+-------------------+--------------------+------------+--------------+%n");
		System.out.format("| Payment No. | VIN               | Car                | Date       | Amount       |%n");
		System.out.format("+-------------+-------------------+--------------------+------------+--------------+%n");

		Iterator<Payment> paymentIterator = paymentService.getPaymentsByVin(vin).iterator();

		while (paymentIterator.hasNext()) {
			Payment payment = paymentIterator.next();

			System.out.format(textAlignFormat, payment.getPaymentNo(), car.getVin(),
					car.getMake() + " " + car.getModel(), payment.getPaidDate(),
					String.format("%.2f", payment.getAmountPaid()));
		}

		System.out.format("+-------------+-------------------+--------------------+------------+--------------+%n");
	}

	@Override
	public void closeOpenedScreens() {
		// No screens opened from this
	}

}
