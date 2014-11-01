package com.tomtom.lejos.stefan.command;

import com.tomtom.lejos.stefan.Main;
import com.tomtom.lejos.stefan.gotoxy.Coordinate;
import com.tomtom.lejos.stefan.gotoxy.Parameters;
import com.tomtom.lejos.stefan.gotoxy.Transformer;

public class GotoCommand implements Command {
	private static final double CENTIMETER = 37;
	private Coordinate destination;
	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		Transformer transformer = new Transformer();
		Parameters transformResult = transformer.transform(context.getPreviousPosition(), context.getCurrentPosition(), destination);
		double angle = transformResult.getAngle();
		double distance = transformResult.getDistance();
		context.getRightMotor().rotate(new Double(angle*Main.DEGREE).intValue(), true);
		context.getLeftMotor().rotate(new Double(angle*Main.DEGREE*(-1)).intValue());
		
		context.getLeftMotor().rotate(new Double(distance*CENTIMETER).intValue(), true);
		context.getRightMotor().rotate(new Double(distance*CENTIMETER).intValue());
		
		context.setPreviousPosition(context.getCurrentPosition());
		context.setCurrentPosition(destination);
		return null;
	}

	@Override
	public void setParams(String[] params) {
		destination = new Coordinate(Double.parseDouble(params[0]), Double.parseDouble(params[1]));
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.GOTO;
	}

}
