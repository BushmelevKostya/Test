package com.gui.test.server;

import com.gui.test.common.*;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;
import com.gui.test.server.database.DBParser;
import com.gui.test.server.database.Migrations;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
@Deprecated
public class ServerExecutor {
	public ServerExecutor() {
	}
	
	public void run() throws IOException, ClassNotFoundException, ExitException {
		DatagramChannel channel = DatagramChannel.open();
		channel.configureBlocking(false);
		channel.bind(new InetSocketAddress("localhost", 9873));
		
		Thread thread = new Thread(new ConsoleServerReader());
		thread.start();
		
		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_READ);
		
		System.out.println("Сервер успешно запущен! Введите exit, чтобы завершить работу сервера");
		while (true) {
			int readyChannels = selector.select();
			if (readyChannels == 0) continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			
			while (keyIterator.hasNext()) {
				SelectionKey key = keyIterator.next();
				if (key.isReadable()) {
					DatagramChannel datagramChannel = (DatagramChannel) key.channel();
					Object[] objects = RecipientChunk.getRequest(datagramChannel);
					Request request = (Request) objects[0];
					InetSocketAddress clientAddress = (InetSocketAddress) objects[1];
					Response response = createResponse(request);
					SenderChunk.sendResponse(response, datagramChannel, clientAddress);
					keyIterator.remove();
				}
			}
		}
	}
	public Response createResponse(Request request){
		Response response = new Response();
		Migrations migrations = DBParser.migrations;
		try {
			String requestType = request.getRequestType();
			if (requestType != null) {
				switch (requestType) {
					case "GET" -> {
						var id = request.getValue();
						try {
							Product product = new ServerValidator().getById(Integer.parseInt(id));
							response.setProduct(product);
						} catch (NumberFormatException exception) {
							throw new NumberFormatException("Этой команде необходимо передать параметр типа int!");
						}
					}
					case "REGISTER" -> {
						String name = request.getName();
						String login = request.getLogin();
						String password = request.getPassword();
						String result = migrations.checkUser(login);
						if (result == null) {
							migrations.insertUser(name, login, password);
							response.setAnswer("Вы успешно зарегистрировались!");
							response.setStatus(true);
						} else throw new UniqueException("Пользователь с таким логином уже существует!");
					}
					case "AUTHORIZE" -> {
						String login = request.getLogin();
						String password = request.getPassword();
						String name = migrations.checkUser(login);
						if (name != null) response.setName(name);
						else throw new NoElementException("Пользователя с таким логином не существует!");
						if (migrations.checkPassword(login, password)) {
							response.setStatus(true);
							response.setAnswer("Вы успешно авторизовались!");
							response.setName(name);
						} else throw new FalseValuesException("Введен неверный пароль!");
					}
					default -> {
						String login = request.getLogin();
						String password = request.getPassword();
						migrations.createPreventTrigger(migrations.getUserId(login));
						if (!migrations.checkPassword(login, password)) throw new NoAuthorizedException("Вы не зарегистрированы!");
						String answer = new CommandExecutor(request.getCommand().replace("insertScript", "insert"), request.getValue(), request.getProduct()).run(login);
						response.setAnswer(answer);
					}
				}
			}
		} catch (NumberFormatException | FalseValuesException | UniqueException | NoElementException | SQLException |
		         ExitException | NoAuthorizedException exception) {
			if (response.getAnswer() == null) response.setAnswer(exception.getMessage());
			else {
				String message = response.getAnswer() + "\n" + exception.getMessage();
				response.setAnswer(message);
			}
		}
		return response;
	}
}
