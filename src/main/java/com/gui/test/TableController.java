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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class TableController implements Initializable {
	private static String username;
	private final ExecutorService executorService;
	public static boolean updatedFlag = true;
	@FXML
	public TextField nameFilterField;
	@FXML
	public Text infoMessage;
	@FXML
	public Label userField = new Label();
	@FXML
	public Label titleText;
	@FXML
	public TextField xFilterField;
	@FXML
	public TextField YFilterField;
	@FXML
	public TextField priceFilterField;
	@FXML
	public TextField partNumberFilterField;
	@FXML
	public TextField idFilterField;
	@FXML
	public TextField manufactureCostFilterField;
	@FXML
	public TextField unitOfMeasureFilterField;
	@FXML
	public TextField orgIdFilterField;
	@FXML
	public TextField orgNameFilterField;
	@FXML
	public TextField fullnameFilterField;
	@FXML
	public TextField annualTurnoverFilterField;
	@FXML
	public TextField EmployeesCountFilterField;
	@FXML
	public TextField OrganizationTypeFilterField;
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
	private static ArrayList<Product> allProducts;
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
		if (updatedFlag) {
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
	}
	
	public void buildTable() {
		var string = ResourceBundle.getBundle("LabelBundle_" + HelloApplication.locale);
		TableColumn<Product, String> idColumn = new TableColumn<>(string.getString("table.column.id"));
		idColumn.setMinWidth(20);
		idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
		
		TableColumn<Product, String> nameColumn = new TableColumn<>(string.getString("table.column.name"));
		nameColumn.setMinWidth(20);
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getName())));
		
		TableColumn<Product, String> coordinatesXColumn = new TableColumn<>(string.getString("table.column.x"));
		coordinatesXColumn.setMinWidth(20);
		coordinatesXColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCoordinates().getX())));
		
		TableColumn<Product, String> coordinatesYColumn = new TableColumn<>(string.getString("table.column.y"));
		coordinatesYColumn.setMinWidth(20);
		coordinatesYColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCoordinates().getY())));
		
		TableColumn<Product, String> creationDateColumn = new TableColumn<>(string.getString("table.column.creationDate"));
		creationDateColumn.setMinWidth(20);
		creationDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCreationDate())));
		
		TableColumn<Product, String> priceColumn = new TableColumn<>(string.getString("table.column.price"));
		priceColumn.setMinWidth(20);
		priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
		
		TableColumn<Product, String> partNumberColumn = new TableColumn<>(string.getString("table.column.partNumber"));
		partNumberColumn.setMinWidth(20);
		partNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPartNumber())));
		
		TableColumn<Product, String> manufacturerCostColumn = new TableColumn<>(string.getString("table.column.manufacturerCost"));
		manufacturerCostColumn.setMinWidth(20);
		manufacturerCostColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufactureCost())));
		
		TableColumn<Product, String> unitOfMeasureColumn = new TableColumn<>(string.getString("table.column.unitOfMeasure"));
		unitOfMeasureColumn.setMinWidth(20);
		unitOfMeasureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUnitOfMeasure())));
		
		TableColumn<Product, String> organizationIdColumn = new TableColumn<>(string.getString("table.column.organizationId"));
		organizationIdColumn.setMinWidth(20);
		organizationIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getId())));
		
		TableColumn<Product, String> organizationNameColumn = new TableColumn<>(string.getString("table.column.organizationName"));
		organizationNameColumn.setMinWidth(20);
		organizationNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturer().getName()));
		
		TableColumn<Product, String> organizationFullNameColumn = new TableColumn<>(string.getString("table.column.organizationFullName"));
		organizationFullNameColumn.setMinWidth(20);
		organizationFullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturer().getFullName()));
		
		TableColumn<Product, String> organizationAnnualTurnoverColumn = new TableColumn<>(string.getString("table.column.organizationAnnualTurnover"));
		organizationAnnualTurnoverColumn.setMinWidth(20);
		organizationAnnualTurnoverColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getAnnualTurnover())));
		
		TableColumn<Product, String> organizationEmployeesCountColumn = new TableColumn<>(string.getString("table.column.organizationEmployeesCount"));
		organizationEmployeesCountColumn.setMinWidth(20);
		organizationEmployeesCountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getEmployeesCount())));
		
		TableColumn<Product, String> organizationTypeColumn = new TableColumn<>(string.getString("table.column.organizationType"));
		organizationTypeColumn.setMinWidth(20);
		organizationTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getManufacturer().getType())));
		
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
	
	public static ArrayList<Product> getAllProduct() {
		return allProducts;
	}
	
	@FXML
	public void filterByName () {
		String filter = nameFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (product.getName().equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			nameFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByX() {
		String filter = xFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getCoordinates().getX()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			xFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByY() {
		String filter = YFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getCoordinates().getY()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			YFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByPrice() {
		String filter = priceFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getPrice()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			priceFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByPartNumber() {
		String filter = partNumberFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (product.getPartNumber().equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			partNumberFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByManufactureCost(ActionEvent event) {
		String filter = manufactureCostFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getManufactureCost()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			manufactureCostFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterById(ActionEvent event) {
		String filter = idFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getId()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			idFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByUnitOfMeasure(ActionEvent event) {
		String filter = unitOfMeasureFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getUnitOfMeasure()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			unitOfMeasureFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByOrgId(ActionEvent event) {
		String filter = orgIdFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getManufacturer().getId()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			orgIdFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByOrgName(ActionEvent event) {
		String filter = orgNameFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getManufacturer().getName()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			orgNameFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByFullname(ActionEvent event) {
		String filter = fullnameFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (product.getManufacturer().getFullName().equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			fullnameFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByAnnualTurnover(ActionEvent event) {
		String filter = annualTurnoverFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if (String.valueOf(product.getManufacturer().getAnnualTurnover()).equals(filter)) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			annualTurnoverFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByEmployeesCount(ActionEvent event) {
		String filter = EmployeesCountFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if ((String.valueOf(product.getManufacturer().getEmployeesCount()).equals(filter))) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			EmployeesCountFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
	@FXML
	public void filterByOrganizationType(ActionEvent event) {
		String filter = OrganizationTypeFilterField.getText();
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendProductsRequest(clientSocket, address, "", "");
			
			Response response = RecipientChunk.getResponse(clientSocket);
			var collection = response.getProducts();
			ArrayList<Product> groupList = new ArrayList<>(collection.values());
			ArrayList<Product> filteredGroupList = new ArrayList<>();
			
			for (Product product : groupList) {
				if ((String.valueOf(product.getManufacturer().getType()).equals(filter))) {
					filteredGroupList.add(product);
				}
			}
			
			ObservableList<Product> observableList = getProducts(filteredGroupList);
			tableView.setItems(observableList);
			
			updatedFlag = false;
			
			OrganizationTypeFilterField.clear();
		} catch (IOException | ClassNotFoundException exception) {
			setErrorMessage(exception.getMessage());
		}
	}
}