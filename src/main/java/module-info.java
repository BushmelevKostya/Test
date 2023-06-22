module com.gui.test {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	opens com.gui.test to javafx.fxml;
	exports com.gui.test;
}