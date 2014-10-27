package com.tomtom.lejos.model;

import java.io.IOException;

import com.tomtom.lejos.SocketClient;

public class Model {

	private SocketClient socketClient;
	
	public Model(String serverName, int port) throws IOException {
		socketClient = new SocketClient(serverName, port);
		socketClient.connect();
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

}
