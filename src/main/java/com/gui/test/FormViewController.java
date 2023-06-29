package com.gui.test;

import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Thread.sleep;

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
	private Map<String, KeyCode> codesMap;
	
	@FXML
	private void initialize() {
		codesMap = new TreeMap<>() {{
			put("Enter", KeyCode.ENTER);
			put("Backspace", KeyCode.BACK_SPACE);
			put("Tab", KeyCode.TAB);
			put("Cancel", KeyCode.CANCEL);
			put("Clear", KeyCode.CLEAR);
			put("Shift", KeyCode.SHIFT);
			put("Ctrl", KeyCode.CONTROL);
			put("Alt", KeyCode.ALT);
			put("Pause", KeyCode.PAUSE);
			put("Caps Lock", KeyCode.CAPS);
			put("Esc", KeyCode.ESCAPE);
			put("Space", KeyCode.SPACE);
			put("Page Up", KeyCode.PAGE_UP);
			put("Page Down", KeyCode.PAGE_DOWN);
			put("End", KeyCode.END);
			put("Home", KeyCode.HOME);
			put("Left", KeyCode.LEFT);
			put("Up", KeyCode.UP);
			put("Right", KeyCode.RIGHT);
			put("Down", KeyCode.DOWN);
			put("Comma", KeyCode.COMMA);
			put("Minus", KeyCode.MINUS);
			put("Period", KeyCode.PERIOD);
			put("Slash", KeyCode.SLASH);
			put("0", KeyCode.DIGIT0);
			put("1", KeyCode.DIGIT1);
			put("2", KeyCode.DIGIT2);
			put("3", KeyCode.DIGIT3);
			put("4", KeyCode.DIGIT4);
			put("5", KeyCode.DIGIT5);
			put("6", KeyCode.DIGIT6);
			put("7", KeyCode.DIGIT7);
			put("8", KeyCode.DIGIT8);
			put("9", KeyCode.DIGIT9);
			put("Semicolon", KeyCode.SEMICOLON);
			put("Equals", KeyCode.EQUALS);
			put("A", KeyCode.A);
			put("B", KeyCode.B);
			put("C", KeyCode.C);
			put("D", KeyCode.D);
			put("E", KeyCode.E);
			put("F", KeyCode.F);
			put("G", KeyCode.G);
			put("H", KeyCode.H);
			put("I", KeyCode.I);
			put("J", KeyCode.J);
			put("K", KeyCode.K);
			put("L", KeyCode.L);
			put("M", KeyCode.M);
			put("N", KeyCode.N);
			put("O", KeyCode.O);
			put("P", KeyCode.P);
			put("Q", KeyCode.Q);
			put("R", KeyCode.R);
			put("S", KeyCode.S);
			put("T", KeyCode.T);
			put("U", KeyCode.U);
			put("V", KeyCode.V);
			put("W", KeyCode.W);
			put("X", KeyCode.X);
			put("Y", KeyCode.Y);
			put("Z", KeyCode.Z);
			put("Open Bracket", KeyCode.OPEN_BRACKET);
			put("Back Slash", KeyCode.BACK_SLASH);
			put("Close Bracket", KeyCode.CLOSE_BRACKET);
			put("Numpad 0", KeyCode.NUMPAD0);
			put("Numpad 1", KeyCode.NUMPAD1);
			put("Numpad 2", KeyCode.NUMPAD2);
			put("Numpad 3", KeyCode.NUMPAD3);
			put("Numpad 4", KeyCode.NUMPAD4);
			put("Numpad 5", KeyCode.NUMPAD5);
			put("Numpad 6", KeyCode.NUMPAD6);
			put("Numpad 7", KeyCode.NUMPAD7);
			put("Numpad 8", KeyCode.NUMPAD8);
			put("Numpad 9", KeyCode.NUMPAD9);
			put("Multiply", KeyCode.MULTIPLY);
			put("Add", KeyCode.ADD);
			put("Separator", KeyCode.SEPARATOR);
			put("Subtract", KeyCode.SUBTRACT);
			put("Decimal", KeyCode.DECIMAL);
			put("Divide", KeyCode.DIVIDE);
			put("Delete", KeyCode.DELETE);
			put("Num Lock", KeyCode.NUM_LOCK);
			put("Scroll Lock", KeyCode.SCROLL_LOCK);
			put("F1", KeyCode.F1);
			put("F2", KeyCode.F2);
			put("F3", KeyCode.F3);
			put("F4", KeyCode.F4);
			put("F5", KeyCode.F5);
			put("F6", KeyCode.F6);
			put("F7", KeyCode.F7);
			put("F8", KeyCode.F8);
			put("F9", KeyCode.F9);
			put("F10", KeyCode.F10);
			put("F11", KeyCode.F11);
			put("F12", KeyCode.F12);
			put("F13", KeyCode.F13);
			put("F14", KeyCode.F14);
			put("F15", KeyCode.F15);
			put("F16", KeyCode.F16);
			put("F17", KeyCode.F17);
			put("F18", KeyCode.F18);
			put("F19", KeyCode.F19);
			put("F20", KeyCode.F20);
			put("F21", KeyCode.F21);
			put("F22", KeyCode.F22);
			put("F23", KeyCode.F23);
			put("F24", KeyCode.F24);
			put("Print Screen", KeyCode.PRINTSCREEN);
			put("Insert", KeyCode.INSERT);
			put("Help", KeyCode.HELP);
			put("Meta", KeyCode.META);
			put("Back Quote", KeyCode.BACK_QUOTE);
			put("Quote", KeyCode.QUOTE);
			put("Numpad Up", KeyCode.KP_UP);
			put("Numpad Down", KeyCode.KP_DOWN);
			put("Numpad Left", KeyCode.KP_LEFT);
			put("Numpad Right", KeyCode.KP_RIGHT);
			put("Dead Grave", KeyCode.DEAD_GRAVE);
			put("Dead Acute", KeyCode.DEAD_ACUTE);
			put("Dead Circumflex", KeyCode.DEAD_CIRCUMFLEX);
			put("Dead Tilde", KeyCode.DEAD_TILDE);
			put("Dead Macron", KeyCode.DEAD_MACRON);
			put("Dead Breve", KeyCode.DEAD_BREVE);
			put("Dead Abovedot", KeyCode.DEAD_ABOVEDOT);
			put("Dead Diaeresis", KeyCode.DEAD_DIAERESIS);
			put("Dead Abovering", KeyCode.DEAD_ABOVERING);
			put("Dead Doubleacute", KeyCode.DEAD_DOUBLEACUTE);
			put("Dead Caron", KeyCode.DEAD_CARON);
			put("Dead Cedilla", KeyCode.DEAD_CEDILLA);
			put("Dead Ogonek", KeyCode.DEAD_OGONEK);
			put("Dead Iota", KeyCode.DEAD_IOTA);
			put("Dead Voiced Sound", KeyCode.DEAD_VOICED_SOUND);
			put("Dead Semivoiced Sound", KeyCode.DEAD_SEMIVOICED_SOUND);
			put("Ampersand", KeyCode.AMPERSAND);
			put("Asterisk", KeyCode.ASTERISK);
			put("Double Quote", KeyCode.QUOTEDBL);
			put("Less", KeyCode.LESS);
			put("Greater", KeyCode.GREATER);
			put("Left Brace", KeyCode.BRACELEFT);
			put("Right Brace", KeyCode.BRACERIGHT);
			put("At", KeyCode.AT);
			put("Colon", KeyCode.COLON);
			put("Circumflex", KeyCode.CIRCUMFLEX);
			put("Dollar", KeyCode.DOLLAR);
			put("Euro Sign", KeyCode.EURO_SIGN);
			put("Exclamation Mark", KeyCode.EXCLAMATION_MARK);
			put("Inverted Exclamation Mark", KeyCode.INVERTED_EXCLAMATION_MARK);
			put("Left Parenthesis", KeyCode.LEFT_PARENTHESIS);
			put("Number Sign", KeyCode.NUMBER_SIGN);
			put("Plus", KeyCode.PLUS);
			put("Right Parenthesis", KeyCode.RIGHT_PARENTHESIS);
			put("Underscore", KeyCode.UNDERSCORE);
			put("Windows", KeyCode.WINDOWS);
			put("Context Menu", KeyCode.CONTEXT_MENU);
			put("Final", KeyCode.FINAL);
			put("Convert", KeyCode.CONVERT);
			put("Nonconvert", KeyCode.NONCONVERT);
			put("Accept", KeyCode.ACCEPT);
			put("Mode Change", KeyCode.MODECHANGE);
			put("Kana", KeyCode.KANA);
			put("Kanji", KeyCode.KANJI);
			put("Alphanumeric", KeyCode.ALPHANUMERIC);
			put("Katakana", KeyCode.KATAKANA);
			put("Hiragana", KeyCode.HIRAGANA);
			put("Full Width", KeyCode.FULL_WIDTH);
			put("Half Width", KeyCode.HALF_WIDTH);
			put("Roman Characters", KeyCode.ROMAN_CHARACTERS);
			put("All Candidates", KeyCode.ALL_CANDIDATES);
			put("Previous Candidate", KeyCode.PREVIOUS_CANDIDATE);
			put("Code Input", KeyCode.CODE_INPUT);
			put("Japanese Katakana", KeyCode.JAPANESE_KATAKANA);
			put("Japanese Hiragana", KeyCode.JAPANESE_HIRAGANA);
			put("Japanese Roman", KeyCode.JAPANESE_ROMAN);
			put("Kana Lock", KeyCode.KANA_LOCK);
			put("Input Method On/Off", KeyCode.INPUT_METHOD_ON_OFF);
			put("Cut", KeyCode.CUT);
			put("Copy", KeyCode.COPY);
			put("Paste", KeyCode.PASTE);
			put("Undo", KeyCode.UNDO);
			put("Again", KeyCode.AGAIN);
			put("Find", KeyCode.FIND);
			put("Properties", KeyCode.PROPS);
			put("Stop", KeyCode.STOP);
			put("Compose", KeyCode.COMPOSE);
			put("Alt Graph", KeyCode.ALT_GRAPH);
			put("Begin", KeyCode.BEGIN);
			put("Undefined", KeyCode.UNDEFINED);
			put("Softkey 0", KeyCode.SOFTKEY_0);
			put("Softkey 1", KeyCode.SOFTKEY_1);
			put("Softkey 2", KeyCode.SOFTKEY_2);
			put("Softkey 3", KeyCode.SOFTKEY_3);
			put("Softkey 4", KeyCode.SOFTKEY_4);
			put("Softkey 5", KeyCode.SOFTKEY_5);
			put("Softkey 6", KeyCode.SOFTKEY_6);
			put("Softkey 7", KeyCode.SOFTKEY_7);
			put("Softkey 8", KeyCode.SOFTKEY_8);
			put("Softkey 9", KeyCode.SOFTKEY_9);
			put("Game A", KeyCode.GAME_A);
			put("Game B", KeyCode.GAME_B);
			put("Game C", KeyCode.GAME_C);
			put("Game D", KeyCode.GAME_D);
			put("Star", KeyCode.STAR);
			put("Pound", KeyCode.POUND);
			put("Power", KeyCode.POWER);
			put("Info", KeyCode.INFO);
			put("Colored Key 0", KeyCode.COLORED_KEY_0);
			put("Colored Key 1", KeyCode.COLORED_KEY_1);
			put("Colored Key 2", KeyCode.COLORED_KEY_2);
			put("Colored Key 3", KeyCode.COLORED_KEY_3);
			put("Eject", KeyCode.EJECT_TOGGLE);
			put("Play", KeyCode.PLAY);
			put("Record", KeyCode.RECORD);
			put("Fast Forward", KeyCode.FAST_FWD);
			put("Rewind", KeyCode.REWIND);
			put("Previous Track", KeyCode.TRACK_PREV);
			put("Next Track", KeyCode.TRACK_NEXT);
			put("Channel Up", KeyCode.CHANNEL_UP);
			put("Channel Down", KeyCode.CHANNEL_DOWN);
			put("Volume Up", KeyCode.VOLUME_UP);
			put("Volume Down", KeyCode.VOLUME_DOWN);
			put("Mute", KeyCode.MUTE);
			put("Command", KeyCode.COMMAND);
		}};
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
		ClientExecutor clientExecutor = ClientExecutor.getClientExecutor();
		
		String creationDate = String.valueOf(ZonedDateTime.now());
		Coordinates coordinates = new Coordinates(x, y);
		Organization organization = new Organization(1, organizationName, organizationFullname, annualTurnover, employeesCount, organizationType);
		Product product = new Product(1, name, coordinates, creationDate, price, partNumber, manufactureCost, unit, organization);
		
		try {
			clientExecutor.perform(product, commandName, value);
		} catch (IOException | UniqueException | NullStringException | FalseTypeException | InfiniteException |
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
	
	@FXML
	public void startRobot() {
		double x = 520;
		var xtage = ((Stage) addButtonForm.getScene().getWindow());
		var IKS = xtage.getX();
		var IGREK = ((Stage) addButtonForm.getScene().getWindow()).getY();
		final double[] y = {266};
		Robot robot = new Robot();
		robot.mouseMove(x, y[0]);
		robot.mouseClick(MouseButton.PRIMARY);
		Thread thread = new Thread(() -> {
			for (int i = 0; i < 6; i++) {
				Platform.runLater(() -> {
					KeyCode[] keyCodes = new KeyCode[]{codesMap.get("1")};
					writeText(keyCodes, robot);
					robot.keyPress(codesMap.get("Tab"));
					y[0] += 64;
				});
			}
			Platform.runLater(() -> {
				robot.keyPress(codesMap.get("Down"));
				robot.keyPress(codesMap.get("Down"));
				robot.keyPress(codesMap.get("Tab"));
			});
			for (int i = 0; i < 4; i++) {
				Platform.runLater(() -> {
					KeyCode[] keyCodes = new KeyCode[]{codesMap.get("1")};
					writeText(keyCodes, robot);
					robot.keyPress(codesMap.get("Tab"));
					y[0] += 64;
				});
			}
			Platform.runLater(() -> {
				robot.keyPress(codesMap.get("Down"));
				robot.keyPress(codesMap.get("Down"));
				robot.keyPress(codesMap.get("Down"));
				robot.keyPress(codesMap.get("Tab"));
				robot.mouseMove(IKS + addButtonForm.getLayoutX() + 20, IGREK + xtage.getHeight() - 80);
				robot.mouseClick(MouseButton.PRIMARY);
			});
		});
		thread.start();
	}
	
	public void writeText(String text, Robot robot) {
		String[] textList = text.split("");
		for (String element : textList) {
			robot.keyPress(KeyCode.valueOf(element));
		}
	}
	
	public void writeText(KeyCode[] codes, Robot robot) {
		for (KeyCode element : codes) {
			robot.keyPress(element);
		}
	}
}


//		while (true) {
//			System.out.println(robot.getMouseX());
//			System.out.println(robot.getMouseY());
//			try {
//				sleep(1000);
//			} catch (InterruptedException exception) {
//				System.out.println(exception.getMessage());
//			}
//		}