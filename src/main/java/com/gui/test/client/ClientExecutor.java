package com.gui.test.client;

import com.gui.test.common.*;
import com.gui.test.common.exception.*;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientExecutor {
	private final HashMap<String, ClientCommand> commandMap = new ClientCommandMap().getMap();
	private final ClientValidator validator = new ClientValidator();
	private final Authorizer authorizer = new Authorizer();
	private static BufferedReader br;
	private static String login;
	private static String password;
	
	protected static String accessToken;
	protected static String refreshToken;
	
	public ClientExecutor() {
	}
	
	/**
	 * this method use when program has been started
	 */
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		     DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			System.out.println("Авторизуйтесь или зарегистрируйтесь, чтобы получить доступ к API приложения.\nВведите:\nl - log in\nr - register");
			while (true) {
				try {
					String string = br.readLine().strip();
					switch (string) {
						case "l", "log in" -> authorizer.authorize();
						case "r", "register" -> authorizer.register();
						default -> {
							execute(clientSocket, address, string);
							System.out.println("\nВведите команду в формате: \"<команда> <ключ>\"");
						}
					}
				} catch (IOException | SQLException exception) {
					System.out.println(exception.getMessage());
					System.out.println("Авторизуйтесь или зарегистрируйтесь, чтобы получить доступ к API приложения.\nВведите:\nl - log in\nr - register");
				} catch (ExitException ignored) {
				}
			}
		} catch (IOException exception){
			System.out.println(exception.getMessage());
		}
	}
	
	public void execute(DatagramSocket clientSocket, InetAddress address, String string) throws ExitException {
		var reader = new Reader(string);
		String command = reader.getCommand();
		var value = reader.getValue();
		
		try {
			if (value != null & !command.equals("execute")) validator.isPositive(value, "Id");
			if (validator.check(string) && validator.checkCommand(command) && validator.checkParam(command, value)) {
				if (!command.equals("execute")) {
					SenderChunk.sendRequest(clientSocket, address, command, value);
					Response response = RecipientChunk.getResponse(clientSocket);
					if (response == null) throw new NullPointerException("Ответ от сервера не был получен!");
					System.out.println("From Server : " + response.getAnswer());
					String accessToken = response.getAccessToken();
					String refreshToken = response.getRefreshToken();
					if (accessToken != null) ClientExecutor.setAccessToken(accessToken);
					if (refreshToken != null) ClientExecutor.setRefreshToken(refreshToken);
				} else {
					commandMap.get(command).execute(value);
				}
			}
		} catch (EOFException exception) {
			System.out.println("Сервер недоступен, проверьте соединение!");
		} catch (IOException | ClassNotFoundException | FalseTypeException | FalseValuesException |
		         NumberFormatException | UniqueException | NullStringException | InfiniteException | NullPointerException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public void runScript(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			setBr(br);
			String line = br.readLine().strip();
			while (line != null) {
				try {
					executeScript(line);
					try {
						line = br.readLine().strip();
					} catch (NullPointerException exception) {
						line = null;
					}
				} catch (NumberFormatException exception) {
					System.out.println("Некорректные данные в файле " + fileName + " : " + exception.getMessage());
					break;
				} catch (ClassNotFoundException | FalseValuesException | NullPointerException |
				         IllegalArgumentException | FalseTypeException |
				         InfiniteException | UniqueException | NullStringException exception) {
					System.out.println(exception.getMessage());
					break;
				} catch (ExitException exception) {
					break;
				}
			}
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public void executeScript(String string) throws ExitException, IOException, ClassNotFoundException, NullPointerException, FalseValuesException, IllegalArgumentException, FalseTypeException, InfiniteException, UniqueException, NullStringException {
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			String newString = string.replace("insert", "insertScript");
			execute(clientSocket, address, newString);
		}
	}
	
	public static BufferedReader getBr() {
		return br;
	}
	
	public static void setBr(BufferedReader bufferedReader) {
		br = bufferedReader;
	}
	
	public static String getLogin() {
		return login;
	}
	
	public static void setLogin(String login) {
		ClientExecutor.login = login;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setPassword(String password) {
		ClientExecutor.password = password;
	}
	
	public static String getAccessToken() {
		return accessToken;
	}
	
	public static void setAccessToken(String accessToken) {
		ClientExecutor.accessToken = accessToken;
	}
	
	public static String getRefreshToken() {
		return refreshToken;
	}
	
	public static void setRefreshToken(String refreshToken) {
		ClientExecutor.refreshToken = refreshToken;
	}
}
