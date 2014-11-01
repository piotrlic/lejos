package com.tomtom.lejos.stefan.command;

import com.tomtom.lejos.stefan.Main;

import lejos.hardware.Button;

public class HelloCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {

		//WHITE UNTIL BOX IS OPEN
		float distance = 0;
		while(distance < 20) {
			float[] sample = new float[context.getEV3IRSensor().sampleSize()];
			context.getEV3IRSensor().getDistanceMode().fetchSample(sample, 0);
			distance = sample[0];
			System.out.println(distance);
			Thread.sleep(1000);
		}
		//ROBOT ROTATION
		Thread.sleep(2000);
		double angle = 90;
		context.getRightMotor().rotate(new Double(angle*Main.DEGREE).intValue(), true);
		context.getLeftMotor().rotate(new Double(angle*Main.DEGREE*(-1)).intValue());
		//FLAG
		Button.LEDPattern(6);
		context.getFlagMotor().setSpeed(100);
		context.getFlagMotor().rotate(-110);
		for (int i = 0; i < 10; i++) {
			context.getFlagMotor().setSpeed(400);
			context.getFlagMotor().rotate(40);
			context.getFlagMotor().rotate(-40);
		}
		context.getFlagMotor().rotate(20);
		Thread.sleep(1000);
		context.getFlagMotor().rotate(90);
		Button.LEDPattern(0);
		return null;
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
