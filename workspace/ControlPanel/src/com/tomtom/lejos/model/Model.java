package com.tomtom.lejos.model;

import java.io.IOException;

import com.tomtom.lejos.SocketClient;

public class Model {

	private SocketClient socketClient;
	
	public Model(String serverName, int port) {
		socketClient = new SocketClient(serverName, port);
	}
	
	public void forward() throws IOException {
        socketClient.sendMessage("DRIVE_F");
	}
	
	public void backward() throws IOException {
		socketClient.sendMessage("DRIVE_B");
	}
	
	public void left() throws IOException {
        socketClient.sendMessage("TURN:-90");
	}
	
	public void right() throws IOException {
		socketClient.sendMessage("TURN:90");
	}
	
	public void stop() throws IOException {
        socketClient.sendMessage("STOP");

	}

}
