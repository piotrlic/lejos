package com.tomtom.lejos.stefan.command;

import lejos.hardware.Button;
import lejos.hardware.Sound;

public class FireCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		Button.LEDPattern(6);
		Sound.playTone(500, 100);
		return null;
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
