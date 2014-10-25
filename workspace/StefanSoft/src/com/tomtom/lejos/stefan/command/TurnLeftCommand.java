package com.tomtom.lejos.stefan.command;

public class TurnLeftCommand implements Command {

	private int angle;
	@Override
	public void executeCommand(BrickContext context) throws InterruptedException {
		context.getLeftMotor().backward();		
		context.getRightMotor().forward();
		
	}

	@Override
	public void setParams(String[] params) {
		// no params
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.TURN_LEFT;
	}
	

}
