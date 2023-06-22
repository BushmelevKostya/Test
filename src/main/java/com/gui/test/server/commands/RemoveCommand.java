package com.gui.test.server.commands;

import com.gui.test.common.exception.NoElementException;
import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.server.ServerValidator;
import com.gui.test.server.commands.ServerCommand;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Remove command with received key
 */
public class RemoveCommand extends ServerCommand {
	private final ServerValidator validator = new ServerValidator();
	
	public RemoveCommand() {
	}
	
	@Override
	public String execute(Integer id, Product product, String login) throws SQLException {
		try {
			validator.idExistCheck(id);
			if (migrations.deleteFromProduct(id, migrations.getUserId(login)) != 0) {
				MainCollection.getCollection().remove(id);
				return "Элемент коллекции был удален!";
			}
		} catch (NumberFormatException exception) {
			setErrorMessage("Этой команде необходимо передать параметр типа int!");
            return "Элемент коллекции не был удален";
		} catch (NoElementException exception) {
            setErrorMessage(exception.getMessage());
            return "Элемент коллекции не был удален";
        }
		return "Отказано в доступе,элемент коллекции не был удален!";
	}
}