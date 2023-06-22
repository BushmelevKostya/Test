module com.gui.test {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.jwt;
	requires com.google.gson;
	
	
	opens com.gui.test to javafx.fxml;
	exports com.gui.test;
}