package ar.edu.itba.paw.configuration;

public class DatabaseSettings {

	public static final String driver = "org.postgresql.Driver";
	public static final String connectionString = "jdbc:postgresql:///";
	public static final String username = "";
	public static final String password = "";
	
	public static String getDriver() {
		return driver;
	}
	public static String getConnectionstring() {
		return connectionString;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}

}
