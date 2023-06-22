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
import java.util.Arrays;
import java.util.HashMap;
@Deprecated
public class Sender {
	
	private static final HashMap<String, ClientCommand> commandMap = new ClientCommandMap().getMap();
	private static final Serializator serializator = new Serializator();
	private static final int SIZE = 10000;
	
	public Sender() {
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
		request.setLogin(ClientExecutor.getLogin());
		request.setPassword(ClientExecutor.getPassword());
		request.setRequestType("POST");
		sendPacket(request, clientSocket, address);
	}
	
	public static void sendResponse(Response response, DatagramChannel datagramChannel, InetSocketAddress clientAddress) throws IOException, ExitException {
//		ByteBuffer buffer;
//		byte[] byteResponse = serializator.serializeObject(response);
//		for (int i = 0; i * SIZE < byteResponse.length; i++) {
//			buffer = ByteBuffer.allocate(SIZE + 1);
//			byte[] newByteResponse = Arrays.copyOfRange(byteResponse, i * SIZE, Math.min((i + 1) * SIZE, byteResponse.length));
//			buffer.put(newByteResponse);
//			if (i * SIZE >= byteResponse.length) buffer.put(new byte[]{-1});
//			buffer.flip();
//			for (int j = 0; j < buffer.limit(); j++) {
//				System.out.printf(buffer.get() + " - " + j + "\n");
//			}
//			datagramChannel.send(buffer, clientAddress);
//		}
		ByteBuffer buffer = ByteBuffer.allocate(SIZE);
		byte[] byteResponse = serializator.serializeObject(response);
		buffer.put(byteResponse);
		buffer.flip();
		datagramChannel.send(buffer, clientAddress);
	}
	
	public static void sendRegisterRequest(DatagramSocket clientSocket, InetAddress address, String login, String name, String password) throws IOException {
		Request request = new Request(login, name, password);
		request.setRequestType("REGISTER");
		sendPacket(request, clientSocket, address);
	}
	
	private static void sendPacket(Request request, DatagramSocket clientSocket, InetAddress address) throws IOException {
		byte[] byteRequest = serializator.serializeObject(request);
//		for (int i = 0; i * SIZE < byteRequest.length; i++) {
//			byte[] newByteRequest = Arrays.copyOfRange(byteRequest, i * SIZE, Math.min((i + 1) * SIZE + 1, byteRequest.length));
//			if (i * SIZE >= byteRequest.length) newByteRequest[1000] = -1;
		DatagramPacket sendPacket = new DatagramPacket(byteRequest, byteRequest.length, address, 9873);
		clientSocket.send(sendPacket);
//		}
	}
	
	public static void sendAutorizeRequest(DatagramSocket clientSocket, InetAddress address, String login, String password) throws IOException {
		Request request = new Request(login, null, password);
		request.setRequestType("AUTHORIZE");
		sendPacket(request, clientSocket, address);
	}
}
