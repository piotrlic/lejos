package com.tomtom.lejos;

import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;

public class FollowPathCommand {

	private RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	private RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	private EV3ColorSensor colorDetector = new EV3ColorSensor(SensorPort.S2);
	private EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S3);
	private boolean cookieEaten = false;
	private float[] sample;

	public void executeCommand() {
		SensorMode redMode = colorDetector.getRedMode();
		runDistancePicking().start();
		boolean turnRight = false;
		double previousValue = 0;
		boolean firstRun = true;
		while (true) {
			double cumulate = 0.0;
			if (Button.ESCAPE.isDown() || cookieEaten) {
				break;
			}

			sample = new float[redMode.sampleSize()];

			redMode.fetchSample(sample, 0);

			double f = sample[0] - previousValue;

			if (!firstRun) {
				System.out.println("cumulate:" + cumulate);
				int times = 0;
				int correctionAngle = 20;
				int angle = correctionAngle;
				if (Math.abs(f) > 0.5 || sample[0] > 0.12) {
					while (!cookieEaten) {
						rightMotor.rotate(angle, true);
						leftMotor.rotate(-angle);
						float[] sampleAfterTurn = new float[redMode
								.sampleSize()];
						redMode.fetchSample(sampleAfterTurn, 0);
		
						if (sampleAfterTurn[0] > previousValue) {
							angle += correctionAngle;
							leftMotor.rotate(angle, true);
							rightMotor.rotate(-angle);
						}
						redMode.fetchSample(sampleAfterTurn, 0);
						if (sampleAfterTurn[0] <= previousValue) {
							break;
						}
						angle += correctionAngle;
					}

				}
			}
			turnRight = true;
			leftMotor.rotate(20, true);
			rightMotor.rotate(20);
			previousValue = sample[0];
			firstRun = false;
			System.out.println("val=" + sample[0]);
		}
	}

	public Thread runDistancePicking() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					float[] sample = new float[irSensor.sampleSize()];
					irSensor.getDistanceMode().fetchSample(sample, 0);
					float distance = sample[0];
					if (distance < 10) {
						cookieEaten = true;
						System.out.println("COOKIE EATEN!!!");
						break;
					}
				}

			}
		});
		return thread;
	}
}
