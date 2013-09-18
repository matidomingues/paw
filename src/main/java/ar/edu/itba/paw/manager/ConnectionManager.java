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

    private ThreadLocal<Connection> connection;

	private ConnectionManager() {
		try {
			Class.forName(DatabaseSettings.getDriver());
            this.connection = new ThreadLocal<Connection>();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }

        return instance;
    }

    private void setConnection() {
        try {
            if (this.connection.get() == null || this.connection.get().isClosed()) {
                Class.forName(DatabaseSettings.getDriver());
                Connection connection = DriverManager.getConnection(
                        connectionString, username, password);
                connection.setAutoCommit(false);
                System.out.println("Setting: " + connection.toString());
                this.connection.set(connection);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

	public Connection getConnection() throws SQLException {
		if (this.connection.get() == null || this.connection.get().isClosed()) {
            this.setConnection();
        }
        return this.connection.get();
	}
}
