package com.revature.cardealership.services;

import com.revature.cardealership.dao.UserDAO;
import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.exceptions.PreexistingRecordException;
import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.Employee;
import com.revature.cardealership.model.User;
import com.revature.cardealership.utils.DAOUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

public class UserServiceImpl extends BaseService implements UserService {

	private UserDAO userDao;

	public UserServiceImpl() {
		userDao = DAOUtilities.getUserDAO();
	}

	@Override
	public User login(String username, String password) throws NotFoundRecordException {
		validateUserLoginInput(username, password);

		User user = userDao.getByUsernameAndPassword(username, password);

		if (user != null) {

			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				// user exist
				// check if user is a customer and employee
				if (user.getType().equals("Employee")) {

					user = new Employee(user.getUsername(), user.getPassword(), user.getFirstName(),
							user.getLastName());

				} else if (user.getType().equals("Customer")) {

					CustomerService customerService = ServiceUtilities.getCustomerService();
					user = customerService.getCustomerByUsername(username);
				}

				return user;
			}

		}

		throw new NotFoundRecordException("Verify your username and password.");
	}

	@Override
	public boolean registerCustomer(String username, String password, String firstName, String lastName)
			throws PreexistingRecordException {

		CustomerService customerService = ServiceUtilities.getCustomerService();

		if (customerService.getCustomerByUsername(username) != null) {
			throw new PreexistingRecordException("Customer with the same username is already registered.");
		}

		Customer theCustomer = new Customer(username, password, firstName, lastName);

		return customerService.addCustomer(theCustomer);
	}

	@Override
	public User getUserByUsername(String username) throws NotFoundRecordException {
		validateUsername(username);

		User user = userDao.getUserByUsername(username);

		if (user != null) {

			return user;
		}

		throw new NotFoundRecordException("User is not registered in the system.");
	}

}
