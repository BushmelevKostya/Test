package com.gui.test.client.builder;

import com.gui.test.client.ClientValidator;
import com.gui.test.common.exception.ExitException;
import com.gui.test.common.exception.FalseTypeException;
import com.gui.test.common.exception.FalseValuesException;
import com.gui.test.common.exception.InfiniteException;
import com.gui.test.common.product.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;

/**
 * Crete object ProductBuilder
 *
 * @see Director
 */
public class ProductBuilder implements Builder {
	private Product product;
	private final ClientValidator validator = new ClientValidator();
	private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public ProductBuilder() {
	}
	
	@Override
	public void reset() {
		this.product = new Product(1, "", new Coordinates(0, 0), "", 0F,
				"", 0, UnitOfMeasure.GRAMS, new Organization());
	}
	
	@Override
	public void reset(Product product) {
		this.product = product;
	}
	
	@Override
	public void setBName() throws ExitException, IOException {
		String name = "";
		while (name.equals("")) {
			System.out.print("Введите имя: ");
			name = getNewString();
		}
		this.product.setName(name);
	}
	
	@Override
	public void setBCoordinates() throws ExitException {
		Coordinates coordinates = new Coordinates();
		coordinates.setX(setBX());
		coordinates.setY(setBY());
		this.product.setCoordinates(coordinates);
	}
	
	@Override
	public void setBCreationDate() {
		this.product.setCreationDate(ZonedDateTime.now().toString());
	}
	
	@Override
	public void setBPrice() throws ExitException {
		while (true) {
			try {
				System.out.print("Введите цену: ");
				var str = getNewString();
				validator.isNull(str, "Price");
				var Price = validator.isDouble(str, "Price");
				validator.isInfinite(Price, "Price");
				validator.isPositive(Price, "Price");
				this.product.setPrice(Price);
				break;
			} catch (IOException | NumberFormatException | NullPointerException | InfiniteException |
			         FalseValuesException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	@Override
	public void setBPartNumber() throws ExitException {
		while (true) {
			try {
				System.out.print("Введите номер партии: ");
				String str = getNewString();
				validator.isNull(str, "PartNumber");
				validator.isStringShort(str, 83, "PartNumber");
//                validator.isPartNumberUnique(str);
				this.product.setPartNumber(str);
				break;
			} catch (IOException | NumberFormatException | NullPointerException | FalseValuesException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	@Override
	public void setBManufactureCost() throws ExitException {
		while (true) {
			try {
				System.out.print("Введите стоимость производства: ");
				String str = getNewString();
				str = validator.emptyToZero(str);
				this.product.setManufactureCost(validator.isInteger(str, "ManufactureCost"));
				break;
			} catch (IOException | NumberFormatException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	@Override
	public void setBUnitOfMeasure() throws ExitException, IOException {
		while (true) {
			System.out.println("Введите единицу измерения из следующего списка:\nC : CENTIMETERS," +
					" L : LITERS," + " M : MILLILITERS," + " G : GRAMS");
			String string = getNewString();
			UnitOfMeasure unitOfMeasure;
			switch (string) {
				case "LITERS", "L" -> unitOfMeasure = UnitOfMeasure.LITERS;
				case "CENTIMETERS", "C" -> unitOfMeasure = UnitOfMeasure.CENTIMETERS;
				case "GRAMS", "G" -> unitOfMeasure = UnitOfMeasure.GRAMS;
				case "MILLILITERS", "M" -> unitOfMeasure = UnitOfMeasure.MILLILITERS;
				default -> {
					continue;
				}
			}
			this.product.setUnitOfMeasure(unitOfMeasure);
			break;
		}
	}
	
	@Override
	public void setBOrganization() throws ExitException, IOException {
		System.out.println("Начало ввода полей Organization\n");
		Organization organization = new Organization();

		organization.setName(setBOrganizationName());
		organization.setFullName(setBFullName());
		organization.setAnnualTurnover(setBAnnualTurnover());
		organization.setEmployeesCount(setBEmployessCount());
		organization.setType(setBType());
		
		this.product.setManufacturer(organization);
	}
	
	/**
	 * @return Product
	 */
	public Product getResult() {
		return this.product;
	}
	
	public String getNewString() throws IOException, ExitException {
		String string = br.readLine().strip();
		tryExit(string);
		return string;
	}
	
	public void tryExit(String string) throws ExitException {
		if (string.equals("/exit")) {
			throw new ExitException();
		}
	}
	
	public long setBX() throws ExitException {
		while (true) {
			try {
				System.out.print("Введите координату X: ");
				String strX = getNewString();
				tryExit(strX);
				if (strX.equals("")) {
					return 0;
				}
				long x = validator.isLong(strX, "X");
				validator.isGreater(x, -230L, "X");
				return x;
			} catch (IOException | FalseTypeException | NumberFormatException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	public long setBY() throws ExitException {
		long maxYvalue = 703L;
		while (true) {
			try {
				System.out.print("Введите координату Y: ");
				String strY = getNewString();
				tryExit(strY);
				validator.isNull(strY, "Y");
				Long y = validator.isLong(strY, "Y");
				validator.isLower(y, maxYvalue, "Y");
				return y;
			} catch (IOException | FalseTypeException | NumberFormatException | NullPointerException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	public String setBOrganizationName() throws IOException, ExitException {
		String name = "";
		while (name.equals("")) {
			System.out.println("Введите аббревиатуру названия организации: ");
			name = getNewString();
		}
		return name;
	}
	
	public String setBFullName() throws ExitException {
		while (true) {
			try {
				System.out.println("Введите полное название организации: ");
				var fullname = getNewString();
				validator.isStringShort(fullname, 1267, "FullName");
				validator.emptyToNull(fullname);
				return fullname;
			} catch (IOException | NumberFormatException | FalseValuesException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	public Integer setBAnnualTurnover() throws ExitException {
		while (true) {
			try {
				System.out.println("Введите годовой оборот: ");
				String str = getNewString();
				validator.isNull(str, "AnnualTurnover");
				var annualTurnover = validator.isInteger(str, "AnnualTurnover");
				validator.isPositive(annualTurnover, "AnnualTurnover");
				return annualTurnover;
			} catch (IOException | NumberFormatException | FalseValuesException | NullPointerException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	public Integer setBEmployessCount() throws ExitException {
		while (true) {
			System.out.println("Введите количество сотрудников: ");
			try {
				String str = getNewString();
				validator.isNull(str, "EmployessCount");
				var employessCount = validator.isInteger(str, "EmployessCount");
				validator.isPositive(employessCount, "EmployessCount");
				return employessCount;
			} catch (IOException | NumberFormatException | FalseValuesException | NullPointerException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	public OrganizationType setBType() throws IOException, ExitException {
		while (true) {
			System.out.println("Введите тип организации из следующего списка:");
			System.out.println("Существующие типы организации:\nC : COMMERCIAL," +
					" G : GOVERNMENT," + " T : TRUST," + " P : PRIVATE_LIMITED_COMPANY");
			String str = getNewString();
			OrganizationType orgType = Organization.stringToOrgType(str);
			if (orgType != null) return orgType;
		}
	}
}