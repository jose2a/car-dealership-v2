package com.revature.cardealership.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

import com.revature.cardealership.model.Car;
import com.revature.cardealership.utils.ModelMapperUtilities;

public class CarDAOImpl extends BaseDAO implements CarDAO {

	@Override
	public Set<Car> getAllCars() {

		Set<Car> cars = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {
			String sql = "SELECT * FROM cars WHERE active = true ORDER BY vin ASC"; // Our SQL query

			stmt = connection.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Car car = new Car();
				ModelMapperUtilities.mapResultSetToCar(rs, car);

				cars.add(car);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return cars;
	}

	@Override
	public boolean addCar(Car car) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		CallableStatement callableStatement = null;

		try {
			String sql = "{? = call insert_car(?, ?, ?, ?)}";

			callableStatement = connection.prepareCall(sql);

			callableStatement.registerOutParameter(1, Types.BOOLEAN);
			callableStatement.setString(2, car.getVin());
			callableStatement.setString(3, car.getMake());
			callableStatement.setString(4, car.getModel());
			callableStatement.setDouble(5, car.getPrice());

			callableStatement.execute();
			return callableStatement.getBoolean(1);

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);

		} finally {
			closeResources(stmt, null);

			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e1) {
					logCheckedExceptionsFromDAO(e1);
				}
			}

		}

		return false;
	}

	@Override
	public boolean updateCar(Car car) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query

		try {

			String sql = "UPDATE cars SET make=?, model=?, price=?, is_sold=?, active=?, username=? WHERE vin = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, car.getMake());
			stmt.setString(2, car.getModel());
			stmt.setDouble(3, car.getPrice());
			stmt.setBoolean(4, car.isSold());
			stmt.setBoolean(5, car.isActive());
			stmt.setString(6, car.getUsername());
			stmt.setString(7, car.getVin());

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
	public boolean removeCar(String vin) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query

		try {

			String sql = "DELETE FROM cars WHERE vin = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, vin);

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
	public Set<Car> getCarsByCustomerUsername(String username) {

		Set<Car> cars = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM cars WHERE username = ? ORDER BY vin ASC"; // Our SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setString(1, username);

			rs = stmt.executeQuery(); // Queries the database

			while (rs.next()) {
				Car car = new Car();
				ModelMapperUtilities.mapResultSetToCar(rs, car);

				cars.add(car);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return cars;
	}

	@Override
	public Car getCarByVin(String vin) {

		Car car = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM cars WHERE vin = ?"; // Our SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setString(1, vin);

			rs = stmt.executeQuery(); // Queries the database

			if (rs.next()) {
				car = new Car();
				ModelMapperUtilities.mapResultSetToCar(rs, car);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return car;
	}

	@Override
	public Set<Car> getCarsInInvertory() {
		Set<Car> cars = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM cars WHERE active = true AND is_sold = false ORDER BY vin ASC"; // Our SQL query

			stmt = connection.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Car car = new Car();
				ModelMapperUtilities.mapResultSetToCar(rs, car);

				cars.add(car);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return cars;
	}

}
