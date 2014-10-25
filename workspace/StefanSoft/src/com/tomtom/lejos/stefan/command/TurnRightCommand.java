package com.tomtom.lejos.stefan.command;

public class TurnRightCommand implements Command {

	@Override
	public void executeCommand(BrickContext context)
			throws InterruptedException {
		context.getLeftMotor().forward();		
		context.getRightMotor().backward();
		
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
