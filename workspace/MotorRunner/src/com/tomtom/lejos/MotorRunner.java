package com.tomtom.lejos;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tomtom.lejos.stefan.SocketServer;

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
	private static final int _1meter = 1820;

	public static void main(String[] args) {
		RidingToPoint ridingToPoint = RobotBehaviorFactory.ridingToPoint();
		ridingToPoint.rideToPoint(1.0,1.0);
	}
	
	public static void mainOld(String[] args) {
		RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.C);
		SocketServer ss = connect();
		try {
			r1.resetTachoCount();
			r2.resetTachoCount();
			r1.rotate(_1meter, true);
			r2.rotate(_1meter);
			int degree = 2 * _90degrees;
			turn(r1, r2, degree);
			r1.resetTachoCount();
			r2.resetTachoCount();
			r1.rotate(_1meter, true);
			r2.rotate(_1meter);
			String tachoString = r1.getTachoCount() + "," + r2.getTachoCount();
			GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
			lcd.clear();
			lcd.drawString(tachoString, 5, 5, 0);
			ss.sendAndReceive("Tachos count: " + tachoString);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			r1.close();
			r2.close();
		}
	}

	public static void mainOldest(String[] args) throws IOException {
		RegulatedMotor r1 = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		SocketServer ss = connect();
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
					ss.sendAndReceive("dist: " + distance);
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

	private static SocketServer connect() {
		int port = 6666;
		int timeout = 30000;
		SocketServer ss = null;

		try {
			ss = new SocketServer(port, timeout);
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		try {
			ss.connect();
		} catch (java.net.SocketTimeoutException ex) {
			Logger.getLogger(ex.toString());
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return ss;
	}
}
