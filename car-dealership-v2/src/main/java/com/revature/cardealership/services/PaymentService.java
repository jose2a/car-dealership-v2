package com.revature.cardealership.services;

import java.util.Set;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Payment;

public interface PaymentService {

	public boolean makePayment(String vin) throws NotFoundRecordException;

	Set<Payment> getAllPayments();

	Set<Payment> getAllPaymentsByCustomerUsername(String username) throws NotFoundRecordException;

	Set<Payment> getPaymentsByVin(String vin) throws NotFoundRecordException;

	Set<Payment> getPaymentsForCarVinAndCustomerUsername(String vin, String username) throws NotFoundRecordException;
	
	double getRemainingBalanceForOffer(String offerId) throws NotFoundRecordException;
}
