package com.gui.test.client;

import com.gui.test.common.RecipientChunk;
import com.gui.test.common.Response;
import com.gui.test.common.SenderChunk;
import com.gui.test.server.database.Connector;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

public class Authorizer {
	private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private final Console console = System.console();
	
	public Authorizer() {
	}
	
	public void register() throws IOException, SQLException, NullPointerException {
		String login = null;
		String name = null;
		String password = null;
		String repeatPassword = null;
		char[] passwordArray = null;
		while (true) {
			login = createLogin(login);
			name = createName(name);
			password = createPassword(password, passwordArray, repeatPassword);
			
			String hash = getPasswordHash(password);
			
			try (DatagramSocket clientSocket = new DatagramSocket()) {
				InetAddress address = InetAddress.getByName("localhost");
				SenderChunk.sendRegisterRequest(clientSocket, address, login, name, hash);
				Response response = RecipientChunk.getResponse(clientSocket);
				System.out.println(response.getAnswer());
				if (response.isStatus()) {
					ClientExecutor.accessToken = response.getAccessToken();
					ClientExecutor.refreshToken = response.getRefreshToken();
					break;
				}
			} catch (ClassNotFoundException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	private String createLogin(String login) throws IOException {
		do {
			System.out.print("Введите новый логин. Логин должен содержать от 4 до 10 символов : ");
			login = br.readLine().strip();
			if (login.equals("")) System.out.println("Логин не может быть пустой строкой!");
			else if (login.length() < 4 || login.length() > 10)
				System.out.println("Логин должен содержать от 4 до 10 символов!");
		} while (login.length() < 4 || login.length() > 10);
		
		return login;
	}
	
	private String createName(String name) throws IOException {
		do {
			System.out.print("Введите Ваше имя. Имя должно содержать от 1 до 10 символов :  ");
			name = br.readLine().strip();
			if (name.equals("")) System.out.println("Имя не может быть пустой строкой!");
			else if (name.length() < 1 || name.length() > 10)
				System.out.println("Имя должно содержать от 1 до 10 символов!");
		} while (name.length() < 1 || name.length() > 10);
		return name;
	}
	
	private String createPassword(String password, char[] passwordArray, String repeatPassword) throws IOException {
		while (true) {
			do {
				System.out.print("Введите пароль. Пароль может включать любые символы, кроме пробела : ");
				if (console == null) {
					password = br.readLine().strip();
				} else {
					passwordArray = console.readPassword();
					password = new String(passwordArray);
				}
				if (password.equals("")) System.out.println("Пароль не может быть пустой строкой!");
				else if (password.contains(" ")) System.out.println("Пароль не должен включать в себя пробел!");
			} while (password.equals("") || password.contains(" "));
			do {
				System.out.print("Повторите ваш пароль : ");
				if (console == null) {
					repeatPassword = br.readLine().strip();
				} else {
					passwordArray = console.readPassword();
					repeatPassword = new String(passwordArray);
				}
				if (repeatPassword.equals("")) System.out.println("Пароль не может быть пустой строкой!");
				else if (repeatPassword.contains(" "))
					System.out.println("Пароль не должен включать в себя пробел!");
			} while (repeatPassword.equals("") || repeatPassword.contains(" "));
			if (!password.equals(repeatPassword)) System.out.println("Пароли не совпадают!");
			else break;
		}
		return password;
	}
	
	private String calculateSHA384(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
			byte[] hashBytes = messageDigest.digest(password.getBytes());
			
			StringBuilder hexBuilder = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = String.format("%02x", b);
				hexBuilder.append(hex);
			}
			
			return hexBuilder.toString();
		} catch (NoSuchAlgorithmException exception) {
			System.out.println(exception.getMessage());
		}
		return null;
	}
	
	public String getPasswordHash(String password) throws IOException {
		Properties properties = new Properties();
//		try (InputStream config = Files.newInputStream(Paths.get(PATH_TO_PROPERTIES))) {
		try (InputStream config = Connector.class.getClassLoader().getResourceAsStream("config.properties")) {
		properties.load(config);
		String PEPPER = properties.getProperty("PEPPER");
		String SALT = properties.getProperty("SALT");
		
		password = PEPPER + password + SALT;
		}
		String hash = calculateSHA384(password);
		if (hash == null) throw new NullPointerException("Системная ошибка создания пароля.");
		
		return hash;
	}
	
	
	public void authorize() throws IOException, SQLException {
		String login;
		String password;
		String name = null;
		do {
			while (true) {
				System.out.print("Введите Ваш логин : ");
				login = br.readLine().strip();
				if (login.equals("")) System.out.println("Логин не может быть пустой строкой!");
				else break;
			}
			while (true) {
				System.out.print("Введите пароль : ");
				if (console == null) {
					password = br.readLine().strip();
				} else {
					char[] passwordArray = console.readPassword();
					password = new String(passwordArray);
				}
				if (password.equals("")) System.out.println("Пароль не может быть пустой строкой!");
				else break;
			}
				try{
					name = sendAuthorizeRequest(password, login);
				} catch (ClassNotFoundException | UnknownHostException exception) {
					System.out.println(exception.getMessage());
				}
		} while (name == null);
		ClientExecutor.setLogin(login);
		ClientExecutor.setPassword(getPasswordHash(password));
		System.out.printf("Здравствуйте, %s! \n", name);
	}
	
	public String sendAuthorizeRequest(String password, String login) throws ClassNotFoundException, IOException {
		String hash = getPasswordHash(password);
		String name = "";
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress address = InetAddress.getByName("localhost");
			SenderChunk.sendAutorizeRequest(clientSocket, address, login, hash);
			Response response = RecipientChunk.getResponse(clientSocket);
			System.out.println(response.getAnswer());
			if (response.isStatus()) {
				name = response.getName();
			}
			ClientExecutor.accessToken = response.getAccessToken();
			ClientExecutor.refreshToken = response.getRefreshToken();
		}
		return name;
	}
	
}
