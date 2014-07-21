package com.tomtom.lejos;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class MotorRunner {

	public static void main(String[] args) {
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
		m.rotate(360);
		m.close();
	}

}
