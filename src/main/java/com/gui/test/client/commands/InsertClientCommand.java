package com.gui.test.client.commands;

import com.gui.test.client.ClientCommand;
import com.gui.test.client.ClientValidator;
import com.gui.test.client.builder.Director;
import com.gui.test.client.builder.ProductBuilder;
import com.gui.test.common.exception.FalseTypeException;
import com.gui.test.common.exception.FalseValuesException;
import com.gui.test.common.product.Product;
import com.gui.test.common.exception.ExitException;

import java.io.IOException;

/**
 * Add new element to collection
 *
 * @see ProductBuilder
 */
public class InsertClientCommand extends ClientCommand {
	private Product product;
	private final ClientValidator validator = new ClientValidator();
	
	public InsertClientCommand() {
	}
	
	/**
	 * @param value this id is key of new collection's element
	 */
	@Override
	public void execute(String value) throws ExitException, FalseValuesException, NumberFormatException, IOException, FalseTypeException {
		Product newProduct = new Director(new ProductBuilder()).make();
		setProduct(newProduct);
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
}