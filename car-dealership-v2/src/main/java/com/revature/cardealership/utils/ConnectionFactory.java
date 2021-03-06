package com.revature.cardealership.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private static Connection connection;

	private static String url;
	private static String user;
	private static String password;
	private static final String PROPERTIES_FILE = "database.properties";

	public static synchronized Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				loadConectionsParameters();
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);

				LogUtilities.trace("Connection open.");
			}
		} catch (ClassNotFoundException e) {
			LogUtilities.error("Could not register the driver.");
		} catch (SQLException e) {
			LogUtilities.error("Could not connect to the database.");
		}

		LogUtilities.trace("Returning existing connection");

		return connection;
	}

	private static void loadConectionsParameters() {
		Properties prop = new Properties();

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		try (InputStream fis = loader.getResourceAsStream(PROPERTIES_FILE);) {
			prop.load(fis);

			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
		} catch (FileNotFoundException e) {
			LogUtilities.error(e.getMessage());
		} catch (IOException e) {
			LogUtilities.error("Could not load connection properties file. " + e.getMessage());
		}
	}

}
