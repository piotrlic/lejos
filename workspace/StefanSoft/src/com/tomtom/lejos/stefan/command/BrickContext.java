package com.tomtom.lejos.stefan.command;

import com.tomtom.lejos.stefan.gotoxy.Coordinate;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class BrickContext {
	private RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
	private RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.C);
	private RegulatedMotor mediumMotor = new EV3MediumRegulatedMotor(
			MotorPort.B);
	private Coordinate previousPosition = new Coordinate(-1, 0);
	private Coordinate currentPosition = new Coordinate(0, 0);

	public RegulatedMotor getRightMotor() {
		return r1;
	}

	public RegulatedMotor getLeftMotor() {
		return r2;
	}

	public RegulatedMotor getFlagMotor() {
		return mediumMotor;
	}
	
	public Coordinate getPreviousPosition(){
		return previousPosition ;
	}
	
	public Coordinate getCurrentPosition(){
		return currentPosition;
	}

	public void setCurrentPosition(Coordinate currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setPreviousPosition(Coordinate previousPosition) {
		this.previousPosition = previousPosition;
	}
}
