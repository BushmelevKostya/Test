package com.gui.test.client.commands;

import com.gui.test.client.ClientCommand;
import com.gui.test.client.ClientValidator;
import com.gui.test.client.builder.Director;
import com.gui.test.client.builder.ProductBuilder;
import com.gui.test.common.exception.FalseTypeException;
import com.gui.test.common.product.Product;
import com.gui.test.common.exception.ExitException;

import java.io.IOException;

public class ReplaceCommand extends ClientCommand {
	private final ClientValidator validator = new ClientValidator();
	private Product product;
	public ReplaceCommand() {
	}
	
	/**
	 *
	 * @param value  id of the element being changed
	 */
	public void execute(String value) throws ExitException {
		try {
			var product = new Director(new ProductBuilder()).make();
			setProduct(product);
		} catch (NumberFormatException exception){
			System.out.println("Этой команде необходимо передать параметр типа int!");
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Product getProduct() {
		return this.product;
	}
}