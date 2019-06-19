package com.revature.cardealership.services;

import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;

public abstract class BaseService {

	protected void validateVin(String vin) {
		if (vin == null || vin.isEmpty()) {
			throw new IllegalArgumentException("Vin number should not be empty.");
		}
	}

	protected void validateUsername(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Please insert a valid customer username.");
		}
	}

	protected void validateOfferId(String offerId) {
		if (offerId == null || offerId.isEmpty()) {
			throw new IllegalArgumentException("Offer number should not be empty.");
		}
	}

	protected void validateCar(Car car) {
		validateVin(car.getVin());

		if (car.getMake() == null || car.getMake().isEmpty()) {
			throw new IllegalArgumentException("Make number should not be empty");
		}

		if (car.getModel() == null || car.getModel().isEmpty()) {
			throw new IllegalArgumentException("Model number should not be empty");
		}
	}

	protected void validateCustomer(Customer customer) {
		validateUsername(customer.getUsername());

		if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Password shoud not be empty");
		}

		if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
			throw new IllegalArgumentException("Fist Name shoud not be empty");
		}

		if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
			throw new IllegalArgumentException("Last Name shoud not be empty");
		}
	}

	protected void validateUserLoginInput(String username, String password) {
		if ((username == null || username.isEmpty()) || (password == null || password.isEmpty())) {
			throw new IllegalArgumentException("Username and password should not be empty.");
		}
	}

}
