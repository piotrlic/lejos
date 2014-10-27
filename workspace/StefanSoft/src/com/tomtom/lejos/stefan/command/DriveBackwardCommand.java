package com.tomtom.lejos.stefan.command;


public class DriveBackwardCommand implements Command {

	@Override
	public void executeCommand(BrickContext context)
			throws InterruptedException {
		context.getLeftMotor().backward();		
		context.getRightMotor().backward();
		
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
