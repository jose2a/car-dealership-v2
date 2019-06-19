package com.revature.cardealership.model;

import java.util.HashSet;
import java.util.Set;

public class Customer extends User {
	
	private final static String USER_TYPE = "Customer";
	
	private Set<Car> cars = new HashSet<>();

	private Set<Offer> contracts = new HashSet<>();

	public Customer() {
		
	}

	public Customer(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName, USER_TYPE);
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	public void addCarToCustomer(Car car) {
		this.cars.add(car);
	}

	public void removeCar(Car car) {
		this.cars.remove(car);
	}

	public Set<Offer> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Offer> contracts) {
		this.contracts = contracts;
	}
	
	public void addContract(Offer contract) {
		this.contracts.add(contract);
	}
	
	public void removeContract(Offer contract) {
		this.contracts.remove(contract);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
