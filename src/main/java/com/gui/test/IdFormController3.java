package com.gui.test;

import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class IdFormController3 {
	@FXML
	public Text infoMessage;
	@FXML
	public Text errorMessage;
	@FXML
	private TextField fileNameField;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private void submit() {
		String filename = fileNameField.getText();
		var clientExecutor = ClientExecutor.getClientExecutor();
		clientExecutor.runScript(filename);
		fileNameField.clear();
	}
	private void setErrorMessage(String message) {
		infoMessage.setManaged(false);
		errorMessage.setManaged(true);
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
}

