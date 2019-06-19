package com.revature.cardealership.utils;

import com.revature.cardealership.services.CarService;
import com.revature.cardealership.services.CarServiceImpl;
import com.revature.cardealership.services.CustomerService;
import com.revature.cardealership.services.CustomerServiceImpl;
import com.revature.cardealership.services.OfferService;
import com.revature.cardealership.services.OfferServiceImpl;
import com.revature.cardealership.services.PaymentService;
import com.revature.cardealership.services.PaymentServiceImpl;
import com.revature.cardealership.services.UserService;
import com.revature.cardealership.services.UserServiceImpl;

public class ServiceUtilities {

	private static CarService carService;
	private static CustomerService customerService;
	private static OfferService offerService;
	private static PaymentService paymentService;
	private static UserService userService;

	public static CarService getCarService() {
		if (carService == null) {
			carService = new CarServiceImpl();
		}
		return carService;
	}

	public static CustomerService getCustomerService() {
		if (customerService == null) {
			customerService = new CustomerServiceImpl();
		}
		return customerService;
	}

	public static OfferService getOfferService() {
		if (offerService == null) {
			offerService = new OfferServiceImpl();
		}
		return offerService;
	}

	public static PaymentService getPaymentService() {
		if (paymentService == null) {
			paymentService = new PaymentServiceImpl();
		}
		return paymentService;
	}

	public static UserService getUserService() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}
}
