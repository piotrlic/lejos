package com.tomtom.lejos.stefan.command;

import lejos.hardware.Button;

public class FireCommand implements Command {

	@Override
	public void executeCommand(BrickContext context)
			throws InterruptedException {
		Button.LEDPattern(6);
		
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public CommandName getCommandName() {
		// TODO Auto-generated method stub
		return null;
	}

}
