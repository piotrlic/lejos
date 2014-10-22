package com.tomtom.lejos.stefan.command;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.Sound;

public class HelloCommand implements Command{

	private BrickContext context;

	public HelloCommand(BrickContext context) {
		this.context = context;
	}

	@Override
	public void executeCommand() throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			Button.LEDPattern(6);
			Thread.sleep(100);
			Button.LEDPattern(4);
			Thread.sleep(100);
			Button.LEDPattern(5);
			Thread.sleep(100);
			Sound.playTone(500, 100);//(new File("waves/test.wav"));
			
		}
		
	}

}
