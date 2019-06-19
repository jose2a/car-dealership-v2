package com.revature.cardealership.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OfferDAOImplTest {

	private static String vin;
	private static String username;
	private static Customer cust;
	private static Car car;

	private static OfferDAOImpl offerDao;
	private static Offer offer;
	private static String offerId;

	@BeforeClass
	public static void init() {
		vin = RandomStringUtils.randomAlphanumeric(5);
		username = RandomStringUtils.randomAlphanumeric(6);

		CarDAOImpl carDao = new CarDAOImpl();
		car = new Car(vin, "Honda", "Accord", 17500, false, true);
		carDao.addCar(car);

		CustomerDAOImpl customerDAO = new CustomerDAOImpl();
		cust = new Customer(username, "Random", "Random", "Random");
		customerDAO.addCustomer(cust);

		offerId = RandomStringUtils.randomNumeric(4);
	}
	
	@AfterClass
	public static void tearDown() {
		CarDAOImpl carDao = new CarDAOImpl();
		carDao.removeCar(vin);
		offerDao.removeOffer(offerId);
	}

	@Before
	public void setUp() throws Exception {
		offerDao = new OfferDAOImpl();
		offer = new Offer(offerId, LocalDate.now(), 16000, OfferStatus.PENDING, username, vin);
	}

	@Test
	public void a_addOffer_ValidVinAndUsername_ReturnsTrue() {
		boolean result = offerDao.addOffer(offer);
		assertTrue(result);
	}

	@Test
	public void b_addOffer_InvalidVin_ReturnsFalse() {
		offer.setUsername(RandomStringUtils.randomNumeric(4));
		boolean result = offerDao.addOffer(offer);
		assertFalse(result);
	}

	@Test
	public void c_updateOffer_ValidOffer_ReturnsTrue() {
		offer.setUsername(username);
		boolean result = offerDao.updateOffer(offer);
		assertTrue(result);
	}

	@Test
	public void d_updateOffer_InvalidOffer_ReturnsFalse() {
		offer.setOfferId(RandomStringUtils.randomNumeric(4));
		
		boolean result = offerDao.updateOffer(offer);
		assertFalse(result);
	}

	@Test
	public void d_getOfferByOfferId_ValidOfferId_ReturnsOffer() {
		offer = offerDao.getOfferByOfferId(offerId);

		assertNotNull(offer);
	}

	@Test
	public void e_getOfferByOfferId_InvalidOfferId_ReturnsNull() {
		offer = offerDao.getOfferByOfferId(RandomStringUtils.randomNumeric(4));

		assertNull(offer);
	}

	@Test
	public void f_getOffersByStatusPending_ReturnsList() {
		Set<Offer> offers = offerDao.getOffersByStatus(OfferStatus.PENDING);

		for (Offer o : offers) {
			assertEquals(OfferStatus.PENDING, o.getStatus());
		}
	}

	@Test
	public void g_getOffersByStatusUnknown_ReturnsEmptyList() {
		Set<Offer> offers = offerDao.getOffersByStatus(OfferStatus.ALL);

		assertEquals(0, offers.size());
	}

	@Test
	public void h_getOfferByOfferIdWithNoJoins_ValidOfferId_ReturnsOffer() {
		offer = offerDao.getOfferByOfferIdWithNoJoins(offerId);

		assertNotNull(offer);
	}

	@Test
	public void i_getOfferByOfferIdWithNoJoins_InvalidOfferId_ReturnsNull() {
		offer = offerDao.getOfferByOfferIdWithNoJoins(RandomStringUtils.randomNumeric(4));

		assertNull(offer);
	}

	@Test
	public void j_getOfferByVinAndStatusApproved_ReturnsOffer() {
		offer.setStatus(OfferStatus.ACCEPTED);
		offerDao.updateOffer(offer);
		
		offer = offerDao.getOfferByOfferIdWithNoJoins(offerId);

		assertEquals(OfferStatus.ACCEPTED, offer.getStatus());
	}

}
