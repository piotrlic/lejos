package com.tomtom.lejos.stefan;

import java.io.IOException;
import java.net.SocketException;

import com.tomtom.lejos.stefan.command.BrickContext;
import com.tomtom.lejos.stefan.command.Command;
import com.tomtom.lejos.stefan.command.CommandsProvider;
import com.tomtom.lejos.stefan.command.ForwardCommand;
import com.tomtom.lejos.stefan.command.HelloCommand;
import com.tomtom.lejos.stefan.command.TurnCommand;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Main {
	private static final int TIMEOUT = 300000;
	public static void main(String[] args) throws IOException,
			InterruptedException {
		BrickContext context = new BrickContext();
		SocketPortPool pool = new SocketPortPool();
		CommandsProvider commandProvider = new CommandsProvider();
		commandProvider.registerCommand("forward", new ForwardCommand());
		commandProvider.registerCommand("turn", new TurnCommand());
		commandProvider.registerCommand("hello", new HelloCommand());
		SocketServer server = new SocketServer(pool.getPort(), TIMEOUT);
		server.connect();
		while (true) {
			try {
				Thread.sleep(1000);
				String message = server.receive();
				if (message != null) {
					String[] payload = message.split(":");
					Command command = commandProvider.getCommand(payload[0]);
					String[] params = payload[1].split(",");
					command.setParams(params);
					if (command == null) {
						LCD.drawString("Unknown command", 0, 0);
					} else {
						command.executeCommand(context);
					}
				}
				if (Button.ESCAPE.isDown()) {
					break;
				}
			} catch (SocketException e) {
				server.connectionLost();
				server = new SocketServer(pool.getPort(), 300000);
				server.connect();
				continue;
				
			}
		}
	}
}
