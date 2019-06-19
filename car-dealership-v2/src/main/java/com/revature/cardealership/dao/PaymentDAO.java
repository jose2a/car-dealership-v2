package com.revature.cardealership.dao;

import java.util.Set;

import com.revature.cardealership.model.Payment;

public interface PaymentDAO {

	boolean addPayment(Payment payment);

	boolean updatePayment(Payment payment);

	Set<Payment> getAllPayments();

	Set<Payment> getAllPaymentsByCustomerUsername(String username);

	Set<Payment> getPaymentsByVin(String vin);

	Set<Payment> getPaymentsByCarVinAndCustomerUsername(String vin, String username);

}
