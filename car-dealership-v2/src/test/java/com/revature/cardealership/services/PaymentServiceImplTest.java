package com.revature.cardealership.services;

import static org.junit.Assert.assertTrue;
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

import com.revature.cardealership.dao.PaymentDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;
import com.revature.cardealership.model.Payment;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RandomStringUtils.class, Clock.class, Payment.class })
public class PaymentServiceImplTest {

	@Mock
	private static PaymentDAO paymentDAO;
	@Mock
	private static CarService carService;
	@Mock
	private static CustomerService customerService;
	@Mock
	private static OfferService offerService;

	private static PaymentServiceImpl paymentService;

	private static Car car;
	private static String vin = "1111111";

	private static Customer customer;
	private static String username = "peterp";

	private static Offer offer;
	private static String offerId = "1234";

	private static Payment payment;
	private static Integer paymentId = 12345;
	private static Set<Payment> payments;

	private static Clock clock = Clock.systemDefaultZone();;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		paymentService = new PaymentServiceImpl();
		paymentService.setPaymentDAO(paymentDAO);
		paymentService.setCarService(carService);
		paymentService.setCustomerService(customerService);
		paymentService.setOfferService(offerService);

		car = new Car(vin, "Toyota", "Corolla", 17000, false, true);
		customer = new Customer(username, "s3cret", "Peter", "Parker");

		offer = new Offer(offerId, LocalDate.now(clock), 14000, OfferStatus.ACCEPTED, username, vin);
		offer.setTotalPayments(36);
		offer.setMonthlyPayment(450.00);

		payment = new Payment(paymentId, LocalDate.now(clock), 450.00, vin, offerId);

		payments = new HashSet<Payment>();
		payments.add(new Payment(paymentId + 2, LocalDate.now().minusMonths(2), 450.00, vin, offerId));
		payments.add(new Payment(paymentId + 1, LocalDate.now().minusMonths(1), 450.00, vin, offerId));
		payments.add(payment);

	}

	@Test(expected = IllegalArgumentException.class)
	public void makePayment_VinIsNull_ThrowsIllegalArgumentException() throws NotFoundRecordException {
		paymentService.makePayment(null);
	}

	@Test(expected = NotFoundRecordException.class)
	public void makePayment_OfferIsNull_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(offerService.getOfferByVinAndStatusApproved(vin)).thenReturn(null);
		paymentService.makePayment(vin);

		verify(offerService).getOfferByVinAndStatusApproved(vin);
	}

	@Test
	public void makePayment_PaymentNotSaved_ReturnFalse() throws NotFoundRecordException {
		when(offerService.getOfferByVinAndStatusApproved(vin)).thenReturn(offer);
		when(paymentDAO.addPayment(payment)).thenReturn(false);

		PowerMockito.mockStatic(RandomStringUtils.class);
		Mockito.when(RandomStringUtils.randomNumeric(4)).thenReturn(paymentId.toString());
		PowerMockito.mockStatic(Clock.class);
		Mockito.when(Clock.systemDefaultZone()).thenReturn(clock);

		paymentService.makePayment(vin);

		verify(offerService).getOfferByVinAndStatusApproved(vin);
		verify(paymentDAO).addPayment(payment);
	}

	@Test
	public void makePayment_OfferIdValid_NotErrors() throws NotFoundRecordException {
		when(offerService.getOfferByVinAndStatusApproved(vin)).thenReturn(offer);
		when(paymentDAO.addPayment(payment)).thenReturn(true);
		when(offerService.updateOffer(offer)).thenReturn(true);

		PowerMockito.mockStatic(RandomStringUtils.class);
		Mockito.when(RandomStringUtils.randomNumeric(4)).thenReturn(paymentId.toString());
		PowerMockito.mockStatic(Clock.class);
		Mockito.when(Clock.systemDefaultZone()).thenReturn(clock);

		boolean res = paymentService.makePayment(vin);

		assertTrue(res);

		verify(offerService).getOfferByVinAndStatusApproved(vin);
		verify(paymentDAO).addPayment(payment);
		verify(offerService).updateOffer(offer);
	}

	@Test
	public void getAllPayments_ReturnsSet() {
		when(paymentDAO.getAllPayments()).thenReturn(payments);

		paymentService.getAllPayments();

		verify(paymentDAO).getAllPayments();
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllPaymentsByCustomerUsername_NullUsername_ThrowsIllegalArgumentException()
			throws NotFoundRecordException {

		paymentService.getAllPaymentsByCustomerUsername(null);

	}

	@Test(expected = NotFoundRecordException.class)
	public void getAllPaymentsByCustomerUsername_CustomerNotFound_ThrowsNotFoundRecordException()
			throws NotFoundRecordException {
		when(customerService.getCustomerByUsername(username)).thenReturn(null);

		paymentService.getAllPaymentsByCustomerUsername(username);

	}

	@Test
	public void getAllPaymentsByCustomerUsername_ReturnsSet() throws NotFoundRecordException {
		when(paymentDAO.getAllPaymentsByCustomerUsername(username)).thenReturn(payments);
		when(customerService.getCustomerByUsername(username)).thenReturn(customer);

		paymentService.getAllPaymentsByCustomerUsername(username);

		verify(paymentDAO).getAllPaymentsByCustomerUsername(username);
		verify(customerService).getCustomerByUsername(username);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPaymentsByVin_VinIsNull_ThrowsIllegalArgumentException() throws NotFoundRecordException {
		paymentService.getPaymentsByVin(null);
	}

	@Test(expected = NotFoundRecordException.class)
	public void getPaymentsByVin_CustomerNotFound_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(carService.getCarByVin(vin)).thenReturn(null);

		paymentService.getPaymentsByVin(username);

		verify(carService).getCarByVin(vin);
	}

	@Test
	public void getPaymentsByVin_VinValid_ReturnsSet() throws NotFoundRecordException {
		when(carService.getCarByVin(vin)).thenReturn(car);
		when(paymentDAO.getPaymentsByVin(vin)).thenReturn(payments);
		when(offerService.getOfferByVinAndStatusApproved(vin)).thenReturn(offer);

		paymentService.getPaymentsByVin(vin);

		verify(carService).getCarByVin(vin);
		verify(paymentDAO).getPaymentsByVin(vin);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRemainingBalanceForOffer_OfferIdNull_ThrowsIllegalArgumentException()
			throws NotFoundRecordException {
		paymentService.getRemainingBalanceForOffer(null);
	}

	@Test(expected = NotFoundRecordException.class)
	public void getRemainingBalanceForOffer_OfferIsNull_ThrowsNotFoundRecordException() throws NotFoundRecordException {
		when(offerService.getOfferByIdWithNoJoins(offerId)).thenReturn(null);

		paymentService.getRemainingBalanceForOffer(offerId);

		verify(offerService).getOfferByIdWithNoJoins(offerId);
	}

	@Test
	public void getRemainingBalanceForOffer_OfferIdValid_ReturnsValue() throws NotFoundRecordException {
		when(offerService.getOfferByIdWithNoJoins(offerId)).thenReturn(offer);

		paymentService.getRemainingBalanceForOffer(offerId);

		verify(offerService).getOfferByIdWithNoJoins(offerId);
	}

}
