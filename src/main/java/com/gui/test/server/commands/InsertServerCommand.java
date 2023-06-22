package com.gui.test.server.commands;

import com.gui.test.client.builder.ProductBuilder;
import com.gui.test.common.product.Product;
import com.gui.test.common.exception.UniqueException;
import com.gui.test.server.ServerValidator;
import com.gui.test.server.database.DBParser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Add new element to collection
 *
 * @see ProductBuilder
 */
public class InsertServerCommand extends ServerCommand{
	
	private final ServerValidator validator = new ServerValidator();
	
	public InsertServerCommand() {
	}
	
//	/**
//	 * @param value this id is key of new collection's element
//	 */

	public String execute(Integer id, Product product, String login) throws NumberFormatException {
		try {
			validator.isPartNumberUnique(product.getPartNumber());
			validator.isFullNameUnique(product.getManufacturer().getFullName());
			DBParser.parseToDb(product, login);
			return "В коллекцию был добавлен новый продукт!";
		} catch (NumberFormatException exception) {
			setErrorMessage("Этой команде необходимо передать параметр типа int!");
		} catch (UniqueException | SQLException | ParseException exception) {
			setErrorMessage(exception.getMessage());
		}
		return "В коллекцию новый продукт добавлен не был!";
	}
}