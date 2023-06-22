package com.gui.test.server.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
	
	public Connector() {
	}
	
	public static Connection getConnection() throws SQLException, IOException {
		
		Properties properties = new Properties();
		try (InputStream config = Connector.class.getClassLoader().getResourceAsStream("config.properties")){
		properties.load(config);
		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		return DriverManager.getConnection(url, user, password);
		}
	}
}
