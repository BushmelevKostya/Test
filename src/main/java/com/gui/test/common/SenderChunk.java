package com.gui.test.common;

import com.gui.test.client.ClientCommand;
import com.gui.test.client.ClientCommandMap;
import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class SenderChunk {
	
	private static final HashMap<String, ClientCommand> commandMap = new ClientCommandMap().getMap();
	private static final Serializator serializator = new Serializator();
	private static final int SIZE = 2000;
	
	public SenderChunk() {
	}
	
	public static void sendRequest(DatagramSocket clientSocket, InetAddress address, String command, String value) throws FalseTypeException, IOException, ExitException, FalseValuesException, UniqueException, NullStringException, InfiniteException {
		Request request = new Request(command, value);
		var executeCommand = commandMap.get(command);
		if (executeCommand != null) {
			executeCommand.execute(value);
			Product product = executeCommand.getProduct();
			if (product != null) {
				request.setProduct(product);
			}
		}
		request.setAccessToken(ClientExecutor.getAccessToken());
		request.setRefreshToken(ClientExecutor.getRefreshToken());
		request.setRequestType("POST");
		sendPacket(request, clientSocket, address);
	}
	
	public static void sendResponse(Response response, DatagramChannel datagramChannel, InetSocketAddress clientAddress) throws IOException, ExitException{
		byte[] byteResponse = serializator.serializeObject(response);
		ByteBuffer buffer;
		for (int i = 0; i * SIZE < byteResponse.length; i++) {
			var newByteResponse = Arrays.copyOfRange(byteResponse, i * SIZE, (i + 1) * SIZE);
			buffer = ByteBuffer.wrap(newByteResponse);
			datagramChannel.send(buffer, clientAddress);
			try {
				sleep(1);
			}  catch (InterruptedException exception) {
				System.out.println(exception.getMessage());
			}
		}
		ByteBuffer metaBuffer = ByteBuffer.allocate(1000);
		metaBuffer.put("Конец".getBytes(StandardCharsets.UTF_8));
		metaBuffer.flip();
		datagramChannel.send(metaBuffer, clientAddress);
	}
	
	public static void sendRegisterRequest(DatagramSocket clientSocket, InetAddress address, String login, String name, String password) throws IOException {
		Request request = new Request(login, name, password);
		request.setRequestType("REGISTER");
		sendPacket(request, clientSocket, address);
	}
	
	private static void sendPacket(Request request, DatagramSocket clientSocket, InetAddress address) throws IOException {
		byte[] byteRequest = serializator.serializeObject(request);
		
		for (int i = 0; i * SIZE < byteRequest.length; i++) {
			var newByteRequest = Arrays.copyOfRange(byteRequest, i * SIZE, (i + 1) * SIZE);
			DatagramPacket sendPacket = new DatagramPacket(newByteRequest, SIZE, address, 9873);
			clientSocket.send(sendPacket);
		}
	}
	
	public static void sendAutorizeRequest(DatagramSocket clientSocket, InetAddress address, String login, String password) throws IOException {
		Request request = new Request(login, null, password);
		request.setRequestType("AUTHORIZE");
		sendPacket(request, clientSocket, address);
	}
	
	public static void sendProductsRequest(DatagramSocket clientSocket, InetAddress address, String login, String password) throws IOException {
		Request request = new Request(login, null, password);
		request.setAccessToken(ClientExecutor.getAccessToken());
		request.setRefreshToken(ClientExecutor.getRefreshToken());
		request.setRequestType("COLLECTION");
		sendPacket(request, clientSocket, address);
	}
}
