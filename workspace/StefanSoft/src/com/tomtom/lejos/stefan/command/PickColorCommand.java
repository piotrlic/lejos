package com.tomtom.lejos.stefan.command;

public class PickColorCommand implements Command {

	@Override
	public String executeCommand(BrickContext context)
			throws InterruptedException {
		return String.valueOf(context.getColorDetector().getColorID());
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public CommandName getCommandName() {
		return CommandName.PICK_COLOR;
	}

}
