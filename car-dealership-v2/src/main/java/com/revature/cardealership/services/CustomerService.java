package com.revature.cardealership.services;

import com.revature.cardealership.model.Customer;

public interface CustomerService {

	public boolean addCustomer(Customer customer);

	public Customer getCustomerByUsername(String username);
}
