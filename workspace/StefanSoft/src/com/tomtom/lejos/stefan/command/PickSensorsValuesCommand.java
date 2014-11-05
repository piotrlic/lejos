package com.tomtom.lejos.stefan.command;

public class PickSensorsValuesCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		float[] distanceGetch = new float[context.getEV3IRSensor().sampleSize()];
		context.getEV3IRSensor().getDistanceMode().fetchSample(distanceGetch , 0);
		return String.valueOf(context.getColorDetector().getColorID()) + ";" + String.valueOf(distanceGetch[0]);
	}

	@Override
	public void setParams(String[] params) {
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.PICK_SENSORS_VALUES;
	}

}
