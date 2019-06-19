package com.revature.cardealership.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashSet;
import java.util.Set;

import com.revature.cardealership.model.Offer;
import com.revature.cardealership.model.OfferStatus;
import com.revature.cardealership.utils.ModelMapperUtilities;

public class OfferDAOImpl extends BaseDAO implements OfferDAO {

	private String baseSql = "SELECT o.*, make, model, price, is_sold, active, first_name, last_name, password "
			+ "FROM offers o INNER JOIN customers c	ON o.username = c.username "
			+ "INNER JOIN cars cr ON o.vin = cr.vin " + "INNER JOIN users u ON c.username = u.username ";

	@Override
	public boolean addOffer(Offer offer) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "INSERT INTO offers (offer_id, signed_date, amount, total_payment, payments_made, monthly_payment, status_id, username, vin) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, offer.getOfferId());
			stmt.setDate(2, Date.valueOf(offer.getSignedDate()));
			stmt.setDouble(3, offer.getAmount());
			stmt.setInt(4, offer.getTotalPayments());
			stmt.setInt(5, offer.getPaymentsMade());
			stmt.setDouble(6, offer.getMonthlyPayment());
			stmt.setInt(7, offer.getStatus().getValue());
			stmt.setString(8, offer.getUsername());
			stmt.setString(9, offer.getVin());

			if (stmt.executeUpdate() != 0) {
				return true;
			}

		} catch (SQLException e) {

			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return false;
	}

	@Override
	public boolean updateOffer(Offer offer) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database
		Savepoint sp = null;

		try {

			connection.setAutoCommit(false); // Using transactions

			sp = connection.setSavepoint("Inserting Offer");

			String sql = "UPDATE offers SET signed_date=?, amount=?, total_payment=?, payments_made=?, monthly_payment=?, status_id=?, username=?, vin=? "
					+ "WHERE offer_id=?";

			stmt = connection.prepareStatement(sql);

			stmt.setDate(1, Date.valueOf(offer.getSignedDate()));
			stmt.setDouble(2, offer.getAmount());
			stmt.setInt(3, offer.getTotalPayments());
			stmt.setInt(4, offer.getPaymentsMade());
			stmt.setDouble(5, offer.getMonthlyPayment());
			stmt.setInt(6, offer.getStatus().getValue());
			stmt.setString(7, offer.getUsername());
			stmt.setString(8, offer.getVin());
			stmt.setString(9, offer.getOfferId());

			if (stmt.executeUpdate() != 0) {
				connection.commit(); // Commit the transactions
				connection.setAutoCommit(true); // Activate autocommit

				return true;
			}

		} catch (SQLException e) {
			try {
				connection.rollback(sp); // Rollback if an error happens
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				logCheckedExceptionsFromDAO(e1);
			}

			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return false;
	}

	@Override
	public boolean removeOffer(String offerId) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "DELETE FROM offers WHERE offer_id = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, offerId);

			if (stmt.executeUpdate() != 0)
				return true;

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return false;
	}

	@Override
	public Offer getOfferByOfferId(String offerId) {

		Offer offer = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = baseSql + "WHERE o.offer_id = ?";

			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setString(1, offerId);

			rs = stmt.executeQuery(); // Queries the database

			if (rs.next()) {
				offer = new Offer();
				ModelMapperUtilities.mapResulsetToOfferWithCarAndCustomer(rs, offer);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return offer;
	}

	@Override
	public Set<Offer> getOffersByStatus(OfferStatus offerStatus) {

		Set<Offer> offers = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = baseSql + "WHERE status_id = ? ORDER BY vin ASC";

			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setInt(1, offerStatus.getValue());

			rs = stmt.executeQuery(); // Queries the database

			while (rs.next()) {
				Offer offer = new Offer();
				ModelMapperUtilities.mapResulsetToOfferWithCarAndCustomer(rs, offer);

				offers.add(offer);
			}
		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return offers;
	}

	@Override
	public Set<Offer> getAllOffers() {

		Set<Offer> offers = new HashSet<>();

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			stmt = connection.prepareStatement(baseSql); // Creates the prepared statement from the query

			rs = stmt.executeQuery(); // Queries the database

			while (rs.next()) {
				Offer offer = new Offer();
				ModelMapperUtilities.mapResulsetToOfferWithCarAndCustomer(rs, offer);

				offers.add(offer);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return offers;
	}

	@Override
	public Offer getOfferByOfferIdWithNoJoins(String offerId) {
		Offer offer = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM offers WHERE offer_id = ?";

			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setString(1, offerId);

			rs = stmt.executeQuery(); // Queries the database

			if (rs.next()) {
				offer = new Offer();
				ModelMapperUtilities.mapResultSetToOffer(rs, offer);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return offer;
	}

	@Override
	public Offer getOfferByVinAndUsernameWithNoJoins(String vin, String username) {
		Offer offer = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM offers WHERE vin = ? and username = ?";

			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setString(1, vin);
			stmt.setString(2, username);

			rs = stmt.executeQuery(); // Queries the database

			if (rs.next()) {
				offer = new Offer();
				ModelMapperUtilities.mapResultSetToOffer(rs, offer);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return offer;
	}

	@Override
	public Offer getOfferByVinAndStatusApproved(String vin) {
		Offer offer = null;

		PreparedStatement stmt = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try {

			String sql = "SELECT * FROM offers WHERE vin = ? and status_id = ?";

			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			stmt.setString(1, vin);
			stmt.setInt(2, OfferStatus.ACCEPTED.getValue());

			rs = stmt.executeQuery(); // Queries the database

			if (rs.next()) {
				offer = new Offer();
				ModelMapperUtilities.mapResultSetToOffer(rs, offer);
			}

		} catch (SQLException e) {
			logCheckedExceptionsFromDAO(e);
		} finally {
			closeResources(stmt, rs);
		}

		return offer;
	}

}
