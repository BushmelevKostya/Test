package com.gui.test;

import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.ZonedDateTime;

public class FormViewController {
	@FXML
	public Text infoMessage;
	@FXML
	public Text errorMessage;
	@FXML
	public TextField nameField;
	@FXML
	private VBox formLayout;
	@FXML
	private TextField xField;
	@FXML
	private TextField yField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField partNumberField;
	@FXML
	private TextField manufactureCostField;
	@FXML
	private ComboBox<String> unitComboBox;
	@FXML
	private TextField organizationNameField;
	@FXML
	private TextField organizationFullnameField;
	@FXML
	private TextField annualTurnoverField;
	@FXML
	private TextField employeesCountField;
	@FXML
	private ComboBox<String> organizationTypeComboBox;
	@FXML
	private Button addButtonForm;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	private void add() {
		// Логика добавления
		String name = nameField.getText();
		long x = Long.parseLong(xField.getText());
		long y = Long.parseLong(yField.getText());
		double price = Double.parseDouble(priceField.getText());
		String partNumber = partNumberField.getText();
		int manufactureCost = Integer.parseInt(manufactureCostField.getText());
		UnitOfMeasure unit = UnitOfMeasure.valueOf(unitComboBox.getValue());
		String organizationName = organizationNameField.getText();
		String organizationFullname = organizationFullnameField.getText();
		int annualTurnover = Integer.parseInt(annualTurnoverField.getText());
		int employeesCount = Integer.parseInt(employeesCountField.getText());
		OrganizationType organizationType = OrganizationType.valueOf(organizationTypeComboBox.getValue());
		
		String commandName = "insert";
		String value = null;
		ClientExecutor clientExecutor = new ClientExecutor();
		
		String creationDate = String.valueOf(ZonedDateTime.now());
		Coordinates coordinates = new Coordinates(x, y);
		Organization organization = new Organization(1, organizationName, organizationFullname, annualTurnover, employeesCount, organizationType);
		Product product = new Product(1, name, coordinates, creationDate, price, partNumber, manufactureCost, unit, organization);
		
		try{
			clientExecutor.perform(product, commandName, value);
		}catch (IOException | UniqueException | NullStringException | FalseTypeException | InfiniteException |
		        ExitException | FalseValuesException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
		
		// Очистка полей после добавления
		nameField.clear();
		xField.clear();
		yField.clear();
		priceField.clear();
		partNumberField.clear();
		manufactureCostField.clear();
		unitComboBox.getSelectionModel().clearSelection();
		organizationNameField.clear();
		organizationFullnameField.clear();
		annualTurnoverField.clear();
		employeesCountField.clear();
		organizationTypeComboBox.getSelectionModel().clearSelection();
	}
	private void setErrorMessage(String message) {
		infoMessage.setManaged(false);
		errorMessage.setManaged(true);
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
}
