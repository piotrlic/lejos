package com.tomtom.lejos.stefan.command;


public class DriveCommand implements Command {

	@Override
	public void executeCommand(BrickContext context)
			throws InterruptedException {
		context.getLeftMotor().forward();		
		context.getRightMotor().forward();
		
	}

	@Override
	public void setParams(String[] params) {
		// no params
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.DRIVE;
	}

}
