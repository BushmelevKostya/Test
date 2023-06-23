package com.gui.test;

import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
	private Button AnimationButton;
	@FXML
	private Button insertButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button RemoveButton;
	@FXML
	private Button RemoveLowerKeyButton;
	@FXML
	private Button ReplaceIfGreaterButton;
	@FXML
	private Button ReplaceIfLowerButton;
	@FXML
	private Button UpdateButton;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button signInButton;
	
	@FXML
	private void insert() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(insertButton.getScene().getWindow());
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
		String commandName = "clear";
		String value = null;
		Product product = null;
		ClientExecutor clientExecutor = ClientExecutor.getClientExecutor();
		try{
			clientExecutor.perform(product, commandName, value);
		} catch (IOException | UniqueException | NullStringException | FalseTypeException | InfiniteException |
		         ExitException | FalseValuesException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	
	@FXML
	private void remove() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(insertButton.getScene().getWindow());
			formStage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("id-form.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 300);
			formStage.setTitle("Id");
			formStage.setScene(scene);
			
			formStage.show();
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	
	@FXML
	private void removeLowerKey() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(insertButton.getScene().getWindow());
			formStage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("id-form-2.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 300);
			formStage.setTitle("Id");
			formStage.setScene(scene);
			
			formStage.show();
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	
	@FXML
	private void replaceIfGreater() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(insertButton.getScene().getWindow());
			formStage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view-2.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 300);
			formStage.setTitle("Id");
			formStage.setScene(scene);
			
			formStage.show();
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	
	@FXML
	private void replaceIfLower() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(insertButton.getScene().getWindow());
			formStage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view-3.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 300);
			formStage.setTitle("Id");
			formStage.setScene(scene);
			
			formStage.show();
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	
	@FXML
	private void update() {
		try {
			Stage formStage = new Stage();
			formStage.initOwner(insertButton.getScene().getWindow());
			formStage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view-4.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 300);
			formStage.setTitle("Id");
			formStage.setScene(scene);
			
			formStage.show();
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void animation() {
		Animation animation = new Animation();
		animation.start(new Stage());
	}
}
