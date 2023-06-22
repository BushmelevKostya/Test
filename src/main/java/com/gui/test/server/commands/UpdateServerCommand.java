package com.gui.test.server.commands;

import com.gui.test.common.Response;
import com.gui.test.common.exception.NoElementException;
import com.gui.test.common.exception.UniqueException;
import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.server.ServerValidator;
import com.gui.test.server.database.DBParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.TreeMap;

public class UpdateServerCommand extends ServerCommand {
	
	private final ServerValidator validator = new ServerValidator();
	public UpdateServerCommand() {
	}
	
	@Override
	public String execute(Integer id, Product product, String login) throws SQLException {
		try {
			TreeMap<Integer, Product> collection = MainCollection.getCollection();
			validator.idExistCheck(id);
			validator.isFullNameUniqueUpdate(id, product.getManufacturer().getFullName());
			validator.isPartNumberUniqueUpdate(id, product.getPartNumber());
			int result = migrations.updateProduct(id, product.getName(), product.getCreationDate(), product.getPrice(),
				product.getPartNumber(), product.getManufactureCost(), product.getUnitOfMeasure().toString(),
				migrations.getUserId(login));
			ResultSet resultSet = migrations.getDataFromProduct();
			resultSet.next();
			if (result > 0) {
				collection.remove(id);
				product.setId(id);
				product.getManufacturer().setId(id);
				collection.put(id, product);
				MainCollection.setCollection(collection);
				return "Элемент коллекции обновлен!";
			}
		} catch (NoElementException | UniqueException exception){
			setErrorMessage(exception.getMessage());
			return "Элемент коллекции не был обновлен!";
		}
		return "Отказано в доступе, элемент коллекции не был обновлен!";
	}
}