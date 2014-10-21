package com.tomtom.lejos.stefan.command;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;


public class BackwardCommand implements Command {

	private BrickContext context;
	public BackwardCommand(BrickContext context){
		this.context = context;
	}
	@Override
	public void executeCommand() {
		
		context.getLeftMotor().rotate(-180, true);
		context.getRightMotor().rotate(-180);
	}

}
