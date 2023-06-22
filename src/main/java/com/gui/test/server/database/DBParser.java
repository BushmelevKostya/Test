package com.gui.test.server.database;

import com.gui.test.common.product.Coordinates;
import com.gui.test.common.product.Organization;
import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.TreeMap;

public class DBParser {
	public static Migrations migrations;
	
	public DBParser(Migrations migrations) {
		DBParser.migrations = migrations;
	}
	
	public void parse() throws SQLException {
		ArrayList<Coordinates> coordinates = new ArrayList<>();
		ArrayList<Organization> organizations = new ArrayList<>();
		TreeMap<Integer, Product> collection = new TreeMap<>();
		
		ResultSet resCoordinates = migrations.getDataFromCoordinates();
		while (resCoordinates.next()) {
			coordinates.add(getCoordinates(resCoordinates));
		}
		ResultSet resOrganization = migrations.getDataFromOrganization();
		while (resOrganization.next()) {
			organizations.add(getOrganization(resOrganization));
		}
		ResultSet resProduct = migrations.getDataFromProduct();
		int i = 0;
		while (resProduct.next()) {
			collection.put(resProduct.getInt(1), getProduct(resProduct, coordinates.get(i), organizations.get(i)));
			i++;
		}
		MainCollection.setCollection(collection);
	}
	
	public Product getProduct(ResultSet resProduct, Coordinates coordinates, Organization organization) throws SQLException {
		var id = resProduct.getInt(1);
		var name = resProduct.getString(2);
		var creationDate = resProduct.getString(3);
		var price = resProduct.getDouble(4);
		var partNumber = resProduct.getString(5);
		var manufactureCost = resProduct.getInt(6);
		var unitOfMeasure = Product.stringToUnitOfMeasure(resProduct.getString(7));
		
		return new Product(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, organization);
	}
	
	public Coordinates getCoordinates(ResultSet resCoordinates) throws SQLException {
		var x = resCoordinates.getLong(1);
		var y = resCoordinates.getLong(2);
		
		return new Coordinates(x, y);
	}
	
	public Organization getOrganization(ResultSet resOrganization) throws SQLException {
		var orgId = resOrganization.getInt(1);
		var orgName = resOrganization.getString(2);
		var orgFulName = resOrganization.getString(3);
		var orgAnnualTurnover = resOrganization.getInt(4);
		var orgEmployeesCount = resOrganization.getInt(5);
		var orgType = Organization.stringToOrgType(resOrganization.getString(6));
		
		return new Organization(orgId, orgName, orgFulName, orgAnnualTurnover, orgEmployeesCount, orgType);
	}
	
	public static void parseToDb(Product product, String login) throws SQLException, ParseException {
		int id = insertProduct(product, login);
		insertCoordinates(product);
		insertOrganization(product);
		product.getManufacturer().setId(id);
		product.setId(id);
		MainCollection.getCollection().put(id, product);
	}
	
	public static int insertCoordinates(Product product) throws SQLException {
		var coordinates = product.getCoordinates();
		var x = coordinates.getX();
		var y = coordinates.getY();
		
		return migrations.insertCoordinates(x, y);
	}
	
	public static int insertOrganization(Product product) throws SQLException {
		var organization = product.getManufacturer();
		
		var orgName = organization.getName();
		var orgFulName = organization.getFullName();
		var orgAnnualTurnover = organization.getAnnualTurnover();
		var orgEmployeesCount = organization.getEmployeesCount();
		var orgType = organization.getType().toString();
		
		return migrations.insertOrganization(orgName, orgFulName, orgAnnualTurnover, orgEmployeesCount, orgType);
	}
	
	public static int insertProduct(Product product, String login) throws SQLException, ParseException {
		var name = product.getName();
		var creationDate = product.getCreationDate();
		var price = product.getPrice();
		var partNumber = product.getPartNumber();
		var manufactureCost = product.getManufactureCost();
		var unitOfMeasure = product.getUnitOfMeasure().toString();
		
		return migrations.insertProduct(name, creationDate, price, partNumber, manufactureCost, unitOfMeasure, migrations.getUserId(login));
	}
}
