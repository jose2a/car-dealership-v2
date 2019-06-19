package com.revature.cardealership.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.cardealership.utils.ConnectionFactory;
import com.revature.cardealership.utils.LogUtilities;
import com.revature.cardealership.utils.ScreenUtilities;

public abstract class BaseDAO {

	protected Connection connection;

	public BaseDAO() {
		connection = ConnectionFactory.getConnection();
	}

	protected void closeResources(PreparedStatement ps, ResultSet rs) {

		try {

			if (ps != null) {
				ps.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			LogUtilities.error("Error closing the resources. " + e.getMessage());
			ScreenUtilities.showFatalErrorMessage();
		}

	}

	protected void logCheckedExceptionsFromDAO(SQLException e) {
		LogUtilities.info(e.getMessage());
	}

}
