package com.revature.cardealership.model;

public class Employee extends User {
	
	private final static String USER_TYPE = "Employee";

	public Employee() {
	}

	public Employee(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName, USER_TYPE);
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
