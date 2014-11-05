package com.tomtom.lejos.stefan.command;

import lejos.hardware.Button;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;

public class FollowPathCommand implements Command {

	private float[] sample;

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {

		// context.getEV3IRSensor().getMode("").fetchSample(sample, offset);
		SensorMode redMode = context.getEV3ColorSensor().getRedMode();
		boolean turnRight = false;
		double previousValue = 0;
		boolean firstRun = true;
		while (true) {
			double cumulate = 0.0;
			if (Button.ESCAPE.isDown()) {
				break;
			}
			sample = new float[redMode.sampleSize()];

			redMode.fetchSample(sample, 0);

			double f = sample[0] - previousValue;
			
			if (!firstRun) {
				System.out.println("cumulate:"+cumulate);
				int times = 0;
				while (sample[0]>0.3) {
					if (Math.abs(cumulate)>0.08 && times>3){
						times=0;
						turnRight=!turnRight;
						cumulate=0;
					}
					if (turnRight) {
						context.getRightMotor().rotate(10);
					} else {
						context.getLeftMotor().rotate(10);
					}
					redMode.fetchSample(sample, 0);
					f = sample[0] - previousValue;
					cumulate = cumulate+f;
					System.out.println("cumulate il:"+cumulate);
					System.out.println("val il=" + sample[0]);
					previousValue = sample[0];
					times++;
				}
			}
			turnRight =true; 
			context.getLeftMotor().rotate(10, true);
			context.getRightMotor().rotate(10);
			previousValue = sample[0];
			firstRun = false;
			System.out.println("val=" + sample[0]);
		}
		return "";
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public CommandName getCommandName() {
		// TODO Auto-generated method stub
		return CommandName.FOLLOW_PATH;
	}

}
