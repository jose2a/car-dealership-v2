package com.revature.cardealership.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.cardealership.model.Customer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerDAOImplTest {

	private static CustomerDAOImpl customerDao;
	private static String customerUsername;
	
	@BeforeClass
	public static void setUp() {
		customerUsername = RandomStringUtils.randomAlphabetic(10);		
	}

	@Before
	public void init() {
		customerDao = new CustomerDAOImpl();
	}

	@Test
	public void a_addCustomer_ValidCustomer_ShouldReturnTrue() {

		Customer theCustomer = new Customer(customerUsername, "s3cret", "Peter", "Parker");
		boolean isAdded = customerDao.addCustomer(theCustomer);

		assertTrue(isAdded);

	}

	@Test
	public void b_addCustomer_UsernameExist_ShouldReturnFalse() {

		Customer theCustomer = new Customer(customerUsername, "s3cret", "Peter", "Parker");
		boolean isAdded = customerDao.addCustomer(theCustomer);

		assertFalse(isAdded);

	}

	@Test
	public void c_getCustomerByUsername_UserIsNotInDB_ReturnsNull() {
		Customer theCustomer = customerDao
				.getCustomerByUsername(RandomStringUtils.randomAlphabetic(10));

		assertNull(theCustomer);
	}

	@Test
	public void d_getCustomerByUsername_UserIsInDB_ReturnsUser() {
		Customer theCustomer = customerDao.getCustomerByUsername(customerUsername);

		assertEquals(customerUsername, theCustomer.getUsername());
	}

}
