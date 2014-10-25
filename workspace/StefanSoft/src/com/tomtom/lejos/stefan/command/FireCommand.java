package com.tomtom.lejos.stefan.command;

import lejos.hardware.Button;
import lejos.hardware.Sound;

public class FireCommand implements Command {

	@Override
	public void executeCommand(BrickContext context)
			throws InterruptedException {
		Button.LEDPattern(6);
		Sound.playTone(500, 100);
		
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public CommandName getCommandName() {
		return CommandName.FIRE;
	}

}
