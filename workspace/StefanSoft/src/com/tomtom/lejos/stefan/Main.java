package com.tomtom.lejos.stefan;

import java.io.IOException;

import com.tomtom.lejos.stefan.command.BackwardCommand;
import com.tomtom.lejos.stefan.command.BrickContext;
import com.tomtom.lejos.stefan.command.Command;
import com.tomtom.lejos.stefan.command.ForwardCommand;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Main {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		SocketServer server = new SocketServer(6666, 300000);
		server.connect();
		BrickContext context = new BrickContext();
		while (true) {
			Thread.sleep(1000);
			String message = server.receive();
			Command command = null;
			if (Button.ESCAPE.isDown()) {
				break;
			}
			if (message != null) {
				if (message.equals("forward")) {
					command = new ForwardCommand(context);
					command.executeCommand();
					LCD.drawString("Forward command executed", 0, 0);
				}else if (message.equals("backward")) {
					command = new BackwardCommand(context);
					command.executeCommand();
					LCD.drawString("Forward command executed", 0, 0);
				}else {
					LCD.drawString("Unknown command", 0, 0);
				}
			}
			if (Button.ESCAPE.isDown()) {
				break;
			}
		}
	}
}
