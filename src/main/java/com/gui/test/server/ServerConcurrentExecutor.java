package com.gui.test.server;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gui.test.common.RecipientChunk;
import com.gui.test.common.Request;
import com.gui.test.common.Response;
import com.gui.test.common.SenderChunk;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;
import com.gui.test.server.database.DBParser;
import com.gui.test.server.database.Migrations;
import com.gui.test.server.jwt.JWTToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static java.lang.Thread.sleep;

public class ServerConcurrentExecutor {
	public ServerConcurrentExecutor() {
	}
	
	public void run() throws IOException, ClassNotFoundException, ExitException {
		Thread thread = new Thread(new ConsoleServerReader());
		thread.start();
		
		try (DatagramChannel channel = DatagramChannel.open()) {
			channel.configureBlocking(false);
			channel.bind(new InetSocketAddress("localhost", 9873));
			Selector selector = Selector.open();
			channel.register(selector, SelectionKey.OP_READ);
			
			System.out.println("Сервер успешно запущен! Введите exit, чтобы завершить работу сервера");
			
			ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
			
			while (true) {
				int readyChannels = selector.select();
				if (readyChannels == 0) continue;
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
				while (keyIterator.hasNext()) {
					execute(keyIterator, forkJoinPool);
				}
				keyIterator.remove();
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void execute(Iterator<SelectionKey> keyIterator, ForkJoinPool forkJoinPool) throws InterruptedException {
		
		SelectionKey key = keyIterator.next();
		if (key.isReadable()) {
			DatagramChannel datagramChannel = (DatagramChannel) key.channel();
			
			RequestHandler requestHandler = new RequestHandler(datagramChannel, forkJoinPool);
			forkJoinPool.invoke(requestHandler);
		}
	}
	
	public static void createResponse(Request request, DatagramChannel datagramChannel, InetSocketAddress clientAddress, ForkJoinPool forkJoinPool) {
		Response response = new Response();
		Migrations migrations = DBParser.migrations;
		
		try {
			String requestType = request.getRequestType();
			if (requestType != null) {
				switch (requestType) {
					case "GET" -> {
						get(request, response);
					}
					case "REGISTER" -> {
						register(request, response, migrations);
					}
					case "AUTHORIZE" -> {
						authorize(request, response, migrations);
					}
					case "COLLECTION" -> {
						sendProducts(request, response);
					}
					default -> {
						post(request, response);
					}
				}
			}
			
		} catch (NoAuthorizedException | NumberFormatException | FalseValuesException | UniqueException |
		         NoElementException | SQLException | UnsupportedEncodingException | ExitException exception) {
			if (response.getAnswer() == null) {
				response.setAnswer(exception.getMessage());
			} else {
				String message = response.getAnswer() + "\n" + exception.getMessage();
				response.setAnswer(message);
			}
		}
		ResponseHandler responseHandler = new ResponseHandler(response, datagramChannel, clientAddress);
		forkJoinPool.invoke(responseHandler);
	}
	
	private static void get(Request request, Response response) throws NoElementException, NumberFormatException {
		var id = request.getValue();
		try {
			Product product = new ServerValidator().getById(Integer.parseInt(id));
			response.setProduct(product);
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Этой команде необходимо передать параметр типа int!");
		}
		
	}
	
	private static void authorize(Request request, Response response, Migrations migrations) throws NoElementException, FalseValuesException, SQLException, UnsupportedEncodingException {
		String login = request.getLogin();
		String password = request.getPassword();
		String name = migrations.checkUser(login);
		
		if (name != null) {
			
			response.setName(name);
			
		}
		else throw new NoElementException("Пользователя с таким логином не существует!");
		
		if (migrations.checkPassword(login, password)) {
			
			response.setStatus(true);
			response.setAnswer("Вы успешно авторизовались!");
			response.setName(name);
			response.setAccessToken(JWTToken.generateAccessToken(login, name, password));
			response.setRefreshToken(JWTToken.generateRefreshToken(login, name, password));
		} else throw new FalseValuesException("Введен неверный пароль!");
	}
	
	private static void register(Request request, Response response, Migrations migrations) throws UniqueException, SQLException, UnsupportedEncodingException {
		String name = request.getName();
		String login = request.getLogin();
		String password = request.getPassword();
		String result = migrations.checkUser(login);
		if (result == null) {
			migrations.insertUser(name, login, password);
			response.setAnswer("Вы успешно зарегистрировались!");
			response.setStatus(true);
			response.setAccessToken(JWTToken.generateAccessToken(login, name, password));
			response.setRefreshToken(JWTToken.generateRefreshToken(login, name, password));
		} else throw new UniqueException("Пользователь с таким логином уже существует!");
	}
	
	private static void sendProducts(Request request, Response response) throws NoAuthorizedException, NoElementException, FalseValuesException, SQLException, UnsupportedEncodingException, UniqueException, ExitException {
		String token = request.getAccessToken();
		String login;
		try {
			login = JWTToken.verifyAccessToken(token);
		} catch (JWTVerificationException exception) {
			token = request.getRefreshToken();
			try {
				var result = JWTToken.verifyRefreshToken(token);
				String newLogin = result.get("login");
				String name = result.get("username");
				String password = result.get("password");
				
				response.setAccessToken(JWTToken.generateAccessToken(newLogin, name, password));
				response.setRefreshToken(JWTToken.generateRefreshToken(newLogin, name, password));
				
				throw new NoAuthorizedException("Срок вашего токена доступа истек, получен новый. Попробуйте еще раз");
			} catch (JWTVerificationException e) {
				throw new NoAuthorizedException("Вы не зарегистрированы!");
			}
		}
		TreeMap<Integer, Product> collection = MainCollection.getCollection();
		response.setProducts(collection);
//		String answer = new CommandExecutor(request.getCommand().replace("insertScript", "insert"), request.getValue(), request.getProduct()).run(login);
//		response.setAnswer(answer);
	}
	
	private static void post(Request request, Response response) throws NoAuthorizedException, NoElementException, FalseValuesException, SQLException, UnsupportedEncodingException, UniqueException, ExitException {
		String token = request.getAccessToken();
		String login;
		try {
			login = JWTToken.verifyAccessToken(token);
		} catch (JWTVerificationException exception) {
			token = request.getRefreshToken();
			try {
				var result = JWTToken.verifyRefreshToken(token);
				String newLogin = result.get("login");
				String name = result.get("username");
				String password = result.get("password");
				
				response.setAccessToken(JWTToken.generateAccessToken(newLogin, name, password));
				response.setRefreshToken(JWTToken.generateRefreshToken(newLogin, name, password));
				
				throw new NoAuthorizedException("Срок вашего токена доступа истек, получен новый. Попробуйте еще раз");
			} catch (JWTVerificationException e) {
				throw new NoAuthorizedException("Вы не зарегистрированы!");
			}
		}
		String answer = new CommandExecutor(request.getCommand().replace("insertScript", "insert"), request.getValue(), request.getProduct()).run(login);
		response.setAnswer(answer);
	}
	
	private static class RequestHandler extends RecursiveAction {
		private final DatagramChannel datagramChannel;
		private Request request;
		private InetSocketAddress clientAddress;
		ForkJoinPool forkJoinPool;
		
		public RequestHandler(DatagramChannel datagramChannel, ForkJoinPool forkJoinPool) {
			this.datagramChannel = datagramChannel;
			this.forkJoinPool = forkJoinPool;
		}
		
		@Override
		protected void compute() {
			try {
				Object[] objects = RecipientChunk.getRequest(datagramChannel);
				request = (Request) objects[0];
				clientAddress = (InetSocketAddress) objects[1];

				Thread threadExecutor = new Thread(() -> createResponse(request, datagramChannel, clientAddress, forkJoinPool));
				threadExecutor.start();
			} catch (IOException | ClassNotFoundException exception) {
				System.out.println(exception.getMessage());
			}
		}
		
		public Request getRequest() {
			return request;
		}
		
		public void setRequest(Request request) {
			this.request = request;
		}
		
		public InetSocketAddress getClientAddress() {
			return clientAddress;
		}
		
		public void setClientAddress(InetSocketAddress clientAddress) {
			this.clientAddress = clientAddress;
		}
	}
	
	private static class ResponseHandler extends RecursiveAction {
		private final Response response;
		private final DatagramChannel datagramChannel;
		private final InetSocketAddress clientAddress;
		
		public ResponseHandler(Response response, DatagramChannel datagramChannel, InetSocketAddress clientAddress) {
			this.response = response;
			this.datagramChannel = datagramChannel;
			this.clientAddress = clientAddress;
		}
		
		@Override
		protected void compute() {
			try {
				SenderChunk.sendResponse(response, datagramChannel, clientAddress);
				
			} catch (IOException | ExitException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
}