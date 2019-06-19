package com.revature.cardealership.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.cardealership.dao.CarDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.exceptions.PreexistingRecordException;
import com.revature.cardealership.model.Car;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {

	@Mock
	private static CarDAO carDao;
	@InjectMocks
	private static CarServiceImpl carService = new CarServiceImpl();

	private static Car car;
	private static String vin;
	private static Set<Car> cars;

	@Before
	public void setUp() throws Exception {

		vin = RandomStringUtils.randomAlphabetic(5);
		car = new Car(vin, "Ford", "Focus", 23000, false, true);

		cars = new HashSet<>();
		cars.add(new Car("1111111", "Toyota", "Corolla", 17000, false, true));
		cars.add(new Car("22222222222", "Ford", "Focus", 23000, false, true));
		cars.add(new Car("3333333", "Dodge", "Charger", 35000, false, true));
	}

	@Test
	public void addCar_CarIsNull_ReturnsFalse() throws PreexistingRecordException {

		boolean result = carService.addCar(null);

		assertFalse(result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addCar_EmptyFields_ThrowsIllegalArgumentException() throws PreexistingRecordException {
		carService.addCar(new Car());
	}

	@Test(expected = PreexistingRecordException.class)
	public void addCar_CarWithExistingVIN_ThrowsPreexistingRecordException() throws PreexistingRecordException {

		when(carDao.getCarByVin(any(String.class))).thenReturn(car);

		boolean result = carService.addCar(car);

		assertEquals(false, result);

		verify(carDao, times(1)).getCarByVin(vin);
	}

	@Test
	public void addCar_CarIsValid_ReturnsTrue() throws PreexistingRecordException {
		when(carDao.addCar(any(Car.class))).thenReturn(true);
		when(carDao.getCarByVin(any(String.class))).thenReturn(null);

		boolean result = carService.addCar(car);

		assertEquals(true, result);

		car.setUsername("9999999");
		result = carService.addCar(car);

		assertEquals(true, result);

		verify(carDao, times(2)).getCarByVin(vin);
		verify(carDao, times(2)).addCar(car);
	}

	@Test
	public void getAllCars_ReturnsList() {
		when(carDao.getAllCars()).thenReturn(cars);

		assertEquals(cars, carService.getAllCars());
		
		verify(carDao, times(1)).getAllCars();
	}

	@Test
	public void removeCar_ValidVin_RetunsTrue() throws NotFoundRecordException {
		when(carDao.updateCar(any(Car.class))).thenReturn(true);
		when(carDao.getCarByVin(vin)).thenReturn(car);
		
		boolean removedCar = carService.removeCar(vin);

		assertTrue(removedCar);
		
		verify(carDao, times(1)).getCarByVin(vin);
		verify(carDao, times(1)).updateCar(car);
	}

	@Test(expected = NotFoundRecordException.class)
	public void removeCar_InvalidVin_RetunsFalse() throws NotFoundRecordException {	
		when(carDao.getCarByVin(vin)).thenReturn(null);
		
		carService.removeCar(vin);
		
		verify(carDao, times(1)).getCarByVin(vin);
	}

	@Test(expected = IllegalArgumentException.class)
	public void removeCar_VinIsNull_ThrowsIllegalArgumentException() throws NotFoundRecordException {
		carService.removeCar(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCarsByCustomerUsername_UsernameIsNull_ThrowsIllegalArgumentException() {
		carService.getCarsByCustomerUsername(null);
	}

	@Test
	public void getCarsByCustomerUsername_ReturnList() {
		when(carDao.getCarsByCustomerUsername("cust1")).thenReturn(cars);

		assertEquals(cars, carService.getCarsByCustomerUsername("cust1"));
		
		verify(carDao, times(1)).getCarsByCustomerUsername("cust1");
	}

	@Test
	public void getCarByVin_ValidVin_ReturnsCar() {
		when(carDao.getCarByVin(vin)).thenReturn(car);
		
		carService.getCarByVin(vin);
		
		verify(carDao, times(1)).getCarByVin(vin);
	}
	
	@Test
	public void getCarByVin_invalidVin_ReturnsNull() {
		when(carDao.getCarByVin(vin)).thenReturn(null);
		
		carService.getCarByVin(vin);
		
		verify(carDao, times(1)).getCarByVin(vin);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getCarByVin_VinIsNull_ThrowsIllegalArgumentException() {
		carService.getCarByVin(null);
	}

}
