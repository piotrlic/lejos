package com.tomtom.lejos;

import java.util.Random;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;

public class MotorRunner {

	private static final int _90degrees = 475;

	public static void main(String[] args) {
		RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		try {

			ir.getDistanceMode();
			int distance = 21;
			while (true) {
				while (distance > 20) {
					r1.forward();
					r2.forward();
					float[] sample = new float[ir.sampleSize()];
					ir.fetchSample(sample, 0);

					distance = (int) sample[0];
					GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
					g.drawString("Distance:" + distance, 5, 0, 0);
				}
				if (Button.ESCAPE.isDown()) {
					break;
				}
				r1.stop(true);
				r2.stop();

				int sng = new Random().nextBoolean() ? 1 : -1;
				int generatedInteger = sng * _90degrees;
				r1.rotate(generatedInteger, true);
				r2.rotate(-generatedInteger);
				distance = 21;
			}
		} finally {
			ir.close();
			r1.close();
			r2.close();
		}
	}

//
//	public static void main(String[] args) {
//		RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
//		RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.C);
//		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
//		try {
//			int angle = _90degrees;
//			r1.rotate(angle , true);
//			r2.rotate(-angle);
//		} finally {
//			ir.close();
//			r1.close();
//			r2.close();
//		}
//	}
}
