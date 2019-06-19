package com.revature.cardealership.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarDAOImplTest {

	private static CarDAOImpl carDao;
	private static String vin;
	private static String username;
	private static Customer cust;
	private static Car car;

	@BeforeClass
	public static void init() {
		vin = RandomStringUtils.randomAlphanumeric(5);
		username = RandomStringUtils.randomAlphanumeric(6);

		CustomerDAOImpl customerDAO = new CustomerDAOImpl();
		cust = new Customer(username, "Random", "Random", "Random");
		customerDAO.addCustomer(cust);
	}

	@Before
	public void setUp() throws Exception {
		carDao = new CarDAOImpl();
		car = new Car(vin, "Mitsubishi", "Eclipse", 15000, false, true);
	}
	
	@AfterClass
	public static void tearDown() {
		carDao.removeCar(vin);
	}

	@Test
	public void a_getAllCars_ReturnsCarsWitActiveTrue() {
		Set<Car> allCars = carDao.getAllCars();

		for (Car car : allCars) {
			assertTrue(car.isActive());
		}
	}

	@Test
	public void b_addCar_ValidData_ShouldRetunsTrue() {
		boolean result = carDao.addCar(car);

		assertTrue(result);
	}

	@Test
	public void c_addCar_RepeatVin_ShouldRetunsFalse() {
		boolean result = carDao.addCar(car);

		assertFalse(result);
	}

	@Test
	public void d_updateCar_ValidVin_ReturnsTrue() {
		boolean result = carDao.addCar(car);
		car.setUsername(username);

		assertFalse(result);
	}

	public void e_updateCar_InvalidVin_ReturnsFalse() {
		car.setVin(RandomStringUtils.randomAlphanumeric(4));
		boolean result = carDao.addCar(car);

		assertFalse(result);
	}

	@Test
	public void f_RemoveCar_ValidVin_ReturnsTrue() {
		boolean result = carDao.removeCar(vin);

		assertTrue(result);
	}

	@Test
	public void g_RemoveCar_InvalidVin_ReturnsFalse() {
		boolean result = carDao.removeCar(vin);

		assertFalse(result);
	}

	@Test
	public void h_getCarsByCustomerUsername_ValidCustomer_ReturnsList() {
		car.setUsername(username);
		carDao.addCar(car);

		Set<Car> carsByCustomerUsername = carDao.getCarsByCustomerUsername(username);

		for (Car c : carsByCustomerUsername) {
			assertTrue(c.getUsername().equals(username));
		}
	}

	@Test
	public void i_getCarsByCustomerUsername_InvalidCustomer_ReturnsEmptyList() {

		Set<Car> carsByCustomerUsername = carDao.getCarsByCustomerUsername(username);

		assertEquals(0, carsByCustomerUsername.size());
	}

	@Test
	public void j_getCarByVin_ValidVin_ReturnsCar() {
		carDao.removeCar(vin);
		carDao.addCar(car);
		
		Car c = carDao.getCarByVin(vin);
		
		assertNotNull(c);
		
	}
	
	@Test
	public void k_getCarByVin_InvalidVin_ReturnsNull() {
		carDao.removeCar(vin);
		
		Car c = carDao.getCarByVin(vin);
		
		assertNull(c);
	}

	@Test
	public void l_getCarsInInvertory_ReturnsListCarIsSoldEqualsToFalse() {
		Set<Car> cars = carDao.getCarsInInvertory();

		for (Car c : cars) {
			assertFalse(c.isSold());
		}
	}

}
