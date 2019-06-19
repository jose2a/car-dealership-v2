package com.revature.cardealership.utils;

import com.revature.cardealership.dao.CarDAO;
import com.revature.cardealership.dao.CarDAOImpl;
import com.revature.cardealership.dao.CustomerDAO;
import com.revature.cardealership.dao.CustomerDAOImpl;
import com.revature.cardealership.dao.OfferDAO;
import com.revature.cardealership.dao.OfferDAOImpl;
import com.revature.cardealership.dao.PaymentDAO;
import com.revature.cardealership.dao.PaymentDAOImpl;
import com.revature.cardealership.dao.UserDAO;
import com.revature.cardealership.dao.UserDAOImpl;

public class DAOUtilities {

	private static CarDAO carDAO;
	private static CustomerDAO customerDAO;
	private static OfferDAO offerDAO;
	private static PaymentDAO paymentDAO;
	private static UserDAO userDAO;

	public static CarDAO getCarDAO() {
		if (carDAO == null) {
			carDAO = new CarDAOImpl();
		}
		return carDAO;
	}

	public static CustomerDAO getCustomerDAO() {
		if (customerDAO == null) {
			customerDAO = new CustomerDAOImpl();
		}
		return customerDAO;
	}

	public static OfferDAO getOfferDAO() {
		if (offerDAO == null) {
			offerDAO = new OfferDAOImpl();
		}
		return offerDAO;
	}

	public static PaymentDAO getPaymentDAO() {
		if (paymentDAO == null) {
			paymentDAO = new PaymentDAOImpl();
		}
		return paymentDAO;
	}

	public static UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAOImpl();
		}
		return userDAO;
	}
}
