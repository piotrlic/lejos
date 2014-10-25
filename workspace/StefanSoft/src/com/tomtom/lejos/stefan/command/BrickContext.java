package com.tomtom.lejos.stefan.command;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class BrickContext {
	private RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
	private RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.C);
	private RegulatedMotor mediumMotor = new EV3MediumRegulatedMotor(
			MotorPort.B);

	public RegulatedMotor getRightMotor() {
		return r1;
	}

	public RegulatedMotor getLeftMotor() {
		return r2;
	}

	public RegulatedMotor getFlagMotor() {
		return mediumMotor;
	}
}
