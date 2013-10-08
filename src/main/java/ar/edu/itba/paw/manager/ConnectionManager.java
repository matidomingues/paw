package ar.edu.itba.paw.manager;

import ar.edu.itba.paw.utils.ConfigManager;
import org.apache.commons.configuration.ConfigurationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static ConnectionManager instance = null;

    private ThreadLocal<Connection> connection;
    private String connectionString;
    private String username;
    private String password;

	private ConnectionManager() {
        try {
            Class.forName(ConfigManager.getInstance().getDatabaseDriver());
            this.connection = new ThreadLocal<Connection>();
            this.connectionString = ConfigManager.getInstance().getConnectionString();
            this.username = ConfigManager.getInstance().getDatabaseUsername();
            this.password = ConfigManager.getInstance().getDatabasePassword();
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Unnable to initialize connection.", e);
        } catch (ConfigurationException e) {
            throw new DatabaseException("Unnable to initialize connection.", e);
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
                Class.forName(ConfigManager.getInstance().getDatabaseDriver());
                Connection connection = DriverManager.getConnection(
                        connectionString, username, password);
                connection.setAutoCommit(false);
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
