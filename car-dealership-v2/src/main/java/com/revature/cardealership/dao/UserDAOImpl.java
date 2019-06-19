package com.revature.cardealership.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.cardealership.model.User;
import com.revature.cardealership.utils.ModelMapperUtilities;

public class UserDAOImpl extends BaseDAO implements UserDAO {

	@Override
	public boolean addUser(User user) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query

		try {

			String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getType());

			if (stmt.executeUpdate() != 0)
				return true;

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, null);
		}

		return false;
	}

	@Override
	public User getByUsernameAndPassword(String username, String password) {

		User user = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, username);
			stmt.setString(2, password);

			rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User();

				ModelMapperUtilities.mapResultSetToUser(rs, user);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM users WHERE username = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, username);

			rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User();

				ModelMapperUtilities.mapResultSetToUser(rs, user);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return user;
	}

}
