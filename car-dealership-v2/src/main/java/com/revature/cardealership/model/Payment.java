package com.revature.cardealership.model;

import java.time.LocalDate;

public class Payment {

	private Integer paymentNo;

	private LocalDate paidDate;
	private double amountPaid;

	private String vin;
	private Car car;

	private String offerId;
	private Offer offer;

	public Payment() {
		super();
	}

	public Payment(Integer paymentNo, LocalDate paidDate, double amountPaid, String vin, String offerId) {
		super();
		this.paymentNo = paymentNo;
		this.paidDate = paidDate;
		this.amountPaid = amountPaid;
		this.vin = vin;
		this.offerId = offerId;
	}

	public Integer getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(Integer paymentNo) {
		this.paymentNo = paymentNo;
	}

	public LocalDate getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(LocalDate paidDate) {
		this.paidDate = paidDate;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
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

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String contractId) {
		this.offerId = contractId;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountPaid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		result = prime * result + ((paidDate == null) ? 0 : paidDate.hashCode());
		result = prime * result + ((paymentNo == null) ? 0 : paymentNo.hashCode());
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
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
		Payment other = (Payment) obj;
		if (Double.doubleToLongBits(amountPaid) != Double.doubleToLongBits(other.amountPaid))
			return false;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		if (paidDate == null) {
			if (other.paidDate != null)
				return false;
		} else if (!paidDate.equals(other.paidDate))
			return false;
		if (paymentNo == null) {
			if (other.paymentNo != null)
				return false;
		} else if (!paymentNo.equals(other.paymentNo))
			return false;
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payment No: " + paymentNo + ", Paid On: " + (paidDate == null ? "--------" : paidDate) + ", Amount: "
				+ String.format("%1$,.2f", amountPaid);
	}

}
