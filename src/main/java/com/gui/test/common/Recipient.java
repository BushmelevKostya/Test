package com.gui.test.common;

import com.gui.test.common.exception.ExitException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
@Deprecated
public class Recipient {
	private static final Serializator serializator = new Serializator();
	public static final int SIZE = 10000;
	
	public Recipient() {
	}
	@Deprecated
	public static Response getResponse(DatagramSocket clientSocket) throws IOException, ClassNotFoundException {
//		ByteBuffer buffer = ByteBuffer.allocate(0);
////		clientSocket.setSoTimeout(2000);
//		int meta = 0;
//		while (meta != -1) {
			byte[] receiveBytes = new byte[SIZE];
			DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
			clientSocket.receive(receivePacket);
//			byte[] data = receivePacket.getData();
//			meta = data[1000];
//			System.out.println("meta" + " = " + meta);
//			data = Arrays.copyOfRange(data, 0, 1000);
//			ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() + SIZE - 1);
//			buffer.flip();
//			newBuffer.put(buffer);
//			newBuffer.put(data);
//			buffer.flip();
//		}
//		byte[] receivesBytes = buffer.array();
		return serializator.deserializeToResponse(receivePacket.getData());
	}
	
	public static Object[] getRequest(DatagramChannel datagramChannel) throws IOException, ClassNotFoundException {
//		ByteBuffer buffer = ByteBuffer.allocate(0);
//		InetSocketAddress clientAddress = null;
//		try {
//			while (true) {
				ByteBuffer nowBuffer = ByteBuffer.allocate(SIZE);
				InetSocketAddress newClientAddress = (InetSocketAddress) datagramChannel.receive(nowBuffer);
//				if (newClientAddress == null) throw new ExitException();
//				else clientAddress = newClientAddress;
//				var data = Arrays.copyOfRange(nowBuffer.array(), 0, 1000);
//				nowBuffer.flip();
//				ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() + 1000);
//				newBuffer.put(buffer);
//				newBuffer.put(data);
//				newBuffer.flip();
//				buffer = newBuffer;
//			}
//		} catch (ExitException ignored) {
//		}
//
		nowBuffer.flip();
		byte[] byteRequest = nowBuffer.array();
		Request request = serializator.deserializeToRequest(byteRequest);
		Object[] objects = new Object[2];
		objects[0] = request;
		objects[1] = newClientAddress;
		return objects;
	}
}
