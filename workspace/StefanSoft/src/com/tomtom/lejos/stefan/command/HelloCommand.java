package com.tomtom.lejos.stefan.command;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.Sound;

public class HelloCommand implements Command {

	@Override
	public void executeCommand(BrickContext context) throws InterruptedException {
			Button.LEDPattern(6);
			Thread.sleep(100);
			context.getFlagMotor().rotate(90);
			Thread.sleep(1000);
			for (int i = 0; i < 10; i++) {
				context.getFlagMotor().rotate(40);
				context.getFlagMotor().rotate(-40);
			}			
			Thread.sleep(1000);
			context.getFlagMotor().rotate(-90);
			//Sound.playTone(500, 100);//(new File("waves/test.wav"));
		
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public CommandName getCommandName() {
		return CommandName.HELLO;
	}

}
