package com.tomtom.lejos;

public class Move {
	private double moveLeft;
	private double moveRight;

	public Move(double moveLeft, double moveRight) {
		super();
		this.moveLeft = moveLeft;
		this.moveRight = moveRight;
	}
	public double getMoveLeft() {
		return moveLeft;
	}
	public void setMoveLeft(double moveLeft) {
		this.moveLeft = moveLeft;
	}
	public double getMoveRight() {
		return moveRight;
	}
	public void setMoveRight(double moveRight) {
		this.moveRight = moveRight;
	}
}
