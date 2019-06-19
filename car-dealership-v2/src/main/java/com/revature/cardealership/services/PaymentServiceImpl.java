package com.revature.cardealership.services;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import com.revature.cardealership.dao.PaymentDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.Payment;
import com.revature.cardealership.utils.DAOUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class PaymentServiceImpl extends BaseService implements PaymentService {

	private PaymentDAO paymentDAO;

	private CustomerService customerService;
	private CarService carService;
	private OfferService offerService;

	public PaymentServiceImpl() {
		paymentDAO = DAOUtilities.getPaymentDAO();
		customerService = ServiceUtilities.getCustomerService();
		carService = ServiceUtilities.getCarService();
		offerService = ServiceUtilities.getOfferService();
	}

	@Override
	public boolean makePayment(String vin) throws NotFoundRecordException {
		validateVin(vin);

		Offer offer = offerService.getOfferByVinAndStatusApproved(vin);

		if (offer == null) {
			throw new NotFoundRecordException("Offer not found.");
		}

		// Check if the user has not made all his payments
		if (offer.getPaymentsMade() < offer.getTotalPayments()) {

			int paymentNo = Integer.parseInt(RandomStringUtils.randomNumeric(4));

			Payment payment = new Payment(paymentNo, LocalDate.now(Clock.systemDefaultZone()),
					offer.getMonthlyPayment(), vin, offer.getOfferId());

			boolean isAdded = paymentDAO.addPayment(payment);

			if (isAdded) {
				offer.setPaymentsMade(offer.getPaymentsMade() + 1);

				return offerService.updateOffer(offer);
			}
		}

		return false;
	}

	@Override
	public Set<Payment> getAllPayments() {
		return paymentDAO.getAllPayments();
	}

	@Override
	public Set<Payment> getAllPaymentsByCustomerUsername(String username) throws NotFoundRecordException {
		validateUsername(username);

		Customer customer = customerService.getCustomerByUsername(username);

		if (customer == null) {
			throw new NotFoundRecordException("The customer is not in the system.");
		}

		return paymentDAO.getAllPaymentsByCustomerUsername(username);
	}

	@Override
	public Set<Payment> getPaymentsByVin(String vin) throws NotFoundRecordException {
		validateVin(vin);

		Car car = carService.getCarByVin(vin);

		if (car == null) {
			throw new NotFoundRecordException("The car is not in the system.");
		}

		Set<Payment> payments = paymentDAO.getPaymentsByVin(vin);

//		Offer offer = offerService.getOfferByVinAndStatusApproved(vin);
//		fillRemainingPayments(offer, payments);

		return payments;
	}

	@Override
	public Set<Payment> getPaymentsForCarVinAndCustomerUsername(String vin, String username)
			throws NotFoundRecordException {

		validateVin(vin);

		Car car = carService.getCarByVin(vin);

		if (car == null) {
			throw new NotFoundRecordException("The car is not in the system.");
		}

		validateUsername(username);

		Customer customer = customerService.getCustomerByUsername(username);

		if (customer == null) {
			throw new NotFoundRecordException("The customer is not in the system.");
		}

		Set<Payment> payments = paymentDAO.getPaymentsByCarVinAndCustomerUsername(vin, username);

//		Offer offer = offerService.getOfferByVinAndStatusApproved(vin);
//		fillRemainingPayments(offer, payments);

		return payments;
	}

	@Override
	public double getRemainingBalanceForOffer(String offerId) throws NotFoundRecordException {

		validateOfferId(offerId);

		Offer offer = offerService.getOfferByIdWithNoJoins(offerId);

		if (offer == null) {
			throw new NotFoundRecordException("Offer not found.");
		}

		double remaining = offer.getAmount() - (offer.getPaymentsMade() * offer.getMonthlyPayment());

		return remaining;
	}

	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

//	private void fillRemainingPayments(Offer offer, Set<Payment> payments) {
//		// Adding the car's remaining payments to the list
//		for (int i = offer.getPaymentsMade(); i < offer.getTotalPayments(); i++) {
//			Payment payment = new Payment(i + 1, null, 0.0, offer.getVin(), offer.getUsername());
//
//			payments.add(payment);
//		}
//	}

}
