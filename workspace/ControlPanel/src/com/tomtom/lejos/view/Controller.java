package com.tomtom.lejos.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

	private Glow enterEffect;

	private InnerShadow pressedEffect;
	
	public void setModel(Model model) {
		this.model = model;
		this.enterEffect = new Glow(0.7);
		this.pressedEffect = new InnerShadow();
		this.pressedEffect.setBlurType(BlurType.GAUSSIAN);
		this.pressedEffect.setChoke(0.2);
		this.pressedEffect.setColor(Color.web("#5F9BB5"));
		this.pressedEffect.setWidth(40.0);
		this.pressedEffect.setHeight(40.0);
		this.pressedEffect.setInput(enterEffect);
	}
	
	@FXML
	public void upAction(MouseEvent mouseEvent) {
		releasePressedButtonEffect(mouseEvent);
		model.forward();
	}
	@FXML
	public void downAction(MouseEvent mouseEvent) {
		releasePressedButtonEffect(mouseEvent);
		model.backward();
	}
	@FXML
	public void leftAction(MouseEvent mouseEvent) {
		releasePressedButtonEffect(mouseEvent);
		model.left();
	}
	@FXML
	public void rightAction(MouseEvent mouseEvent) {
		releasePressedButtonEffect(mouseEvent);
		model.right();
	}
	
	@FXML
	public void setPressedButtonEffect(MouseEvent mouseEvent) {
		Node target = (Node)mouseEvent.getTarget();
		target.setEffect(pressedEffect);
	}
	
	@FXML
	public void releasePressedButtonEffect(MouseEvent mouseEvent) {
		Node target = (Node)mouseEvent.getTarget();
		target.setEffect(pressedEffect.getInput());
	}
	
	@FXML
	public void setEnterButtonEffect(MouseEvent mouseEvent) {
		Node target = (Node)mouseEvent.getTarget();
		target.setEffect(enterEffect);
	}

	@FXML
	public void removeAllButtonsEffects(MouseEvent mouseEvent) {
		Node target = (Node)mouseEvent.getTarget();
		target.setEffect(null);
	}
}
