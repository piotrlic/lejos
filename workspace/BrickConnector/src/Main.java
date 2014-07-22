import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;

public class Main {
	public static void main(String[] args) throws RemoteException,
			MalformedURLException, NotBoundException {
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
	     RegulatedMotor r1 = Motor.A;
	    RegulatedMotor r2 = Motor.D;
		try{
		

		ir.getDistanceMode();
		int distance = 0;
		int i = 0;
		
		while (distance>20) {
			float[] sample = new float[ir.sampleSize()];
			ir.fetchSample(sample, 0);

			distance = (int) sample[0];
			System.out.println(" Distance: " + distance);
			i++;
			r1.forward();
			r2.forward();
		}
		
		}finally{
			ir.close();	
			r1.close();
			r2.close();
		}
	}
}
