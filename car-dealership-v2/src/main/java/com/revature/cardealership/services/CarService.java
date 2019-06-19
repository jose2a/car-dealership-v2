package com.revature.cardealership.services;

import java.util.Set;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.exceptions.PreexistingRecordException;
import com.revature.cardealership.model.Car;

public interface CarService {

	public boolean addCar(Car car) throws PreexistingRecordException;
	
	public boolean updateCar(Car car) throws NotFoundRecordException;

	public Set<Car> getAllCars();

	public boolean removeCar(String vin) throws NotFoundRecordException;

	public Car getCarByVin(String vin);

	public Set<Car> getCarsByCustomerUsername(String username);
	
	public Set<Car> getCarsInInvertory();

}
