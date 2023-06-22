package com.gui.test.client.builder;

import com.gui.test.common.product.Product;
import com.gui.test.common.exception.ExitException;
import com.gui.test.common.exception.NoElementException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Takes in a builder object and manages its operation
 *
 * @see ProductBuilder
 */
public class Director {
	private Builder builder;
	
	/**
	 * @param builder
	 */
	public Director(Builder builder) {
		this.builder = builder;
	}
	
	/**
	 * @param builder
	 */
	public void changeBuilder(Builder builder) {
		this.builder = builder;
	}
	
	/**
	 * @return Product
	 */
	public Product make() throws ExitException, IOException {
		builder.reset();
		builder.setBName();
		builder.setBCoordinates();
		builder.setBCreationDate();
		builder.setBPrice();
		builder.setBPartNumber();
		builder.setBManufactureCost();
		builder.setBUnitOfMeasure();
		builder.setBOrganization();
		return builder.getResult();
		
	}
	
	public Product make(Product product) throws ExitException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Введите поле, которое хотите обновить. Чтобы завершить обновление, введите /exit");
		builder.reset(product);
		String str = "";
		while (!str.equals("/exit")) {
			System.out.println("name - n\ncoordinates - c\nprice - p\npartNumber - pn\n" +
					"manufactureCost - mc\nunitOfMeasure - uom\norganization - o");
			str = br.readLine().strip();
			switch (str) {
				case "name", "n" -> builder.setBName();
				case "coordinates", "c" -> builder.setBCoordinates();
				case "price", "p" -> builder.setBPrice();
				case "partNumber", "pn" -> builder.setBPartNumber();
				case "manufactureCost", "mc" -> builder.setBManufactureCost();
				case "unitOfMeasure", "uom" -> builder.setBUnitOfMeasure();
				case "organization", "o" -> builder.setBOrganization();
				case "/exit" -> System.out.println("Ввод полей завершен.");
				default -> System.out.println("Неверный ввод!");
			}
		}
		return builder.getResult();
	}
}
