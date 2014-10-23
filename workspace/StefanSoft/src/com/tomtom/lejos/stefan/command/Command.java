package com.tomtom.lejos.stefan.command;

public interface Command {
	void executeCommand(BrickContext context) throws InterruptedException;

	void setParams(String[] params);
}
