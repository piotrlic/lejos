package com.tomtom.ttday;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class WelcomeMain {
	public static void main(String[] args) {
		RegulatedMotor r1 = Motor.B;
		int i=0;
		while(true){
			r1.forward();
			if (Button.ESCAPE.isDown()) {
				break;
			}
			i++;
		}
		
	}
}
