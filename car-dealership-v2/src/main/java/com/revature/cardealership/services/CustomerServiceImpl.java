package com.revature.cardealership.services;

import com.revature.cardealership.dao.CustomerDAO;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.utils.DAOUtilities;

public class CustomerServiceImpl extends BaseService implements CustomerService {

	private CustomerDAO customerDao;

	public CustomerServiceImpl() {
		customerDao = DAOUtilities.getCustomerDAO();
	}

	@Override
	public boolean addCustomer(Customer customer) {
		validateCustomer(customer);
		
		return customerDao.addCustomer(customer);
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		validateUsername(username);

		return customerDao.getCustomerByUsername(username);
	}

}
