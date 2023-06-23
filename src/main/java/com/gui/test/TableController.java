/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gui.test;

import com.gui.test.common.RecipientChunk;
import com.gui.test.common.Response;
import com.gui.test.common.SenderChunk;
import com.gui.test.common.product.Product;
import com.gui.test.common.product.UnitOfMeasure;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.security.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class TableController implements Initializable {
	private static String username;
	private final ExecutorService executorService;
	@FXML
	public Text infoMessage;
	@FXML
	public Label userField = new Label();
	@FXML
	public Label titleText;
	@FXML
	private TextField filterTextField;
	@FXML
	private ComboBox<String> sortComboBox;
	@FXML
	public VBox sidebar;
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
	private VBox commands;
	@FXML
	private VBox formLayout;
	@FXML
	private TextField nameField;
	@FXML
	private TextField xField;
	@FXML
	private ComboBox<String> unitComboBox;
	@FXML
	private Button addButtonForm;
	@FXML
	private TableView<Product> tableView;
	private ArrayList<Product> allProducts;
	public TableController() {;
		executorService = Executors.newSingleThreadExecutor();
	}
	@Override
	public void initialize(URL url, ResourceBundle rb)  {
		userField.setText(username);
		buildTable();
		Thread thread = new Thread(() -> {
			while (true) {
				executorService.submit(this::loadProducts);
				try {
					sleep(1000);
				} catch (InterruptedException exception) {
					setErrorMessage(exception.getMessage());
				}
			}
		});
		thread.start();
		
//		filterTextField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter(newValue));
//		sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> applySort(newValue));
	}
	public void loadProducts() {
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			allProducts = groupList;
			ObservableList<Product> observableList = getProducts(groupList);
			tableView.setItems(observableList);
		} catch (IOException | ClassNotFoundException exception) {
		setErrorMessage(exception.getMessage());
		}
	}
	
	private void applyFilter(String filterValue) {
		Predicate<Product> filterPredicate = createFilterPredicate(filterValue);
		List<Product> filteredList = allProducts.stream()
				.filter(filterPredicate)
				.collect(Collectors.toList());
		tableView.setItems(FXCollections.observableArrayList(filteredList));
	}
	
	private Predicate<Product> createFilterPredicate(String filterValue) {
		return product -> {
			if (filterValue == null || filterValue.isEmpty()) {
				return true; // No filter, return all
			}
			
			String lowerCaseFilterValue = filterValue.toLowerCase();
			
			return product.getName().toLowerCase().contains(lowerCaseFilterValue)
					|| product.getManufacturer().getName().toLowerCase().contains(lowerCaseFilterValue)
					|| product.getManufacturer().getFullName().toLowerCase().contains(lowerCaseFilterValue);
		};
	}
	
	private void applySort(String columnName) {
		if (columnName == null || columnName.isEmpty()) {
			return; // No column selected, do nothing
		}
		
		Comparator<Product> comparator;
		switch (columnName) {
			case "ID":
				comparator = Comparator.comparing(Product::getId);
				break;
			case "Name":
				comparator = Comparator.comparing(Product::getName);
				break;
			case "Price":
				comparator = Comparator.comparing(Product::getPrice);
				break;
			default:
				return; // Invalid column selected, do nothing
		}
		
		List<Product> sortedList = allProducts.stream()
				.sorted(comparator)
				.collect(Collectors.toList());
		tableView.setItems(FXCollections.observableArrayList(sortedList));
	}
	
	private void showInfoMessage(String message) {
		infoMessage.setVisible(true);
		infoMessage.setText(message);
	}
	
	private void hideInfoMessage() {
		infoMessage.setVisible(false);
	}
	
	private void showErrorMessage(String message) {
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
	
	private void hideErrorMessage() {
		errorMessage.setVisible(false);
	}
	
	public void buildTable() {
		TableColumn<Product, String> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(20);
		idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
		
		TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(20);
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getName())));
		
		TableColumn<Product, String> coordinatesXColumn = new TableColumn<>("X");
		coordinatesXColumn.setMinWidth(20);
		coordinatesXColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCoordinates().getX())));
		
		TableColumn<Product, String> coordinatesYColumn = new TableColumn<>("Y");
		coordinatesYColumn.setMinWidth(20);
		coordinatesYColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCoordinates().getX())));
		
		TableColumn<Product, String> creationDateColumn = new TableColumn<>("Creation Date");
		creationDateColumn.setMinWidth(20);
		creationDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCreationDate())));
		
		TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(20);
		priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
		
		TableColumn<Product, String> partNumberColumn = new TableColumn<>("Part Number");
		partNumberColumn.setMinWidth(20);
		partNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPartNumber())));
		
		TableColumn<Product, String> manufacturerCostColumn = new TableColumn<>("Manufacturer Cost");
		manufacturerCostColumn.setMinWidth(20);
		manufacturerCostColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufactureCost())));
		
		TableColumn<Product, String> unitOfMeasureColumn = new TableColumn<>("Unit of Measure");
		unitOfMeasureColumn.setMinWidth(20);
		unitOfMeasureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUnitOfMeasure())));
		
		TableColumn<Product, String> organizationIdColumn = new TableColumn<>("Organization Id");
		organizationIdColumn.setMinWidth(20);
		organizationIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getId())));
		
		TableColumn<Product, String> organizationNameColumn = new TableColumn<>("Organization Name");
		organizationNameColumn.setMinWidth(20);
		organizationNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturer().getName()));
		
		TableColumn<Product, String> organizationFullNameColumn = new TableColumn<>("Full Name");
		organizationFullNameColumn.setMinWidth(20);
		organizationFullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturer().getFullName()));
		
		TableColumn<Product, String> organizationAnnualTurnoverColumn = new TableColumn<>("Annual Turnover");
		organizationAnnualTurnoverColumn.setMinWidth(20);
		organizationAnnualTurnoverColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getAnnualTurnover())));
		
		TableColumn<Product, String> organizationEmployeesCountColumn = new TableColumn<>("Employees Count");
		organizationEmployeesCountColumn.setMinWidth(20);
		organizationEmployeesCountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getEmployeesCount())));
		
		TableColumn<Product, String> organizationTypeColumn = new TableColumn<>("Organization Type");
		organizationTypeColumn.setMinWidth(20);
		organizationTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer())));
		
		tableView.getColumns().addAll(idColumn, nameColumn, coordinatesXColumn, coordinatesYColumn, creationDateColumn, priceColumn, partNumberColumn, manufacturerCostColumn, unitOfMeasureColumn, organizationIdColumn, organizationNameColumn, organizationFullNameColumn, organizationAnnualTurnoverColumn, organizationEmployeesCountColumn, organizationTypeColumn);
	}
	
	private ObservableList<Product> getProducts(List<Product> list) {
		return FXCollections.observableList(list);
	}
	
	private void setErrorMessage(String message) {
		infoMessage.setManaged(false);
		errorMessage.setManaged(true);
		errorMessage.setVisible(true);
		errorMessage.setText(message);
	}
	
	public static void setUserField(String username) {
		TableController.username = username;
	}
}