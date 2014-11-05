package com.tomtom.lejos.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import com.tomtom.lejos.SocketClient;

public class Model {

	private SocketClient socketListener;
	private SocketClient socketSender;
	private ObjectProperty<Color> colorPresenter;
	private Map<String, Color> colorPresenterMap;
	private List<DistanceListener> distanceListenerList;
	private static double oneCmInPixels;
	private static String serverIp = "10.0.1.1";

	// public Model(String serverName, int port) throws IOException {
	public Model() throws IOException {
		colorPresenterMap = createColorMap();
		colorPresenter = new SimpleObjectProperty<Color>(Color.WHITE);
		distanceListenerList = new LinkedList<>();
	}

	private Map<String, Color> createColorMap() {
		Map<String, Color> colorMap = new HashMap<String, Color>();
		colorMap.put("0", Color.RED);
		colorMap.put("1", Color.GREEN);
		colorMap.put("2", Color.BLUE);
		colorMap.put("3", Color.YELLOW);
		colorMap.put("4", Color.MAGENTA);
		colorMap.put("5", Color.ORANGE);
		colorMap.put("6", Color.WHITE);
		colorMap.put("7", Color.BLACK);
		colorMap.put("8", Color.PINK);
		colorMap.put("9", Color.GRAY);
		colorMap.put("10", Color.LIGHTGRAY);
		colorMap.put("11", Color.DARKGRAY);
		colorMap.put("12", Color.CYAN);
		colorMap.put("13", Color.BROWN);
		colorMap.put("-1", Color.TRANSPARENT);
		return colorMap;
	}

	private void startColorDistanceReciving() {
		Thread colorReciverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(200);
						String colorDistance = socketListener.receive();
						String[] split = colorDistance.split(";");

						final Color currentColor = colorPresenterMap.get(split[0]);
						final String distance = split[1];
						
						if (!colorPresenter.getValue().equals(currentColor)) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									colorPresenter.set(currentColor);
								}
							});
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								for (DistanceListener distanceListener : distanceListenerList) {
									distanceListener.distanceNotify(distance);
								}
							}
						});
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		colorReciverThread.start();
	}

	public void forward() throws IOException {
		socketSender.sendMessage("DRIVE_F");
	}

	public void backward() throws IOException {
		socketSender.sendMessage("DRIVE_B");
	}

	public void left() throws IOException {
		socketSender.sendMessage("TURN_LEFT");
	}

	public void right() throws IOException {
		socketSender.sendMessage("TURN_RIGHT");
	}

	public void stop() throws IOException {
		socketSender.sendMessage("STOP");
	}

	public void gotoAction(String x, String y) throws NumberFormatException,
			IOException {
		socketSender.sendMessage("GOTO:" + x + "," + y);
	}

	public void gotoAction(double x, double y, Bounds boundsInLocal)
			throws NumberFormatException, IOException {
		double xCalibrated = (x - boundsInLocal.getWidth() / 2.0)
				/ oneCmInPixels;
		double yCalibrated = (-1) * (y - boundsInLocal.getHeight() / 2.0)
				/ oneCmInPixels;
		System.out.println("clicked");
		System.out.println("x = " + x + "  ,  y = " + y);
		System.out.println("calibrated");
		System.out.println("x = " + xCalibrated + "  ,  y = " + yCalibrated);
		gotoAction(Double.toString(xCalibrated), Double.toString(yCalibrated));
	}

	public ObservableValue<Color> getColorPresenter() {
		return colorPresenter;
	}

	public void testColorChange() {
		if (colorPresenter.getValue().equals(Color.RED)) {
			colorPresenter.setValue(Color.WHITE);
		} else {
			colorPresenter.setValue(Color.RED);
		}
	}

	public void calculateCalbration(Point2D calibrationFirstPoint,
			Point2D calibrationSecondPoint) {
		double xDiff = calibrationSecondPoint.getX()
				- calibrationFirstPoint.getX();
		double yDiff = calibrationSecondPoint.getY()
				- calibrationFirstPoint.getY();

		double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		oneCmInPixels = distance / 10.0;
		System.out.println(oneCmInPixels);
	}

	public void connectPlus() throws IOException {
		socketListener = new SocketClient(serverIp, 6666);
		socketListener.connect();
		socketSender = new SocketClient(serverIp, 6667);
		socketSender.connect();
		startColorDistanceReciving();
	}
	
	public void registerDistanceListener(DistanceListener distanceListener) {
		distanceListenerList.add(distanceListener);
	}
	
	public void removeDistanceListener(DistanceListener distanceListener) {
		distanceListenerList.remove(distanceListener);
	}

}
