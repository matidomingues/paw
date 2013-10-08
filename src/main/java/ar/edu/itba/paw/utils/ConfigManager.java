package ar.edu.itba.paw.utils;

import org.apache.commons.configuration.*;

public class ConfigManager {

    private static ConfigManager instance;

    private Configuration config;
    private String driver;
    private String connectionString;
    private String dbUser;
    private String dbPass;

    private ConfigManager() throws ConfigurationException {
        this.config = new PropertiesConfiguration("twatt.properties");

        this.driver = config.getString("database.driver");
        this.connectionString = config.getString("database.connection-string");
        this.dbUser = config.getString("database.user");
        this.dbPass = config.getString("database.pass");

    }

    public static ConfigManager getInstance() throws ConfigurationException {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getDatabaseDriver() {
        return this.driver;
    }
    public String getConnectionString() {
        return this.connectionString;
    }
    public String getDatabaseUsername() {
        return this.dbUser;
    }
    public String getDatabasePassword() {
        return this.dbPass;
    }
}
