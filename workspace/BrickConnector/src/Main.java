import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;

public class Main {
	public static void main(String[] args) throws RemoteException,
			MalformedURLException, NotBoundException {
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);

		ir.getDistanceMode();
		float[] sample = new float[ir.sampleSize()];
		int control = ir.getRemoteCommand(0);
		int distance = 0;
		while (distance < 80) {
			ir.fetchSample(sample, 0);

			distance = (int) sample[0];
			System.out
					.println("Control: " + control + " Distance: " + distance);
		}
		ir.close();
	}
}
