package com.tomtom.lejos.stefan.command;

import java.util.HashMap;
import java.util.Map;

public class CommandsProvider {
	
	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	public void registerCommand(Command command){
		commands.put(command.getCommandName(), command);
	}
	public Command getCommand(CommandName commandName){
		return commands.get(commandName);
	}
}
