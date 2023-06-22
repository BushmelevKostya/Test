package com.gui.test.server.main;

import com.gui.test.server.ServerConcurrentExecutor;
import com.gui.test.server.database.DBParser;
import com.gui.test.server.database.Initializer;
import com.gui.test.server.database.Connector;
import com.gui.test.server.database.Migrations;

import java.sql.Connection;
import java.sql.Statement;

public class UDPServer {
	
	public static void main(String[] args) throws Exception {
		try (Connection connection = Connector.getConnection();
			Statement statement = connection.createStatement()) {
			Migrations migrations = new Migrations(connection, statement);
			new Initializer(migrations).initialize();
			DBParser parser = new DBParser(migrations);
			parser.parse();
			new ServerConcurrentExecutor().run();
		}
	}
}