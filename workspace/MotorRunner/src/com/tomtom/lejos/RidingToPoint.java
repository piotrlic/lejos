package com.tomtom.lejos;

import lejos.robotics.RegulatedMotor;

public class RidingToPoint {
	
	private RegulatedMotor r1;
	private RegulatedMotor r2;
	private static final int _90degrees = 475;
	private static final int _1meter = 1820;
	
	public RidingToPoint(RegulatedMotor r1, RegulatedMotor r2) {
		this.r1 = r1;
		this.r2 = r2;
	}

	public void rideToPoint(double x, double y) {
		//TODO:implement
	}

	private void turn(double degree) {
		int degreeRounded = (int)Math.round(calculateTurnForMotors(degree));
		r1.rotate(degreeRounded, true);
		r2.rotate(-degreeRounded);
	}
	
	private void goForward(double metersDistance) {
		int degreeRounded = (int)Math.round(calculateMetersForMotors(metersDistance));
		r1.rotate(degreeRounded);
		r2.rotate(degreeRounded);
	}

	private double calculateMetersForMotors(double metersDistance) {
		return metersDistance*_1meter;
	}

	private double calculateTurnForMotors(double degree) {
		return _90degrees*degree/90.0;
	}
	
}
