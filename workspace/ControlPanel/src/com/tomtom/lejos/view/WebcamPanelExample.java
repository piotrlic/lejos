package com.tomtom.lejos.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;


public class WebcamPanelExample {

	public static void main(String[] args) throws InterruptedException {

		Dimension requiredDimension = new Dimension(1600,900);
		Dimension[] dime = {requiredDimension};
		Webcam webCam = Webcam.getWebcams().get(0);
		webCam.setCustomViewSizes(dime);
		webCam.setViewSize(requiredDimension);
		WebcamPanel panel = new WebcamPanel(webCam);
		
		panel.setFPSDisplayed(true);
		
		panel.setSize(requiredDimension);
		JFrame window = new JFrame("Test webcam panel");
		
		window.add(panel);
		window.setSize(requiredDimension);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}
}
