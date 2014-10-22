package com.tomtom.lejos.stefan.command;

import java.util.HashMap;
import java.util.Map;

public class CommandsProvider {
	
	private Map<String, Command> commands = new HashMap<String, Command>();
	public void registerCommand(String commandName,Command command){
		commands.put(commandName, command);
	}
	public Command getCommand(String commandName){
		return commands.get(commandName);
	}
}
