package com.revature.cardealership.dao;

import java.util.Set;

import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;

public interface OfferDAO {

	boolean addOffer(Offer offer);
	boolean updateOffer(Offer offer);
	boolean removeOffer(String offerId);

	Offer getOfferByOfferId(String offerId);
	Offer getOfferByOfferIdWithNoJoins(String offerId);
	Offer getOfferByVinAndUsernameWithNoJoins(String vin, String username);
	
	Set<Offer> getOffersByStatus(OfferStatus offerStatus);
	Set<Offer> getAllOffers();
	Offer getOfferByVinAndStatusApproved(String vin);
}
