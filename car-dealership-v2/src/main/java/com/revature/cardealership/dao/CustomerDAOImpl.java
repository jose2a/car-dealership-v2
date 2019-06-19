package com.revature.cardealership.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.cardealership.model.Customer;
import com.revature.cardealership.model.User;
import com.revature.cardealership.model.UserType;

public class CustomerDAOImpl extends UserDAOImpl implements CustomerDAO {

	@Override
	public boolean addCustomer(Customer customer) {
		
		customer.setType(UserType.Customer.toString());

		boolean isUserAdded = super.addUser(customer);

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		if (isUserAdded) {

			try {
				
				String sql = "INSERT INTO customers VALUES (?)";
				stmt = connection.prepareStatement(sql);

				stmt.setString(1, customer.getUsername());

				if (stmt.executeUpdate() != 0)
					return true;
			} catch (SQLException e) {
				logCheckedExceptionsFromDAO(e);
			} finally {
				closeResources(stmt, rs);
			}
		}

		return false;
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		Customer customer = null;

		User user = super.getUserByUsername(username);

		if (user == null) {
			return null;
		}

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {
			
			String sql = "SELECT * FROM customers c WHERE c.username = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, username);

			rs = stmt.executeQuery();

			if (rs.next()) {
				customer = new Customer(user.getUsername(), user.getPassword(), user.getFirstName(),
						user.getLastName());

				// If we had more fields for customers we use this loop to populate them
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return customer;
	}

}
