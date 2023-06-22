package com.gui.test.server.database;

import java.sql.*;
import java.text.ParseException;
import java.time.ZonedDateTime;

public class Migrations {
	private final Statement statement;
	private final Connection connection;
	
	public Migrations(Connection connection, Statement statement) {
		this.connection = connection;
		this.statement = statement;
	}
	
	public boolean createTableProduct() throws SQLException {
		String query = "CREATE TABLE IF NOT EXISTS product\n" +
				"(\n" +
				"    id               SERIAL PRIMARY KEY,\n" +
				"    name             VARCHAR(255)        NOT NULL,\n" +
				"    creation_date    timestamp                   ,\n" +
				"    price            FLOAT               NOT NULL,\n" +
				"    part_number      VARCHAR(255) UNIQUE NOT NULL,\n" +
				"    manufacture_cost VARCHAR(255)        NOT NULL,\n" +
				"    unit_of_measure  VARCHAR(255)        NOT NULL,\n" +
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE\n" +
				");";
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.execute();
	}
	
	public boolean createTableCoordinates() throws SQLException {
		String query = "CREATE TABLE IF NOT EXISTS coordinate " +
				"(id INTEGER PRIMARY KEY DEFAULT 0, FOREIGN KEY (id) REFERENCES product(id) ON DELETE CASCADE, x BIGINT NOT NULL, y BIGINT NOT NULL);";
		
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.execute();
	}
	
	public boolean createTableOrganization() throws SQLException {
		String query = "CREATE TABLE IF NOT EXISTS organization\n" +
				"    (id INTEGER PRIMARY KEY DEFAULT 0, FOREIGN KEY (id) REFERENCES product (id) ON DELETE CASCADE,\n" +
				"    name              VARCHAR(255) NOT NULL,\n" +
				"    fullname          VARCHAR(255) UNIQUE,\n" +
				"    annual_turnover   INTEGER      NOT NULL,\n" +
				"    employees_count   INTEGER      NOT NULL,\n" +
				"    organization_type VARCHAR(255) NOT NULL\n" +
				");";
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.execute();
	}
	
	public boolean createTableUser() throws SQLException {
		String query = "CREATE TABLE IF NOT EXISTS users\n" +
				"   (id SERIAL PRIMARY KEY," +
				"   name VARCHAR(255) NOT NULL," +
				"   login VARCHAR(255) UNIQUE NOT NULL," +
				"   password VARCHAR(255) NOT NULL);";
		
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.execute();
	}
	
	public ResultSet getDataFromCoordinates() throws SQLException {
		String query = "SELECT * FROM coordinate";
		
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.executeQuery();
	}
	
	public ResultSet getDataFromOrganization() throws SQLException {
		String query = "SELECT * FROM organization";
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.executeQuery();
	}
	
	public ResultSet getDataFromProduct() throws SQLException {
		String query = "SELECT * FROM product";
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.executeQuery();
	}
	
	public int insertCoordinates(long x, long y) throws SQLException {
		String query = "INSERT INTO coordinate (id, x, y)\n" +
				"VALUES (CURRVAL('product_id_seq'), ?, ?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, x);
		ps.setLong(2, y);
		return ps.executeUpdate();
	}
	
	public int insertOrganization(String name, String fullname, int annual_turnover, int employees_count, String orgType) throws SQLException {
		String query = "INSERT INTO organization (id, name, fullname, annual_turnover, employees_count, organization_type)\n" +
				"VALUES (CURRVAL('product_id_seq'), ?, ?, ?, ?, ?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ps.setString(2, fullname);
		ps.setInt(3, annual_turnover);
		ps.setInt(4, employees_count);
		ps.setString(5, orgType);
		return ps.executeUpdate();
	}
	
	public int insertProduct(String name, String creation_date, double price, String part_number, int manufacture_cost, String unit_of_measure, Integer user_id) throws SQLException, ParseException {
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(creation_date);
		Timestamp timestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime());
		String query = "INSERT INTO product (name, creation_date, price, part_number, manufacture_cost, unit_of_measure, user_id)\n" +
				"VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ps.setTimestamp(2, timestamp);
		ps.setDouble(3, price);
		ps.setString(4, part_number);
		ps.setInt(5, manufacture_cost);
		ps.setString(6, unit_of_measure);
		ps.setInt(7, user_id);
		ps.executeUpdate();
		
		return getLastProductId();
	}
	
	public int insertUser(String name, String login, String password) throws SQLException {
		String query = "INSERT INTO users(name, login, password)" +
				"VALUES (?,?,?);";
		
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ps.setString(2, login);
		ps.setString(3, password);
		return ps.executeUpdate();
	}
	
	public boolean dropProduct() throws SQLException {
		String query = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'product')";
		PreparedStatement ps = connection.prepareStatement(query);
		try (ResultSet resultSet = ps.executeQuery()) {
			resultSet.next();
			if (resultSet.getBoolean(1)) {
				query = "DROP TABLE product CASCADE;";
				ps = connection.prepareStatement(query);
				return ps.execute();
			}
		}
		return false;
	}
	
	public boolean dropCoordinate() throws SQLException {
		String query = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'coordinate')";
		PreparedStatement ps = connection.prepareStatement(query);
		try (ResultSet resultSet = ps.executeQuery()) {
			resultSet.next();
			if (resultSet.getBoolean(1)) {
				query = "DROP TABLE coordinate CASCADE;";
				ps = connection.prepareStatement(query);
				return ps.execute();
			}
		}
		return false;
	}
	
	public boolean dropOrganization() throws SQLException {
		String query = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'organization')";
		PreparedStatement ps = connection.prepareStatement(query);
		try (ResultSet resultSet = ps.executeQuery()) {
			resultSet.next();
			if (resultSet.getBoolean(1)) {
				query = "DROP TABLE organization CASCADE;";
				ps = connection.prepareStatement(query);
				return ps.execute();
			}
		}
		return false;
	}
	
	public int getLastProductId() throws SQLException {
		String query = "SELECT CURRVAL('product_id_seq')";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet result = ps.executeQuery();
		result.next();
		
		return result.getInt(1);
	}
	
	@Deprecated
	public void clearCoordinateTable() throws SQLException {
		String query = "TRUNCATE TABLE coordinate RESTART IDENTITY";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.executeUpdate();
	}
	
	@Deprecated
	public void clearOrganizationTable() throws SQLException {
		String query = "TRUNCATE TABLE organization RESTART IDENTITY";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.executeUpdate();
	}
	
	public int deleteFromProduct(Integer id, Integer user_id) throws SQLException {
		String query = "DELETE FROM product WHERE id = ? AND user_id = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);
		ps.setInt(2, user_id);
		
		return ps.executeUpdate();
	}
	
	
	public String checkUser(String login) throws SQLException {
		String query = "SELECT name FROM users " +
				"WHERE login = ?;";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, login);
		ResultSet result = ps.executeQuery();
		if (result.next()) return result.getString(1);
		return null;
	}
	
	public boolean checkPassword(String login, String password) throws SQLException {
		String query = "SELECT name FROM users " +
				"WHERE login = ? " +
				"AND password = ?;";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, login);
		ps.setString(2, password);
		ResultSet result = ps.executeQuery();
		return result.next();
	}
	
	public Integer getUserId(String login) throws SQLException {
		String query = "SELECT id FROM users " +
				"WHERE login = ?;";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, login);
		ResultSet result = ps.executeQuery();
		result.next();
		return result.getInt(1);
	}
	@Deprecated
	public void createPreventTrigger(Integer user_id) throws SQLException {
		
		String queryFunc = "CREATE OR REPLACE FUNCTION keep_rows()" +
				"RETURNS TRIGGER AS $$" +
				"BEGIN " +
				"IF OLD.user_id = " + user_id + " THEN " +
				"RETURN OLD;" +
				"ELSE " +
				"RETURN NULL;" +
				"END IF;" +
				"END;" +
				"$$" +
				"LANGUAGE plpgsql;";
		
		String queryUpdateFunc = "CREATE OR REPLACE FUNCTION keep_rows_update()" +
				"RETURNS TRIGGER AS $$" +
				"BEGIN " +
				"IF OLD.user_id = " + user_id + " THEN " +
				"RETURN NEW;" +
				"ELSE " +
				"RETURN NULL;" +
				"END IF;" +
				"END;" +
				"$$" +
				"LANGUAGE plpgsql;";
		
		String queryDeleteTrigger = "CREATE OR REPLACE TRIGGER keep_rows_trigger " +
				"BEFORE DELETE ON product " +
				"FOR EACH ROW " +
				"EXECUTE FUNCTION keep_rows();";
		
		String queryUpdateTrigger = "CREATE OR REPLACE TRIGGER prevent_update_trigger " +
				"BEFORE UPDATE ON product " +
				"FOR EACH ROW " +
				"EXECUTE FUNCTION keep_rows_update();";
		
		statement.execute(queryFunc);
		statement.execute(queryUpdateFunc);
		statement.execute(queryDeleteTrigger);
		statement.execute(queryUpdateTrigger);
	}
	
	public void clearProductTable(Integer user_id) throws SQLException {
		String query = "DELETE FROM product WHERE user_id = ?;";
		
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, user_id);
		ps.executeUpdate();
	}
	
	
	public boolean dropUsers() throws SQLException {
		String query = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'users')";
		PreparedStatement ps = connection.prepareStatement(query);
		try (ResultSet resultSet = ps.executeQuery()) {
			resultSet.next();
			if (resultSet.getBoolean(1)) {
				query = "DROP TABLE users CASCADE;";
				ps = connection.prepareStatement(query);
				return ps.execute();
			}
		}
		return false;
	}
	
	public int updateProduct(Integer id, String name, String creation_date, double price, String part_number, int manufacture_cost, String unit_of_measure, Integer user_id) throws SQLException {
		Timestamp timestamp;
		try {
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(creation_date);
			timestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime());
		} catch (IllegalArgumentException exception) {
			timestamp = Timestamp.valueOf(creation_date);
		}
		String query = "UPDATE product " +
				"SET name = ?, creation_date = ?, price = ?, part_number = ?, manufacture_cost = ?, unit_of_measure = ?, user_id = ? " +
				"WHERE id = ? AND user_id = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		
		ps.setString(1, name);
		ps.setTimestamp(2, timestamp);
		ps.setDouble(3, price);
		ps.setString(4, part_number);
		ps.setInt(5, manufacture_cost);
		ps.setString(6, unit_of_measure);
		ps.setInt(7, user_id);
		ps.setInt(8, id);
		ps.setInt(9, user_id);
		
		return ps.executeUpdate();
	}
	
	public void dropTrigger() throws SQLException {
		String query = "DROP TRIGGER IF EXISTS keep_rows_trigger ON product;";
		
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
	}
	
	public int deleteFromProductWhere(Integer id, Integer user_id) throws SQLException {
		String sql = "DELETE FROM product WHERE id < ? AND user_id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ps.setInt(2, user_id);
		
		return ps.executeUpdate();
	}
}