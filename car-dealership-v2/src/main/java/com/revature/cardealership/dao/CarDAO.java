package com.revature.cardealership.dao;

import java.util.Set;

import com.revature.cardealership.model.Car;

public interface CarDAO {

	public boolean addCar(Car car);
	
	public boolean updateCar(Car car);

	public boolean removeCar(String vin);

	public Set<Car> getCarsByCustomerUsername(String username);
	
	public Set<Car> getCarsInInvertory();
	
	public Car getCarByVin(String vin);

	public Set<Car> getAllCars();
}
