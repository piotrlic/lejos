package com.tomtom.lejos;

public class Parameters {
	private double distance;
	private double angle;
	
	public Parameters(double distance, double angle) {
		super();
		this.distance = distance;
		this.angle = angle;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}

}
