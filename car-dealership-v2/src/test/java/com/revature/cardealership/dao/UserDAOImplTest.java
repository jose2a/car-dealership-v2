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

import com.revature.cardealership.model.User;
import com.revature.cardealership.model.UserType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOImplTest {

	private static UserDAOImpl userDao;
	private static String username;

	@BeforeClass
	public static void setUp() {
		username = RandomStringUtils.randomAlphabetic(10);
	}

	@Before
	public void init() {
		userDao = new UserDAOImpl();
	}

	@Test
	public void _1_add_ValidUser_ShouldReturnTrue() {
		User theUser = new User(username, "s3cret", "John", "Doe", UserType.Employee.toString());
		boolean isAdded = userDao.addUser(theUser);

		assertTrue(isAdded);

	}

	public void _2_add_InvalidUser_ShouldThrowSQLException() {
		User theUser = new User(username, null, "John", "Doe", UserType.Employee.toString());
		boolean res = userDao.addUser(theUser);

		assertFalse(res);
	}

	@Test
	public void _3_getByUsernameAndPassword_ValidData_ShouldReturnUser() {
		User theUser = userDao.getByUsernameAndPassword(username, "s3cret");

		assertEquals("Doe", theUser.getLastName());
	}

	@Test
	public void _4_getByUsernameAndPassword_InvalidData_ShouldReturnNull() {
		User theUser = userDao.getByUsernameAndPassword(username, "scret");

		assertNull(theUser);
	}

}
