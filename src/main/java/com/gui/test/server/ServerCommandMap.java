package com.gui.test.server;

import com.gui.test.server.commands.*;
import com.gui.test.server.database.Migrations;

import java.util.HashMap;

public class ServerCommandMap {
	private final HashMap<String, ServerCommand> commandMap;
	
	public ServerCommandMap() {
		commandMap = new HashMap<>();

		commandMap.put("clear", new ClearCommand());
		commandMap.put("filter_by_part_number", new FilterByPartNumber());
		commandMap.put("insert", new InsertServerCommand());
		commandMap.put("replace_if_greater", new ReplaceIfGreaterServerCommand());
		commandMap.put("replace_if_lower", new ReplaceIfLowerServerCommand());
		commandMap.put("update", new UpdateServerCommand());
		commandMap.put("help", new HelpCommand());
		commandMap.put("info", new InfoCommand());
		commandMap.put("print_field_ascending_manufacturer", new PrintFieldAscendingManufacturer());
		commandMap.put("print_unique_part_number", new PrintUniquePartNumber());
		commandMap.put("remove", new RemoveCommand());
		commandMap.put("remove_lower_key", new RemoveLowerKey());
		commandMap.put("show", new ShowCommand());

	}
	
	public HashMap<String, ServerCommand> getMap(){
		return this.commandMap;
	}
}
