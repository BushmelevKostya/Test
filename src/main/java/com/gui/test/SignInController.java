package com.gui.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
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
		
//		if (this.validate(username, password)) {
//			signInRequest(username, password, event);
////            this.executorService.submit(() -> signInRequest(username, password, event));
//		}
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
}
