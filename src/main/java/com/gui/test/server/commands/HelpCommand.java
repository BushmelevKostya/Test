package com.gui.test.server.commands;

import com.google.gson.Gson;
import com.gui.test.common.product.Product;
import com.gui.test.server.database.Connector;

import java.io.*;
import java.util.*;

/**
 * Print all valid commands from file
 */
public class HelpCommand extends ServerCommand {
	private final HashMap<String, String> data = new HashMap<>();
	
	public HelpCommand() {
//		try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/common/data/БД.json"))) {
		try (InputStream is = Connector.class.getClassLoader().getResourceAsStream("БД.json")) {
			Gson gson = new Gson();
			var json = gson.fromJson(new InputStreamReader(is), HashMap.class);
			
			json.forEach((key, value) -> data.put((String) key, (String) json.get(key)));
			
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	/**
	 * @param id isn't used
	 */
	public String execute(Integer id, Product product, String login) {
		data.forEach((key, value) -> addToResponse(key + " : " + value + "\n"));
		return response;
	}
}