package com.tomtom.lejos.stefan;

import java.io.IOException;
import java.net.SocketException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

import com.tomtom.lejos.stefan.command.BrickContext;
import com.tomtom.lejos.stefan.command.Command;
import com.tomtom.lejos.stefan.command.CommandName;
import com.tomtom.lejos.stefan.command.CommandsProvider;
import com.tomtom.lejos.stefan.command.DriveBackwardCommand;
import com.tomtom.lejos.stefan.command.DriveForwardCommand;
import com.tomtom.lejos.stefan.command.FireCommand;
import com.tomtom.lejos.stefan.command.ForwardCommand;
import com.tomtom.lejos.stefan.command.GotoCommand;
import com.tomtom.lejos.stefan.command.HelloCommand;
import com.tomtom.lejos.stefan.command.PickColorCommand;
import com.tomtom.lejos.stefan.command.StopCommand;
import com.tomtom.lejos.stefan.command.TurnLeftCommand;
import com.tomtom.lejos.stefan.command.TurnRightCommand;

public class Main {
	private static final int TIMEOUT = 300000;
	private static String lastColorId = "-1";
	private static SocketPortPool pool = new SocketPortPool();
	private static BrickContext context = new BrickContext();

	public static void main(String[] args) throws IOException,
			InterruptedException {
		
		runColorDetector().start();
		
		CommandsProvider commandProvider = new CommandsProvider();
		commandProvider.registerCommand(new ForwardCommand());
		commandProvider.registerCommand(new TurnLeftCommand());
		commandProvider.registerCommand(new TurnRightCommand());
		commandProvider.registerCommand(new DriveForwardCommand());
		commandProvider.registerCommand(new DriveBackwardCommand());
		commandProvider.registerCommand(new StopCommand());
		commandProvider.registerCommand(new HelloCommand());
		commandProvider.registerCommand(new FireCommand());
		commandProvider.registerCommand(new GotoCommand());
		SocketServer server = new SocketServer(6667, TIMEOUT);
		server.connect();
		while (true) {
			try {
				Thread.sleep(1000);
				String message = server.receive();
				if (message != null && message.length() > 0) {
					String[] payload = message.split(":");
					Command command = commandProvider.getCommand(CommandName
							.valueOf(payload[0]));
					if (payload.length > 1) {
						String[] params = payload[1].split(",");
						command.setParams(params);
					}

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
				server = new SocketServer(6667, 300000);
				server.connect();
				continue;
			}
		}
	}

	public static Thread runColorDetector() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Command command = new PickColorCommand();
					SocketServer server = new SocketServer(6666, TIMEOUT);
					server.connect();
					while (true) {
						Thread.sleep(300);
						String messageToSend = command.executeCommand(context);
						if (!lastColorId.equals(messageToSend)) {
							server.send(messageToSend);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return thread;
	}
}
