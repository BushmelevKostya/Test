package com.gui.test;

import com.gui.test.client.Authorizer;
import com.gui.test.client.ClientExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public class SignInController {
	@FXML
	public Text infoMessage;
	Window window;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button signInButton;
	@FXML
	private Text errorMessage;
	
	@FXML
	private void signIn(ActionEvent event) {
		var username = this.usernameField.getText();
		var password = this.passwordField.getText();
		
		if (validate(username, password)) {
			try {
				Authorizer authorizer = new Authorizer();
				String name = authorizer.sendAuthorizeRequest(password, username);
				if (name.equals("")) setErrorMessage("Данные введены неверно!");
				else {
					TableController.setUserField(username);
					changeScene();
				}
			} catch (IOException | ClassNotFoundException exception) {
				setErrorMessage(exception.getMessage());
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}
	
	@FXML
	private void showSignUpStage() {
		Stage stage = (Stage) signInButton.getScene().getWindow();
//		try {
//			ViewsManager.showSignUpView(stage);
//		} catch (IOException e) {
//			showErrorMessage("Ошибка загрузки страницы");
//		}
	}
	
	@FXML
	public void onEnter(ActionEvent actionEvent) {
		this.signIn(actionEvent);
	}
	
	private boolean validate(String login, String password) {
		if (login.equals("")) {
			setErrorMessage("Логин не может быть пустой строкой!");
			return false;
		}
		
		if (password.equals("")) {
			setErrorMessage("Пароль не может быть пустой строкой!");
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
		var window = signInButton.getScene().getWindow();
		Stage stage = WindowController.getStageFromWindow(window);
		TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-view.fxml"));
		fxmlLoader.setResources(TranslationBundles.getBundle());
		Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
		stage.setTitle("Products");
		stage.setScene(scene);
	}
}
