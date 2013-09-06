package ar.edu.itba.paw.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.edu.itba.paw.configuration.DatabaseSettings;

public class ConnectionManager {

	private static final String username = DatabaseSettings.getUsername();
	private static final String password = DatabaseSettings.getPassword();
	private static final String connectionString = DatabaseSettings
			.getConnectionstring();
	private static ConnectionManager instance = null;

	private ConnectionManager() {
		try {
			Class.forName(DatabaseSettings.getDriver());
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public static Connection getConnection() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		try {
			Class.forName(DatabaseSettings.getDriver());
			Connection connection = DriverManager.getConnection(
					connectionString, username, password);
			connection.setAutoCommit(false);
			return connection;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
