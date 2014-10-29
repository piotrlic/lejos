package com.tomtom.lejos.stefan.command;


public class DriveForwardCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		context.getLeftMotor().forward();		
		context.getRightMotor().forward();
		return null;
	}

	@Override
	public void setParams(String[] params) {
		// no params
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.DRIVE_F;
	}

}
