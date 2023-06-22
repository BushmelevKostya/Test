package com.gui.test.server.database;

import java.sql.SQLException;

// update not owned entity -> setup n -> /exit


public class Initializer {
	
	private final Migrations migrations;
	
	public Initializer(Migrations migrations) {
		this.migrations = migrations;
	}
	
	public void initialize() throws SQLException {
//		migrations.dropUsers();
//		migrations.dropProduct();
//		migrations.dropOrganization();
//		migrations.dropCoordinate();
		migrations.createTableUser();
		migrations.createTableProduct();
		migrations.createTableCoordinates();
		migrations.createTableOrganization();
//		migrations.insertProduct("1","2023-05-06T10:38:23.552896300+03:00[Europe/Moscow]",1,"1",1,"GRAMS");
//		migrations.insertCoordinates(1,1);
//		migrations.insertOrganization("1","1",1,1,"TRUST");
	}
}
