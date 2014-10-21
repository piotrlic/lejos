package com.tomtom.lejos.stefan.command;


public class ForwardCommand implements Command {
	private BrickContext context;
	public ForwardCommand(BrickContext context){
		this.context = context;
	}
	@Override
	public void executeCommand() {

		context.getLeftMotor().rotate(180, true);
		context.getRightMotor().rotate(180);
	}

}
