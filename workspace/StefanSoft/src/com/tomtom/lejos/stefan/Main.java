package com.tomtom.lejos.stefan;

import java.io.IOException;

import com.tomtom.lejos.stefan.command.BackwardCommand;
import com.tomtom.lejos.stefan.command.BrickContext;
import com.tomtom.lejos.stefan.command.Command;
import com.tomtom.lejos.stefan.command.CommandsProvider;
import com.tomtom.lejos.stefan.command.ForwardCommand;
import com.tomtom.lejos.stefan.command.HelloCommand;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Main {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		BrickContext context = new BrickContext();
		CommandsProvider commandProvider = new CommandsProvider();
		commandProvider.registerCommand("forward", new ForwardCommand(context));
		commandProvider.registerCommand("backward", new BackwardCommand(context));
		commandProvider.registerCommand("hello", new HelloCommand(context));
		SocketServer server = new SocketServer(6666, 300000);
		server.connect();
		
		while (true) {
			Thread.sleep(1000);
			String message = server.receive();
			if (message != null) {
				Command command = commandProvider.getCommand(message);
				if (command!=null){
					command.executeCommand();
				}else{
					LCD.drawString("Unknown command", 0, 0);
				}
			}
			if (Button.ESCAPE.isDown()) {
				break;
			}
		}
	}
}
