package com.gui.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class CommandsController implements Serializable {
	@FXML
	public Text infoMessage;
	@FXML
	public Text errorMessage;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private Button executeButton;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button signInButton;
	
	@FXML
	private void add() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(executeButton.getScene().getWindow());
			formStage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 300);
			formStage.setTitle("Insert");
			formStage.setScene(scene);
			
			formStage.show();
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	
	private void setErrorMessage(String message) {
		infoMessage.setManaged(false);
		errorMessage.setManaged(true);
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
	
	@FXML
	private void clear() {
	
	}
	
	@FXML
	private void remove() {
	
	}
	
	@FXML
	private void removeLowerKey() {
	
	}
	
	@FXML
	private void replaceIfGreater() {
	
	}
	
	@FXML
	private void replaceIfLower() {
	
	}
	
	@FXML
	private void update() {
	
	}
	
	@FXML
	private void handleComboBoxAction() {
	
	}
}
