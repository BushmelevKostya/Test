package com.gui.test;

import com.gui.test.client.Authorizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Locale;

public class RegisterController {
	@FXML
	public Text infoMessage;
	Window window;
	@FXML
	private TextField nameField;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField repeatPasswordField;
	@FXML
	private Button registerButton;
	@FXML
	private Button signInButton;
	@FXML
	private Text errorMessage;
	
	
	@FXML
	private void register(ActionEvent actionEvent) {
		var username = this.usernameField.getText();
		var name = this.nameField.getText();
		var password = this.passwordField.getText();
		var repeatPassword = this.repeatPasswordField.getText();
		
		if (validate(username, name, password, repeatPassword)) {
			try {
				Authorizer authorizer = new Authorizer();
				authorizer.sendRegisterRequest(password, name, username);
				TableController.setUserField(username);
				changeScene();
			} catch (IOException | ClassNotFoundException exception) {
				setErrorMessage(exception.getMessage());
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}
	
	@FXML
	public void onEnter(ActionEvent actionEvent) {
		this.register(actionEvent);
	}
	
	private boolean validate(String login, String name, String password, String repeatPassword) {
		if (login.length() < 4 || login.length() > 10) {
			setErrorMessage("Логин должен содержать от 4 до 10 символов");
			return false;
		}
		
		if (name.length() < 1 || name.length() > 10) {
			setErrorMessage("Имя должно содержать от 1 до 10 символов!");
			return false;
		}
		
		if (password.contains(" ")) setErrorMessage("Пароль не должен включать в себя пробел!");
		else if (password.length() < 4 || password.length() > 10) {
			setErrorMessage("Пароль должен содержать от 4  до 10 символов!");
			return false;
		} else if (!repeatPassword.equals(password)) {
			setErrorMessage("Пароли не совпадают!");
			return false;
		}
		return true;
	}
	
	private void setErrorMessage(String message) {
		infoMessage.setManaged(false);
		errorMessage.setManaged(true);
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
	
	public void changeScene() throws IOException {
		showProduct(registerButton);
	}
	
	public static void showProduct(Button registerButton) throws IOException{
		var window = registerButton.getScene().getWindow();
		Stage stage = WindowController.getStageFromWindow(window);
		TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-view.fxml"));
		fxmlLoader.setResources(TranslationBundles.getBundle());
		Scene scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Products");
		stage.setScene(scene);
		stage.setMaximized(true);
	}
}
