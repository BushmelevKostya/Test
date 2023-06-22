package com.gui.test.server.commands;

import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;

/**
 * Print all information about element collection
 */
public class ShowCommand extends ServerCommand {
	public ShowCommand() {
	}
	
	@Override
	public String execute(Integer id, Product product, String login) {
		return MainCollection.printCollection();
	}
}