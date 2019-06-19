package com.revature.cardealership.dao;

import com.revature.cardealership.model.Customer;

public interface CustomerDAO extends UserDAO {
	boolean addCustomer(Customer customer);
	
	Customer getCustomerByUsername(String username);

}
