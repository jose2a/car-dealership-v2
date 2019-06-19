package com.revature.cardealership.services;

import java.util.Set;

import com.revature.cardealership.dao.CarDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.exceptions.PreexistingRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.utils.DAOUtilities;
import com.revature.cardealership.utils.LogUtilities;

public class CarServiceImpl extends BaseService implements CarService {

	private CarDAO carDao;

	public CarServiceImpl() {
		carDao = DAOUtilities.getCarDAO();
	}

	@Override
	public boolean addCar(Car car) throws PreexistingRecordException {
		if (car != null) {

			validateCar(car);

			if (!isCarAdded(car)) {

				return carDao.addCar(car);
			}

			LogUtilities.trace("Car with the same VIN already in the system.");
			throw new PreexistingRecordException("Car with the same VIN already in the system.");
		}

		return false;
	}

	@Override
	public boolean updateCar(Car car) throws NotFoundRecordException {
		validateCar(car);

		return carDao.updateCar(car);
	}

	@Override
	public Set<Car> getAllCars() {
		return carDao.getAllCars();
	}

	@Override
	public boolean removeCar(String vin) throws NotFoundRecordException, IllegalArgumentException {
		validateVin(vin);

		Car car = carDao.getCarByVin(vin);

		if (car == null) {
			LogUtilities.trace("Car was not found.");
			throw new NotFoundRecordException("Car was not found.");
		}

		car.setActive(false);

		return carDao.updateCar(car);
	}

	@Override
	public Set<Car> getCarsByCustomerUsername(String username) {
		validateUsername(username);

		return carDao.getCarsByCustomerUsername(username);
	}

	@Override
	public Car getCarByVin(String vin) {
		validateVin(vin);

		return carDao.getCarByVin(vin);
	}

	@Override
	public Set<Car> getCarsInInvertory() {
		return carDao.getCarsInInvertory();
	}

	private boolean isCarAdded(Car car) {
		Car c = carDao.getCarByVin(car.getVin());

		if (c != null) {
			return true;
		}

		return false;
	}

	// Setting CarDAO
	public void setCarDao(CarDAO carDao) {
		this.carDao = carDao;
	}

}
