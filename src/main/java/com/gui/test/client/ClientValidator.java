package com.gui.test.client;

import com.gui.test.common.product.Coordinates;
import com.gui.test.common.product.Organization;
import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.common.CommandWithParameters;
import com.gui.test.common.CommandWithoutParameters;
import com.gui.test.common.exception.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

public class ClientValidator {
	public ClientValidator() {
	}
	
	public boolean check(String data) {
		try {
			var list = new Reader(data).getData();
			if (list.length > 2) {
				System.out.println("Введено слишком много аргументов!");
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException ignored) {
		}
		return true;
	}
	
	public boolean checkCommand(String command) {
		boolean exists = true;
		try {
			CommandWithoutParameters.valueOf(command);
		} catch (IllegalArgumentException exception) {
			try {
				CommandWithParameters.valueOf(command);
			} catch (IllegalArgumentException e) {
				exists = false;
				if (!command.equals("")) {
					System.out.println("Команды " + command + " не существует!");
				}
			}
		}
		return exists;
	}
	
	public boolean checkParam(String command, String value) {
		if (value == null) {
			try {
				CommandWithoutParameters.valueOf(command);
			} catch (IllegalArgumentException exception) {
				System.out.println("Этой команде необходимо передать параметр!");
				return false;
			}
		} else {
			try {
				CommandWithParameters.valueOf(command);
			} catch (IllegalArgumentException exception) {
				System.out.println("У этой команды нет параметров!");
				return false;
			}
		}
		return true;
	}
	
	public Integer checkParamType(String value) throws NumberFormatException, FalseTypeException {
		try {
			var id = Integer.parseInt(value);
			if (id <= 0) throw new FalseTypeException("Значение id должно быть положительным!");
			return id;
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Значение id должно быть типа int!");
		}
	}
	
	public void isNull(String str, String field) throws NullPointerException {
		if ("".equals(str)) {
			throw new NullPointerException("Поле " + field + " не может быть null!");
		}
	}
	
	public void isPositive(Double value, String field) throws FalseValuesException {
		if (value <= 0) {
			throw new FalseValuesException("Поле " + field + " должно быть положительным");
		}
	}
	
	public void isPositive(Integer value, String field) throws FalseValuesException {
		if (value <= 0) {
			throw new FalseValuesException("Поле " + field + " должно быть положительным");
		}
	}
	
	public void isPositive(String value, String field) throws FalseValuesException {
		try {
			int id = Integer.parseInt(value);
			if (id <= 0) {
				throw new FalseValuesException("Поле " + field + " должно быть положительным");
			}
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Значение Id должно быть типа int!");
		}
	}
	
	public Double isDouble(String value, String field) throws NumberFormatException {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Значение " + field + " должно быть типа double!");
		}
	}
	
	public Integer isInteger(String value, String field) throws NumberFormatException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Значение " + field + " должно быть типа int!");
		}
	}
	
	public Long isLong(String value, String field) throws NumberFormatException {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Значение " + field + " должно быть типа long!");
		}
	}
	
	public void isInfinite(Double value, String field) throws InfiniteException {
		if (value.isInfinite()) {
			throw new InfiniteException("Введено слишком большое значение, переполнение поля " + field + " !");
		}
	}
	
	public String emptyToZero(String value) {
		if (value.equals("")) {
			value = "0";
		}
		return value;
	}
	
	public String emptyToNull(String value) {
		if (value.equals("")) {
			value = null;
		}
		return value;
	}
	
	public void isGreater(Integer value, Integer number, String field) throws FalseTypeException {
		if (value <= number) {
			throw new FalseTypeException(field + " должен быть > " + number);
		}
	}
	
	public void isLower(Integer value, Integer number, String field) throws FalseTypeException {
		if (value >= number) {
			throw new FalseTypeException(field + " должен быть < " + number);
		}
	}
	
	public void isGreater(Long value, Long number, String field) throws FalseTypeException {
		if (value <= number) {
			throw new FalseTypeException(field + " должен быть > " + number);
		}
	}
	
	public void isLower(Long value, Long number, String field) throws FalseTypeException {
		if (value >= number) {
			throw new FalseTypeException(field + " должен быть < " + number);
		}
	}
	
	public void isGreater(Double value, Double number, String field) throws FalseTypeException {
		if (value <= number) {
			throw new FalseTypeException(field + " должен быть > " + number);
		}
	}
	
	public void isLower(Double value, Double number, String field) throws FalseTypeException {
		if (value >= number) {
			throw new FalseTypeException(field + " должен быть < " + number);
		}
	}
	
	public void isStringShort(String value, int number, String field) throws FalseValuesException {
		if (value.length() >= number) {
			throw new FalseValuesException("Длина " + field + " должна быть < " + number);
		}
	}
}
