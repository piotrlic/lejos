package com.tomtom.lejos.stefan.command;


public class ForwardCommand implements Command {
	private BrickContext context;
	private int distance;
	public ForwardCommand(BrickContext context){
		this.context = context;
	}
	@Override
	public void executeCommand() {

		context.getLeftMotor().rotate(distance, true);
		context.getRightMotor().rotate(distance);
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
	
}
