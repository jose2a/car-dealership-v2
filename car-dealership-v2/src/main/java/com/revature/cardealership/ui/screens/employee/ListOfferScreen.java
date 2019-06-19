package com.revature.cardealership.ui.screens.employee;

import java.util.Iterator;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.services.OfferService;
import com.revature.cardealership.ui.screens.BaseScreen;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ScreenUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class ListOfferScreen extends BaseScreen implements Screen {

	private OfferService offerService;

	public ListOfferScreen(Screen previousScreen) {
		super(previousScreen);

		offerService = ServiceUtilities.getOfferService();
	}

	@Override
	public void renderScreen() {
		
		printOffersTable();

		System.out.println("MENU");
		System.out.println("-----------------------------------------");
		System.out.println("1. Accept an offer.");
		System.out.println("2. Reject an offer.");
		System.out.println("3. Go back to main menu.");
		System.out.println("-----------------------------------------");

		int opt = 0;

		do {

			System.out.println("Select an option from the menu:");
			opt = InputUtilities.getNumber(1, 3);

		} while (opt < 1 || opt > 3);

		switch (opt) {
		case 1:
			acceptOffer();
			break;
		case 2:
			rejectOffer();
			break;
		case 3:
			break;
		}

		goBackToPreviousScreen();

	}

	private void acceptOffer() {

		try {
			System.out.println("-----------------------------------------");
			System.out.println("Enter offer number to be accepted:");
			String offerId = InputUtilities.getString();

			System.out.println("Enter number of months for the contract:");
			int numberOfMonths = InputUtilities.getNumber(0, 75);

			offerService.acceptOffer(offerId, numberOfMonths);

			setMessage("Offer accepted successfully!!!");

		} catch (IllegalArgumentException | NotFoundRecordException e) {
			System.out.println(e.getMessage());
			ScreenUtilities.askEnterToContinue();
		}

		display();
	}

	private void rejectOffer() {

		try {
			System.out.println("-----------------------------------------");
			System.out.println("Enter offer number to be rejected:");

			String contractId = InputUtilities.getString();
			offerService.rejectOffer(contractId);

			setMessage("Offer rejected successfully!!!");

		} catch (IllegalArgumentException | NotFoundRecordException e) {
			System.out.println(e.getMessage());
			ScreenUtilities.askEnterToContinue();
		}

		display();
	}

	private void printOffersTable() {
		String textAlignFormat = "| %-6s | %-19s | %-17s | %-18s | %9s | %10s | %14s |%n";

		System.out.format(
				"+-----------------------------------------------------------------------------------------------------------------+%n");
		System.out.format(
				"|                                            PENDING OFFERS                                                       |%n");
		System.out.format(
				"+--------+---------------------+-------------------+--------------------+-----------+------------+----------------+%n");
		System.out.format(
				"| Offer  | Customer            | VIN               | Car                | Price     | Date       | Amount Offered |%n");
		System.out.format(
				"+--------+---------------------+-------------------+--------------------+-----------+------------+----------------+%n");

		Iterator<Offer> offerIterator = offerService.getPendingOffers().iterator();

		while (offerIterator.hasNext()) {
			Offer offer = offerIterator.next();
			Car car = offer.getCar();

			System.out.format(textAlignFormat, offer.getOfferId(), offer.getCustomer(), car.getVin(),
					car.getMake() + ", " + car.getModel(), String.format("%.2f", car.getPrice()), offer.getSignedDate(),
					String.format("%.2f", offer.getAmount()));
		}

		System.out.format(
				"+--------+---------------------+-------------------+--------------------+-----------+------------+----------------+%n");
	}

	@Override
	public void closeOpenedScreens() {
		// No screens opened from here

	}

}
