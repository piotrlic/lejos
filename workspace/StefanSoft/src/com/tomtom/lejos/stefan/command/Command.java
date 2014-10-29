package com.tomtom.lejos.stefan.command;

public interface Command {
	String executeCommand(BrickContext context) throws InterruptedException;

	void setParams(String[] params);
	
	CommandName getCommandName();
}
