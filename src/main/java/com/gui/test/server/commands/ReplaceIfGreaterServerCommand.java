package com.gui.test.server.commands;

import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.common.exception.NoElementException;
import com.gui.test.common.exception.UniqueException;
import com.gui.test.server.ServerValidator;

import java.sql.SQLException;
import java.util.Arrays;

public class ReplaceIfGreaterServerCommand extends ServerCommand{
	private final ServerValidator validator = new ServerValidator();
	
	public ReplaceIfGreaterServerCommand()  {
	}
	
	public String execute(Integer id, Product product, String login) throws SQLException {
		try {
			validator.idExistCheck(id);
			validator.isFullNameUniqueUpdate(id, product.getManufacturer().getFullName());
			validator.isPartNumberUniqueUpdate(id, product.getPartNumber());
			if (!MainCollection.getCollection().get(id).compare(product)) {
				return new UpdateServerCommand().execute(id, product, login);
			}
		} catch (NumberFormatException exception) {
			setErrorMessage("Этой команде необходимо передать параметр типа int!");
		} catch (NoElementException | UniqueException exception) {
			setErrorMessage(exception.getMessage());
		}
		return "Объект в коллекции не был изменен!";
	}
}