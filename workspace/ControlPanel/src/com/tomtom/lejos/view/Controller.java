package com.tomtom.lejos.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.tomtom.lejos.model.Model;

public class Controller {
	
	private Model model;
	
	@FXML
	private ImageView upButton;
	@FXML
	private ImageView downButton;
	@FXML
	private ImageView leftButton;
	@FXML
	private ImageView rightButton;
	
	@FXML
	private Pane buttonsPane;

	private Glow glow;
	
	public void setModel(Model model) {
		this.model = model;
		this.glow = new Glow(0.7);
	}
	
	@FXML
	public void upAction(MouseEvent mouseEvent) {
		model.forward();
	}
	
	@FXML
	public void setButtonEffect(MouseEvent mouseEvent) {
		removeAllButtonsEffects(mouseEvent);
		System.out.println("adding effects");
		Node target = (Node)mouseEvent.getTarget();
		target.setEffect(glow);
	}

	@FXML
	public void removeAllButtonsEffects(MouseEvent mouseEvent) {
		System.out.println("removing effects");
		upButton.setEffect(null);
//		downButton.setEffect(null);
//		leftButton.setEffect(null);
//		rightButton.setEffect(null);
	}
}
