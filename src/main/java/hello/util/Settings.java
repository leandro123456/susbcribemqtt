package hello.util;

import java.io.FileInputStream;
import java.util.Properties;


public class Settings {
	private Properties properties;
    private static Settings instance = null;
	private boolean usarPostgresql;
	private String uriBroker;
	private String userNameBroker;
	private String passwordBroker;


	private Settings() {
        this.load();
    }

    public static Settings getInstance() {
        if (instance==null) {
            instance = new Settings();
        }
        return instance;
    }


    public void load() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("/var/cdash/properties/cdash.properties"));
            this.usarPostgresql = Boolean.parseBoolean(properties.getProperty("usarPostgresql"));
            this.uriBroker = properties.getProperty("uriBroker");
            this.userNameBroker = properties.getProperty("userNameBroker");
            this.passwordBroker = properties.getProperty("passwordBroker");
        } catch (Exception e) {
        	System.out.println("Error... no se puede leer el archivo de propiedades");
            e.printStackTrace();
        }
    }

	public boolean getUsarPostgresql() {
		return usarPostgresql;
	}

	public String getUriBroker() {
		return uriBroker;
	}

	public String getUserNameBroker() {
		return userNameBroker;
	}

	public String getPasswordBroker() {
		return passwordBroker;
	}   
}

