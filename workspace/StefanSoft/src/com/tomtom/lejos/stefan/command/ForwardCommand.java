package com.tomtom.lejos.stefan.command;


public class ForwardCommand implements Command {

	private int distance;
	@Override
	public String executeCommand(BrickContext context) {

		context.getLeftMotor().rotate(distance, true);
		context.getRightMotor().rotate(distance);
		return null;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	@Override
	public void setParams(String[] params) {
		distance = Integer.parseInt(params[0]);
	}
	@Override
	public CommandName getCommandName() {
		return CommandName.FORWARD;
	}
	
}
