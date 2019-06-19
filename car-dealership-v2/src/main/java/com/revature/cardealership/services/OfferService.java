package com.revature.cardealership.services;

import java.util.Set;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Offer;

public interface OfferService {
	
	boolean updateOffer(Offer offer);

	boolean makeAnOffer(String username, String carVin, double amount) throws NotFoundRecordException;

	void acceptOffer(String offerId, int numOfMonths) throws NotFoundRecordException;

	void rejectOffer(String offerId) throws NotFoundRecordException;
	
	Offer getOfferByIdWithNoJoins(String offerId);

	Set<Offer> getPendingOffers();

	Set<Offer> getAllOffers();

	Offer getOfferByVinAndStatusApproved(String vin);
}
