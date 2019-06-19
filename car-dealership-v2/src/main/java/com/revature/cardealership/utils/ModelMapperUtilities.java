package com.revature.cardealership.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;
import com.revature.cardealership.model.Payment;
import com.revature.cardealership.model.User;

public class ModelMapperUtilities {

	public static void mapResultSetToCar(ResultSet rs, Car car) throws SQLException {
		car.setVin(rs.getString("vin"));
		car.setMake(rs.getString("make"));
		car.setModel(rs.getString("model"));
		car.setPrice(rs.getDouble("price"));
		car.setSold(rs.getBoolean("is_sold"));
		car.setActive(rs.getBoolean("active"));
		car.setUsername(rs.getString("username"));
	}

	public static void mapResultSetToCustomer(ResultSet rs, Customer customer) throws SQLException {
		customer.setUsername(rs.getString("username"));
		customer.setPassword(rs.getString("password"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
	}

	public static void mapResultSetToOffer(ResultSet rs, Offer offer) throws SQLException {
		offer.setOfferId(rs.getString("offer_id"));
		offer.setSignedDate(rs.getDate("signed_date").toLocalDate());
		offer.setAmount(rs.getDouble("amount"));
		offer.setTotalPayments(rs.getInt("total_payment"));
		offer.setPaymentsMade(rs.getInt("payments_made"));
		offer.setMonthlyPayment(rs.getDouble("monthly_payment"));
		offer.setUsername(rs.getString("username"));
		offer.setVin(rs.getString("vin"));
		offer.setStatus(OfferStatus.valueOf(rs.getInt("status_id")));
	}

	public static void mapResulsetToOfferWithCarAndCustomer(ResultSet rs, Offer offer) throws SQLException {
		mapResultSetToOffer(rs, offer);

		Car car = new Car();
		mapResultSetToCar(rs, car);
		offer.setCar(car);

		Customer customer = new Customer();
		mapResultSetToCustomer(rs, customer);
		offer.setCustomer(customer);
	}

	public static void mapResultSetToPayment(ResultSet rs, Payment payment) throws SQLException {
		payment.setPaymentNo(rs.getInt("payment_id"));
		payment.setPaidDate(rs.getDate("paid_date").toLocalDate());
		payment.setAmountPaid(rs.getDouble("amount_paid"));
		payment.setVin(rs.getString("vin"));
	}
	
	public static void mapResultSetToUser(ResultSet rs, User user) throws SQLException {
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setType(rs.getString("type"));
	}

}
