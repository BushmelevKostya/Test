package com.gui.test.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class RecipientChunk {
	private static final Serializator serializator = new Serializator();
	public static final int SIZE = 2000;
	
	public RecipientChunk() {
	}
	
	public static Response getResponse(DatagramSocket clientSocket) throws IOException, ClassNotFoundException {
		byte[] response = new byte[0];
		clientSocket.setSoTimeout(5000);
		while (true) {
			byte[] receiveBytes = new byte[SIZE];
			DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
			clientSocket.receive(receivePacket);
			var data = receivePacket.getData();
			if (new String(data, StandardCharsets.UTF_8).contains("Конец")) break;
			ByteBuffer buffer = ByteBuffer.allocate(response.length + data.length);
			buffer.put(response);
			buffer.put(data);
			buffer.flip();
			response = buffer.array();
		}
		return serializator.deserializeToResponse(response);
	}
	
	public static Object[] getRequest(DatagramChannel datagramChannel) throws IOException, ClassNotFoundException {
		ByteBuffer buffer = ByteBuffer.allocate(0);
		InetSocketAddress clientAddress = null;
		datagramChannel.socket().setSoTimeout(3000);
		while (true) {
			ByteBuffer newBuffer = ByteBuffer.allocate(SIZE);
			InetSocketAddress newClientAddress = (InetSocketAddress) datagramChannel.receive(newBuffer);
			newBuffer.flip();
			if (newClientAddress == null) break;
			clientAddress = newClientAddress;
			ByteBuffer tempBuffer = ByteBuffer.allocate(buffer.capacity() + newBuffer.capacity());
			tempBuffer.put(buffer);
			tempBuffer.put(newBuffer);
			tempBuffer.flip();
			buffer = tempBuffer;
		}
		byte[] byteRequest = buffer.array();
		Request request = serializator.deserializeToRequest(byteRequest);
		Object[] objects = new Object[2];
		objects[0] = request;
		objects[1] = clientAddress;
		return objects;
	}
}
