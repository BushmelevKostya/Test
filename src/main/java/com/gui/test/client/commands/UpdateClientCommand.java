package com.gui.test.client.commands;

import com.gui.test.client.ClientCommand;
import com.gui.test.client.ClientValidator;
import com.gui.test.client.builder.Director;
import com.gui.test.client.builder.ProductBuilder;
import com.gui.test.common.RecipientChunk;
import com.gui.test.common.Request;
import com.gui.test.common.Response;
import com.gui.test.common.Serializator;
import com.gui.test.common.exception.FalseTypeException;
import com.gui.test.common.product.Product;
import com.gui.test.common.exception.ExitException;
import com.gui.test.common.exception.NoElementException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class UpdateClientCommand extends ClientCommand {
	
	private final ClientValidator validator = new ClientValidator();
	
	private Product product;
	
	private final Serializator serializator = new Serializator();
	
	public UpdateClientCommand() {
	}
	
	@Override
	public void execute(String value) throws ExitException, NumberFormatException, FalseTypeException {
		try  {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress address = InetAddress.getByName("localhost");
			
			System.out.println("Вы хотите обновить старый элемент или создать новый?\nold/new");
			while (true) {
				String str = br.readLine().strip();
				if (str.equals("old")) {
					System.out.println("Выполняется запрос элемента коллекции на сервер...");
					Request request = new Request("update", value);
					request.setRequestType("GET");
					byte[] byteRequest = serializator.serializeObject(request);
					DatagramPacket sendPacket = new DatagramPacket(byteRequest, byteRequest.length, address, 9873);
					clientSocket.send(sendPacket);
					
					Response response = RecipientChunk.getResponse(clientSocket);

					Product product = response.getProduct();
					if (product != null) {
						System.out.println("Элемент коллекции получен!");
						execute(product.getId(), product);
					}
					break;
				} else if (str.equals("new")) {
					setProduct(new Director(new ProductBuilder()).make());
					break;
				} else {
					System.out.println("Неверный ввод!");
				}
			}
		} catch (NumberFormatException exception) {
			throw new NumberFormatException("Этой команде необходимо передать параметр типа int!");
		} catch (IOException | ClassNotFoundException | NoElementException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	/**
	 * @param id      id of the element being changed
	 * @param product new value if it already exists
	 */
	public void execute(Integer id, Product product) throws IOException, ExitException, NoElementException {
		var newProduct = new Director(new ProductBuilder()).make(product);
		setProduct(newProduct);
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
}
