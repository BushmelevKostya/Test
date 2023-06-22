package com.gui.test.server.commands;

import com.gui.test.common.exception.FalseValuesException;
import com.gui.test.common.exception.NoElementException;
import com.gui.test.common.product.Product;
import com.gui.test.server.database.DBParser;
import com.gui.test.server.database.Migrations;

import java.sql.SQLException;
import java.util.Arrays;

public abstract class ServerCommand {
	private String errorMessage = "Команда выполнена без ошибок!";
	protected String response = "";
	protected final Migrations migrations = DBParser.migrations;
	
	public ServerCommand() {
	}
	
	public abstract String execute(Integer id, Product product, String login) throws FalseValuesException, SQLException;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void addToResponse(String response) {
		this.response += response;
	}
}
