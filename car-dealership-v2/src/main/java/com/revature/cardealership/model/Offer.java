package com.revature.cardealership.model;

import java.time.LocalDate;

public class Offer {

	private String offerId;

	private LocalDate signedDate; // date when the offer was made
	private double amount; // car's sold price
	private int totalPayments; // Total number of payments
	private int paymentsMade; // Number of payments made
	private double monthlyPayment; // amount to pay every month
	private OfferStatus status; // Status of the offer (ACCEPTED, REJECTED, PENDING)

	private String username;
	private Customer customer; // Customer who made the offer

	private String vin;
	private Car car; // Car the offer was made for

	public Offer() {

	}

	public Offer(String offerId, LocalDate signedDate, double amount, OfferStatus status, String username, String vin) {
		super();
		this.offerId = offerId;
		this.signedDate = signedDate;
		this.amount = amount;
		this.totalPayments = 0;
		this.paymentsMade = 0;
		this.monthlyPayment = 0.0;
		this.status = status;
		this.username = username;
		this.vin = vin;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public LocalDate getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(LocalDate signedDate) {
		this.signedDate = signedDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTotalPayments() {
		return totalPayments;
	}

	public void setTotalPayments(int totalPayments) {
		this.totalPayments = totalPayments;
	}

	public int getPaymentsMade() {
		return paymentsMade;
	}

	public void setPaymentsMade(int paymentsMade) {
		this.paymentsMade = paymentsMade;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		result = prime * result + ((signedDate == null) ? 0 : signedDate.hashCode());
		result = prime * result + totalPayments;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		if (signedDate == null) {
			if (other.signedDate != null)
				return false;
		} else if (!signedDate.equals(other.signedDate))
			return false;
		if (totalPayments != other.totalPayments)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Offer No: " + offerId + "\nCustomer: " + customer + "\nCar: " + car + "\nSigned On: " + signedDate
				+ "\nAmount Offered: " + amount + "\nStatus: " + status.toString();
	}

	public String toSingleLineString() {
		return "Offer No: " + offerId + ", Customer: " + customer + ", Car: " + car.toSingleLineString()
				+ ", Signed On: " + signedDate + ", Amount Offered: " + amount + ", Status: " + status.toString();
	}

}
