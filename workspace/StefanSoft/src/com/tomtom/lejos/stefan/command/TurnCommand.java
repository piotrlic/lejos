package com.tomtom.lejos.stefan.command;

public class TurnCommand implements Command {

	private int angle;
	@Override
	public void executeCommand(BrickContext context) throws InterruptedException {
		context.getLeftMotor().rotate(angle, true);
		context.getRightMotor().rotate((-1)*angle);
	}
	public int getDistance() {
		return angle;
	}
	public void setDistance(int distance) {
		this.angle = distance;
	}
	@Override
	public void setParams(String[] params) {
		angle = Integer.parseInt(params[0]);
	}
	@Override
	public CommandName getCommandName() {
		return CommandName.TURN;
	}
	

}
