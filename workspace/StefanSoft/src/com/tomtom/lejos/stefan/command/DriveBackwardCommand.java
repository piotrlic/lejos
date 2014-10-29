package com.tomtom.lejos.stefan.command;


public class DriveBackwardCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		context.getLeftMotor().backward();		
		context.getRightMotor().backward();
		return null;
	}

	@Override
	public void setParams(String[] params) {
		// no params
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.DRIVE_B;
	}
	
}
