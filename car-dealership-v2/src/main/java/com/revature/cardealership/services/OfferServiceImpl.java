package com.revature.cardealership.services;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import com.revature.cardealership.dao.OfferDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;
import com.revature.cardealership.utils.DAOUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class OfferServiceImpl extends BaseService implements OfferService {

	private OfferDAO offerDao;

	private CustomerService customerService;
	private CarService carService;

	public OfferServiceImpl() {
		this.offerDao = DAOUtilities.getOfferDAO();

		this.customerService = ServiceUtilities.getCustomerService();
		this.carService = ServiceUtilities.getCarService();
	}

	@Override
	public boolean updateOffer(Offer offer) {
		validateOfferId(offer.getOfferId());
		
		validateUsername(offer.getUsername());
		validateVin(offer.getVin());

		return offerDao.updateOffer(offer);
	}

	@Override
	public Offer getOfferByIdWithNoJoins(String offerId) {
		validateOfferId(offerId);

		return offerDao.getOfferByOfferIdWithNoJoins(offerId);
	}

	@Override
	public Offer getOfferByVinAndStatusApproved(String vin) {
		validateVin(vin);

		return offerDao.getOfferByVinAndStatusApproved(vin);
	}

	@Override
	public boolean makeAnOffer(String username, String carVin, double amount) throws NotFoundRecordException {
		validateUsername(username);
		validateVin(carVin);

		Customer customer = customerService.getCustomerByUsername(username);

		if (customer == null) {
			throw new NotFoundRecordException("Customer does not exist in the system.");
		}

		// Get car
		Car car = carService.getCarByVin(carVin);

		if (car == null) {
			throw new NotFoundRecordException("Car does not exist in the system.");
		}

		if (car.isSold()) {
			throw new IllegalArgumentException("Car was already sold.");
		}

		// Get new id for contract
		String offerId = RandomStringUtils.randomNumeric(4);

		// Create contract, set it to PENDING
		Offer offer = new Offer(offerId, LocalDate.now(Clock.systemDefaultZone()), amount, OfferStatus.PENDING,
				username, carVin);

		return offerDao.addOffer(offer);
	}

	@Override
	public void acceptOffer(String offerId, int numOfMonths) throws NotFoundRecordException {

		validateOfferId(offerId);

		// Access the contracts through the user collection
		Offer offer = offerDao.getOfferByOfferIdWithNoJoins(offerId);

		if (offer == null) {
			throw new NotFoundRecordException("Offer does not exist in the system");
		}

		if (offer.getStatus() == OfferStatus.REJECTED) {
			throw new IllegalArgumentException("This offer was already rejected.");
		}

		// If the offer was accepted, we don't need to accept it again
		if (offer.getStatus() != OfferStatus.ACCEPTED) {

			offer.setStatus(OfferStatus.ACCEPTED);
			offer.setTotalPayments(numOfMonths);

			double monthlyPayment = offer.getAmount() / numOfMonths;
			offer.setMonthlyPayment(monthlyPayment);

			Car car = carService.getCarByVin(offer.getVin());

			if (car == null) {
				throw new NotFoundRecordException("Car with this vin numbers was not found.");
			}

			car.setUsername(offer.getUsername()); // Set the customer who bought the car
			car.setSold(true); // Car is sold

			carService.updateCar(car);

			// TODO: Implement rejection of not accepted offers using a trigger

			offerDao.updateOffer(offer);
		}
	}

	@Override
	public void rejectOffer(String offerId) throws NotFoundRecordException {
		validateOfferId(offerId);

		Offer offer = offerDao.getOfferByOfferIdWithNoJoins(offerId);

		if (offer == null) {
			throw new NotFoundRecordException("Offer does not exist in the system");
		}

		if (offer.getStatus() == OfferStatus.ACCEPTED) {
			throw new IllegalArgumentException("This offer was already acepted.");
		}

		// If the offer was already rejected we don't need to reject it again
		if (offer.getStatus() != OfferStatus.REJECTED) {
			offer.setStatus(OfferStatus.REJECTED);

			offerDao.updateOffer(offer);
		}
	}

	@Override
	public Set<Offer> getPendingOffers() {
		return offerDao.getOffersByStatus(OfferStatus.PENDING);
	}

	@Override
	public Set<Offer> getAllOffers() {
		return offerDao.getAllOffers();
	}

	// Setting dependencies
	public void setOfferDao(OfferDAO offerDao) {
		this.offerDao = offerDao;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

}
