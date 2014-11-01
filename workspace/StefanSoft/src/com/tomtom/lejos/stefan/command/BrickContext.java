package com.tomtom.lejos.stefan.command;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.ColorIdentifier;
import lejos.robotics.RegulatedMotor;

import com.tomtom.lejos.stefan.gotoxy.Coordinate;

public class BrickContext {
	
	private RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
	private RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.D);
	private EV3ColorSensor colorDetector = new EV3ColorSensor(SensorPort.S2);
	private EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S3);
	private BaseRegulatedMotor mediumMotor = new EV3MediumRegulatedMotor(
			MotorPort.C);
	
	private Coordinate previousPosition = new Coordinate(0, -1);
	private Coordinate currentPosition = new Coordinate(0, 0);

	public RegulatedMotor getRightMotor() {
		return r1;
	}

	public RegulatedMotor getLeftMotor() {
		return r2;
	}

	public ColorIdentifier getColorDetector() {
		return colorDetector;
	}
	
	public EV3ColorSensor getEV3ColorSensor() {
		return colorDetector;
	}
	
	public EV3IRSensor getEV3IRSensor() {
		return irSensor;
	}
	
	public BaseRegulatedMotor getFlagMotor() {
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
