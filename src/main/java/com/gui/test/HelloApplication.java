package com.gui.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class HelloApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		TranslationBundles.setLanguage(new Locale("ru"));
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-in-view.fxml"));
		fxmlLoader.setResources(TranslationBundles.getBundle());
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}