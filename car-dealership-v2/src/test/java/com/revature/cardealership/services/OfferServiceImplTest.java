package com.revature.cardealership.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.revature.cardealership.dao.OfferDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RandomStringUtils.class, Clock.class, Offer.class })
public class OfferServiceImplTest {

	@Mock
	private static OfferDAO offerDao;
	@Mock
	private static CarService carService;
	@Mock
	private static CustomerService customerService;

	private static OfferServiceImpl offerService;

	private static Car car;
	private static String vin = "1111111";
	private static Customer customer;
	private static String username = "peterp";
	private static Offer offer;
	private static String offerId;
	private static Set<Offer> offerings;

	private static Clock clock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		offerService = new OfferServiceImpl();
		offerService.setOfferDao(offerDao);
		offerService.setCarService(carService);
		offerService.setCustomerService(customerService);

		car = new Car(vin, "Toyota", "Corolla", 17000, false, true);
		customer = new Customer(username, "s3cret", "Peter", "Parker");

		clock = Clock.systemDefaultZone();

		offerId = RandomStringUtils.randomNumeric(4);
		offer = new Offer(offerId, LocalDate.now(clock), 14000, OfferStatus.PENDING, username, vin);

		offerings = new HashSet<Offer>();
		offerings.add(new Offer());
		offerings.add(new Offer());
		offerings.add(offer);
	}

	@Test
	public void updateOffer_DataIsValid_ReturnsTrue() {
		when(offerDao.updateOffer(offer)).thenReturn(true);

		boolean res = offerService.updateOffer(offer);

		assertTrue(res);

		verify(offerDao, times(1)).updateOffer(offer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateOffer_DataIsNotValid_ThrowsIllegalArgumentException() {
		offer.setUsername(null);
		offerService.updateOffer(offer);
	}

	@Test
	public void getOfferByIdWithNoJoins_ValidOfferId_ReturnOffer() {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(offer);

		Offer res = offerService.getOfferByIdWithNoJoins(offerId);

		assertNotNull(res);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
	}

	@Test
	public void getOfferByIdWithNoJoins_NotValidOfferId_ReturnNull() {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(null);

		Offer res = offerService.getOfferByIdWithNoJoins(offerId);

		assertNull(res);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getOfferByIdWithNoJoins_NotValidOfferId_ThrowsIllegalArgumentException() {

		offerService.getOfferByIdWithNoJoins(null);
	}

	@Test
	public void offerByVinAndStatusApproved_ValidVin_ReturnsOffer() {
		when(offerDao.getOfferByVinAndStatusApproved(vin)).thenReturn(offer);

		Offer res = offerService.getOfferByVinAndStatusApproved(vin);

		assertNotNull(res);

		verify(offerDao).getOfferByVinAndStatusApproved(vin);
	}

	@Test
	public void offerByVinAndStatusApproved_NotValidVin_ReturnsNull() {
		when(offerDao.getOfferByVinAndStatusApproved(vin)).thenReturn(null);

		Offer res = offerService.getOfferByVinAndStatusApproved(vin);

		assertNull(res);

		verify(offerDao).getOfferByVinAndStatusApproved(vin);
	}

	@Test(expected = IllegalArgumentException.class)
	public void offerByVinAndStatusApproved_NullVin_ThrowsIllegalArgumentException() {

		offerService.getOfferByVinAndStatusApproved(null);
	}

	@Test
	public void makeAnOffer_ValidData_ReturnsTrue() throws Exception {
		when(offerDao.addOffer(offer)).thenReturn(true);
		when(carService.getCarByVin(vin)).thenReturn(car);
		when(customerService.getCustomerByUsername(username)).thenReturn(customer);

		PowerMockito.mockStatic(RandomStringUtils.class);
		Mockito.when(RandomStringUtils.randomNumeric(4)).thenReturn(offerId);
		PowerMockito.mockStatic(Clock.class);
		Mockito.when(Clock.systemDefaultZone()).thenReturn(clock);

		boolean res = offerService.makeAnOffer(username, vin, 14000.0);

		assertTrue(res);

		verify(offerDao).addOffer(offer);
		verify(carService).getCarByVin(vin);
		verify(customerService).getCustomerByUsername(username);
	}

	@Test(expected = IllegalArgumentException.class)
	public void makeAnOffer_NotValidVin_ThrowsIllegalArgumentException() throws Exception {

		offerService.makeAnOffer(username, null, 14000.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void makeAnOffer_NotValidUsername_ThrowsIllegalArgumentException() throws Exception {

		offerService.makeAnOffer(null, vin, 14000.0);
	}

	@Test(expected = NotFoundRecordException.class)
	public void makeAnOffer_NullCustomer_ThrowsNotFoundRecordException() throws Exception {
		when(carService.getCarByVin(vin)).thenReturn(null);
		when(customerService.getCustomerByUsername(username)).thenReturn(customer);

		offerService.makeAnOffer(username, vin, 14000.0);

		verify(customerService).getCustomerByUsername(username);
	}

	@Test(expected = NotFoundRecordException.class)
	public void makeAnOffer_NullCar_ReturnsTrue() throws Exception {
		when(carService.getCarByVin(vin)).thenReturn(car);
		when(customerService.getCustomerByUsername(username)).thenReturn(null);

		offerService.makeAnOffer(username, vin, 14000.0);

		verify(customerService).getCustomerByUsername(username);
	}

	@Test(expected = IllegalArgumentException.class)
	public void acceptOffer_NotValidOfferId_ThrowsIllegalArgumentException() throws NotFoundRecordException {

		offerService.acceptOffer(null, 36);
	}

	@Test(expected = NotFoundRecordException.class)
	public void acceptOffer_OfferNotFound_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(null);

		offerService.acceptOffer(offerId, 36);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
	}

	@Test(expected = NotFoundRecordException.class)
	public void acceptOffer_CarNotFound_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(offer);
		when(carService.getCarByVin(vin)).thenReturn(null);

		offerService.acceptOffer(offerId, 36);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
		verify(carService).getCarByVin(vin);
	}

	@Test(expected = NotFoundRecordException.class)
	public void acceptOffer_CarNotUpdate_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(offer);
		when(carService.getCarByVin(vin)).thenReturn(null);
		when(carService.updateCar(car)).thenThrow(NotFoundRecordException.class);

		offerService.acceptOffer(offerId, 36);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
		verify(carService).getCarByVin(vin);
		verify(carService).updateCar(car);
	}

	@Test
	public void acceptOffer_ValidOfferId_NotThrowsExceptions() throws NotFoundRecordException {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(offer);
		when(offerDao.updateOffer(offer)).thenReturn(true);
		when(carService.getCarByVin(vin)).thenReturn(car);
		when(carService.updateCar(car)).thenReturn(true);

		offerService.acceptOffer(offerId, 36);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
		verify(offerDao).updateOffer(offer);
		verify(carService).getCarByVin(vin);
		verify(carService).updateCar(car);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectOffer_NotValidOfferId_ThrowsIllegalArgumentException() throws NotFoundRecordException {
		offerService.rejectOffer(null);
	}

	@Test(expected = NotFoundRecordException.class)
	public void rejectOffer_OfferNotFound_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(null);

		offerService.rejectOffer(offerId);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectOffer_OfferAccepted_ThrowsIllegalArgumentException() throws NotFoundRecordException {
		offer.setStatus(OfferStatus.ACCEPTED);
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(offer);

		offerService.rejectOffer(offerId);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
	}

	@Test
	public void rejectOffer_ValidOfferId_NotThrowsExceptions() throws NotFoundRecordException {
		when(offerDao.getOfferByOfferIdWithNoJoins(offerId)).thenReturn(offer);
		when(offerDao.updateOffer(offer)).thenReturn(true);

		offerService.rejectOffer(offerId);

		verify(offerDao).getOfferByOfferIdWithNoJoins(offerId);
		when(offerDao.updateOffer(offer)).thenReturn(true);
	}

	@Test
	public void getPendingOffers_ReturnsList() {
		when(offerDao.getOffersByStatus(OfferStatus.PENDING)).thenReturn(offerings);
		
		offerService.getPendingOffers();

		verify(offerDao).getOffersByStatus(OfferStatus.PENDING);
	}

	@Test
	public void getAllOffers_ReturnsList() {
		when(offerDao.getAllOffers()).thenReturn(offerings);
		
		offerService.getAllOffers();

		verify(offerDao).getAllOffers();
	}

}
