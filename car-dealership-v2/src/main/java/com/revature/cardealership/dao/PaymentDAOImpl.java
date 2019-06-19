package com.revature.cardealership.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.revature.cardealership.model.Payment;
import com.revature.cardealership.utils.ModelMapperUtilities;

public class PaymentDAOImpl extends BaseDAO implements PaymentDAO {

	@Override
	public boolean addPayment(Payment payment) {
		PreparedStatement stmt = null; // Creates the prepared statement from the query

		try {
			
			String sql = "INSERT INTO payments(payment_id, paid_date, amount_paid, vin) VALUES (?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);

			stmt.setInt(1, payment.getPaymentNo());
			stmt.setDate(2, Date.valueOf(payment.getPaidDate()));
			stmt.setDouble(3, payment.getAmountPaid());
			stmt.setString(4, payment.getVin());

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
	public boolean updatePayment(Payment payment) {
		PreparedStatement stmt = null; // Creates the prepared statement from the query

		try {

			String sql = "UPDATE payments SET paid_date=?, amount_paid=?, vin=? WHERE payment_id=?";
			stmt = connection.prepareStatement(sql);

			stmt.setDate(1, Date.valueOf(payment.getPaidDate()));
			stmt.setDouble(2, payment.getAmountPaid());
			stmt.setString(3, payment.getVin());
			stmt.setInt(4, payment.getPaymentNo());

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
	public Set<Payment> getAllPayments() {
		Set<Payment> payments = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT payment_id, paid_date, amount_paid, vin "
					+ "FROM payments ORDER BY vin ASC"; // Our SQL query

			stmt = connection.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Payment payment = new Payment();
				ModelMapperUtilities.mapResultSetToPayment(rs, payment);

				payments.add(payment);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return payments;
	}

	@Override
	public Set<Payment> getAllPaymentsByCustomerUsername(String username) {
		Set<Payment> payments = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT payment_id, paid_date, amount_paid, c.vin "
					+ "FROM payments p INNER JOIN cars c ON p.vin = c.vin "
					+ "WHERE c.username = ? ORDER BY c.vin ASC"; // Our SQL query
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, username);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Payment payment = new Payment();
				ModelMapperUtilities.mapResultSetToPayment(rs, payment);

				payments.add(payment);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return payments;
	}

	@Override
	public Set<Payment> getPaymentsByVin(String vin) {
		Set<Payment> payments = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {
			
			String sql = "SELECT payment_id, paid_date, amount_paid, vin "
					+ "FROM payments WHERE vin=? ORDER BY paid_date DESC"; // Our SQL query
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, vin);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Payment payment = new Payment();
				ModelMapperUtilities.mapResultSetToPayment(rs, payment);

				payments.add(payment);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return payments;
	}

	@Override
	public Set<Payment> getPaymentsByCarVinAndCustomerUsername(String vin, String username) {
		Set<Payment> payments = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT payment_id, paid_date, amount_paid, c.vin "
					+ "FROM payments p INNER JOIN cars c ON p.vin = c.vin "
					+ "WHERE c.username= ? AND c.vin= ? ORDER BY paid_date DESC"; // Our SQL query
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, username);
			stmt.setString(2, vin);
			
			rs = stmt.executeQuery();

			while (rs.next()) {
				Payment payment = new Payment();
				ModelMapperUtilities.mapResultSetToPayment(rs, payment);

				payments.add(payment);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return payments;
	}

}
