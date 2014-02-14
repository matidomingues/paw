package ar.edu.itba.paw.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.runners.model.InitializationError;

public class ConfigManager {

    private static ConfigManager instance;
    private static final Object synchronizer = new Object();
    
    private Configuration config;
    private int depth;
    private int popularityThreshold;
    
    private ConfigManager() throws ConfigurationException {
        this.config = new PropertiesConfiguration("twatt.properties");

        this.depth = config.getInt("recommendations.deep");
        this.popularityThreshold = config.getInt("general.popularityThreshold");
    }

    public synchronized static ConfigManager getInstance() {
        if (instance == null) {
        	synchronized (synchronizer) {
        		if (instance == null) {
        			try {
						instance = new ConfigManager();
					} catch (ConfigurationException ce) {
						try {
							throw new InitializationError(ce);
						} catch (InitializationError ie) {
							throw new Error(ie);
						}
					}
        		}
			}
        }
        return instance;
    }

    public int getDepth(){
    	return this.depth;
    }
    
    public int getPopularityThreshold() {
    	return this.popularityThreshold;
    }
}
