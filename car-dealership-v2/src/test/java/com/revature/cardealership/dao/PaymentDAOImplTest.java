package com.revature.cardealership.dao;

import static org.junit.Assert.*;

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
import com.revature.cardealership.model.Payment;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentDAOImplTest {

	private static String vin;
	private static String username;
	private static Customer cust;
	private static Car car;
	private static Offer offer;
	private static String offerId;

	private static PaymentDAOImpl paymentDao;
	private static int paymentNo;
	private static Payment payment;

	@BeforeClass
	public static void init() {
		vin = RandomStringUtils.randomAlphanumeric(5);
		username = RandomStringUtils.randomAlphanumeric(6);
		offerId = RandomStringUtils.randomNumeric(4);

		CarDAOImpl carDao = new CarDAOImpl();
		car = new Car(vin, "Honda", "Accord", 17500, false, true);
		carDao.addCar(car);

		CustomerDAOImpl customerDAO = new CustomerDAOImpl();
		cust = new Customer(username, "Random", "Random", "Random");
		customerDAO.addCustomer(cust);

		car.setUsername(username);
		carDao.updateCar(car);

		OfferDAOImpl offerDao = new OfferDAOImpl();
		offer = new Offer(offerId, LocalDate.now(), 16000, OfferStatus.ACCEPTED, username, vin);
		offerDao.addOffer(offer);

		paymentNo = Integer.parseInt(RandomStringUtils.randomNumeric(4));

	}

	@Before
	public void setUp() throws Exception {
		paymentDao = new PaymentDAOImpl();

		payment = new Payment(paymentNo, LocalDate.now(), 450, vin, offerId);
	}

	@AfterClass
	public static void tearDown() {
		CarDAOImpl carDao = new CarDAOImpl();
		carDao.removeCar(vin);

		OfferDAOImpl offerDao = new OfferDAOImpl();
		offerDao.removeOffer(offerId);
	}

	@Test
	public void a_addPayment_ValidPayment_ReturnsTrue() {
		boolean res = paymentDao.addPayment(payment);

		assertTrue(res);
	}

	@Test
	public void b_addPayment_InvalidPayment_ReturnsFalse() {
		payment.setVin("999999999");
		boolean res = paymentDao.addPayment(payment);

		assertFalse(res);
	}

	@Test
	public void c_getAllPayments_ReturnsList() {
		Set<Payment> payments = paymentDao.getAllPayments();

		assertTrue(payments.size() > 0);

	}

	@Test
	public void d_getAllPaymentsByCustomerUsername_ValidUsername_ReturnList() {
		Set<Payment> payments = paymentDao.getAllPaymentsByCustomerUsername(username);

		assertTrue(payments.size() > 0);
	}

	@Test
	public void e_getAllPaymentsByCustomerUsername_InvalidUsername_ReturnEmptyList() {
		Set<Payment> payments = paymentDao.getAllPaymentsByCustomerUsername("99999999");

		assertTrue(payments.size() == 0);
	}

	@Test
	public void f_getPaymentsByVin_ValidVin_ReturnsList() {
		Set<Payment> payments = paymentDao.getPaymentsByVin(vin);

		assertTrue(payments.size() > 0);
	}

	@Test
	public void g_getPaymentsByVin_InvalidVin_ReturnsList() {
		Set<Payment> payments = paymentDao.getPaymentsByVin("999999");

		assertTrue(payments.size() == 0);
	}

	@Test
	public void h_getPaymentsByCarVinAndCustomerUsername_ValidInfo_ReturnsList() {

		Set<Payment> payments = paymentDao.getPaymentsByCarVinAndCustomerUsername(vin, username);

		assertTrue(payments.size() > 0);
	}

	@Test
	public void i_getPaymentsByCarVinAndCustomerUsername_InvalidInfo_ReturnsEmptyList() {
		Set<Payment> payments = paymentDao.getPaymentsByCarVinAndCustomerUsername("99999", username);

		assertTrue(payments.size() == 0);
	}

}
