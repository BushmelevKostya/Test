package com.gui.test;

import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class IdFormController2 {
	@FXML
	public Text infoMessage;
	@FXML
	public Text errorMessage;
	@FXML
	private TextField idField;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private void submit() {
		String id = idField.getText();
		ClientExecutor clientExecutor = ClientExecutor.getClientExecutor();
		String commandName = "remove_lower_key";
		String value = id;
		Product product = null;
		try{
			clientExecutor.perform(product, commandName, value);
		} catch (IOException | UniqueException | NullStringException | FalseTypeException | InfiniteException |
		         ExitException | FalseValuesException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
		idField.clear();
	}
	private void setErrorMessage(String message) {
		infoMessage.setManaged(false);
		errorMessage.setManaged(true);
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
}

