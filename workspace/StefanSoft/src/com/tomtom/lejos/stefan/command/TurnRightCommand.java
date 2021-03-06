package com.tomtom.lejos.stefan.command;

public class TurnRightCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		context.getLeftMotor().forward();		
		context.getRightMotor().backward();
		return null;
	}

	@Override
	public void setParams(String[] params) {
		// no params
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.TURN_RIGHT;
	}

}
