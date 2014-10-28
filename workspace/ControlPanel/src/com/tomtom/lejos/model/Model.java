package com.tomtom.lejos.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

import com.tomtom.lejos.SocketClient;

public class Model {

	private SocketClient socketClient;
	private ObjectProperty<Color> colorPresenter;
	private Map<String, Color> colorPresenterMap;
	
	public Model(String serverName, int port) throws IOException {
		socketClient = new SocketClient(serverName, port);
		socketClient.connect();
		colorPresenterMap = createColorMap();
		colorPresenter = new SimpleObjectProperty<Color>(Color.WHITE);
		startColorReciving();
	}
	
	private Map<String, Color> createColorMap() {
		Map<String, Color> colorMap = new HashMap<String, Color>();
		colorMap.put("0",Color.RED);
		colorMap.put("1",Color.GREEN);
		colorMap.put("2",Color.BLUE);
		colorMap.put("3",Color.YELLOW);
		colorMap.put("4",Color.MAGENTA);
		colorMap.put("5",Color.ORANGE);
		colorMap.put("6",Color.WHITE);
		colorMap.put("7",Color.BLACK);
		colorMap.put("8",Color.PINK);
		colorMap.put("9",Color.GRAY);
		colorMap.put("10",Color.LIGHTGRAY);
		colorMap.put("11",Color.DARKGRAY);
		colorMap.put("12",Color.CYAN);
		colorMap.put("13",Color.BROWN);
		colorMap.put("-1",Color.TRANSPARENT);
		return colorMap;
	}

	private void startColorReciving() {
		Thread colorReciverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String colorKey = socketClient.receiveAndSend("");
					Color currentColor = colorPresenterMap.get(colorKey);
					if (!colorPresenter.getValue().equals(currentColor)){
//						Platform.runLater(arg0);
						colorPresenter.set(currentColor);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		colorReciverThread.start();
	}

	public void forward() throws IOException {
        socketClient.sendMessage("DRIVE_F");
	}
	
	public void backward() throws IOException {
		socketClient.sendMessage("DRIVE_B");
	}
	
	public void left() throws IOException {
        socketClient.sendMessage("TURN_LEFT");
	}
	
	public void right() throws IOException {
		socketClient.sendMessage("TURN_RIGHT");
	}
	
	public void stop() throws IOException {
        socketClient.sendMessage("STOP");
	}

	public void gotoAction(String x, String y) throws NumberFormatException, IOException{
		socketClient.sendMessage("GOTO:"+x+","+y);
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
	
	
	

}
