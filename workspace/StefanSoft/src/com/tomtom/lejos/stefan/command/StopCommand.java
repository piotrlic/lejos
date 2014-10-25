package com.tomtom.lejos.stefan.command;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StopCommand implements Command{

	@Override
	public void executeCommand(final BrickContext context)
			throws InterruptedException {
		final CyclicBarrier gate = new CyclicBarrier(3);

		Thread t1 = new Thread(){
		    public void run(){
		        try {
					gate.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        context.getLeftMotor().stop();	    
		    }};
		Thread t2 = new Thread(){
		    public void run(){
		        try {
					gate.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        context.getRightMotor().stop();    
		    }};

		t1.start();
		t2.start();

		try {
			gate.await();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("all threads started");
		
			
		
	}

	@Override
	public void setParams(String[] params) {
		// no params
		
	}

	@Override
	public CommandName getCommandName() {
		return CommandName.STOP;
	}

}
