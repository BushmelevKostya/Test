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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
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
	private Button LocalizationButton;
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
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
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
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("id-form.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
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
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("id-form-2.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
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
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view-2.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
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
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view-3.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
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
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("form-view-4.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
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
		ArrayList<Integer> angles = new ArrayList<>();
		ArrayList<Integer> tipz = new ArrayList<>();
		ArrayList<Integer> tlpy = new ArrayList<>();
		ArrayList<Product> products = TableController.getAllProduct();
		for (Product product : products) {
			angles.add((int) (Math.random() * 360));
			tipz.add(((int) product.getCoordinates().getX()) * 10);
			tlpy.add(Math.toIntExact(product.getCoordinates().getY()) * 10);
		}
		
		animation.start(new Stage(), angles, tipz, tlpy, products);
	}
	
	@FXML
	public void localization() {
		try {
			if (HelloApplication.locale.equals("en")) HelloApplication.locale = "ru";
			else if (HelloApplication.locale.equals("ru")) HelloApplication.locale = "sk";
			else if (HelloApplication.locale.equals("sk")) HelloApplication.locale = "gr";
			else if (HelloApplication.locale.equals("gr")) HelloApplication.locale = "es";
			else if (HelloApplication.locale.equals("es")) HelloApplication.locale = "en";
			var window = LocalizationButton.getScene().getWindow();
			Stage stage = WindowController.getStageFromWindow(window);
			TranslationBundles.setLanguage(new Locale(HelloApplication.locale));
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-view.fxml"));
			fxmlLoader.setResources(TranslationBundles.getBundle());
			Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
			stage.setTitle("Products");
			stage.setScene(scene);
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
}
