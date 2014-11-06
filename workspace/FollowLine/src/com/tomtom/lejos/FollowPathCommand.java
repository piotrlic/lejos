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
				while (sample[0] > 0.3 && !cookieEaten) {
					if (Math.abs(cumulate) > 0.08 && times > 3) {
						times = 0;
						turnRight = !turnRight;
						cumulate = 0;
					}
					if (turnRight) {
						rightMotor.rotate(10);
					} else {
						leftMotor.rotate(10);
					}
					redMode.fetchSample(sample, 0);
					f = sample[0] - previousValue;
					cumulate = cumulate + f;
					System.out.println("cumulate il:" + cumulate);
					System.out.println("val il=" + sample[0]);
					previousValue = sample[0];
					times++;
				}
			}
			turnRight = true;
			leftMotor.rotate(10, true);
			rightMotor.rotate(10);
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
