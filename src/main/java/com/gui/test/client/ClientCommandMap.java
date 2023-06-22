package com.gui.test.client;

import com.gui.test.client.commands.*;

import java.util.HashMap;

public class ClientCommandMap {
	private final HashMap<String, ClientCommand> commandMap;
	
	public ClientCommandMap() {
		commandMap = new HashMap<>();
		
		commandMap.put("insert", new InsertClientCommand());
		commandMap.put("replace_if_greater", new ReplaceCommand());
		commandMap.put("replace_if_lower", new ReplaceCommand());
		commandMap.put("update", new UpdateClientCommand());
		commandMap.put("execute", new ExecuteClientCommand());
		commandMap.put("exit", new ExitCommand());
		commandMap.put("insertScript", new InsertScriptClientCommand());
	}
	
	public HashMap<String, ClientCommand> getMap(){
		return this.commandMap;
	}
}
