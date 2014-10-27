package com.tomtom.lejos.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import sun.org.mozilla.javascript.internal.Kit;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import com.github.sarxos.webcam.Webcam;
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
	@FXML
	private ImageView camView;
	@FXML
	private TextField xTextField;
	@FXML
	private TextField yTextField;
	@FXML
	private Shape colorPresenter;
	
	private Glow enterEffect;
	private InnerShadow pressedEffect;
	private DropShadow colorPresenterEffect;

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
		this.colorPresenterEffect = new DropShadow();
		this.colorPresenterEffect.setBlurType(BlurType.GAUSSIAN);
		this.colorPresenterEffect.setWidth(120.0);
		this.colorPresenterEffect.setHeight(120.0);
		this.colorPresenterEffect.setColor(Color.WHITE);
		this.colorPresenterEffect.setSpread(0.6);
		this.colorPresenter.setEffect(colorPresenterEffect);
//		this.model.getColorPresenterProperty()

		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				Webcam webcam = Webcam.getDefault();
				webcam.setViewSize(new Dimension(640, 480));
				webcam.open();
				while (webcam.isOpen()) {
					BufferedImage image = webcam.getImage();
					final WritableImage imagefx = SwingFXUtils.toFXImage(image, null);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							camView.setImage(imagefx);
						}
					});
				};
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	public void upAction(MouseEvent mouseEvent) throws IOException {
		setPressedButtonEffect(mouseEvent);
		model.forward();
	}

	@FXML
	public void downAction(MouseEvent mouseEvent) throws IOException {
		setPressedButtonEffect(mouseEvent);
		model.backward();
	}

	@FXML
	public void leftAction(MouseEvent mouseEvent) throws IOException {
		setPressedButtonEffect(mouseEvent);
		model.left();
	}

	@FXML
	public void rightAction(MouseEvent mouseEvent) throws IOException {
		setPressedButtonEffect(mouseEvent);
		model.right();
	}
	
	@FXML
	public void gotoAction(MouseEvent mouseEvent) throws IOException {
		setPressedButtonEffect(mouseEvent);
		try {
			model.gotoAction(xTextField.getText(), yTextField.getText());
		} catch (NumberFormatException ex) {
			System.out.println("Unparsaplne input for go to function: " + xTextField.getText() + "  ,  " + yTextField.getText());
		}
	}

	@FXML
	public void setPressedButtonEffect(MouseEvent mouseEvent) {
		Node target = (Node) mouseEvent.getTarget();
		target.setEffect(pressedEffect);
	}

	@FXML
	public void stopAction(MouseEvent mouseEvent) throws IOException {
		releaseButtonEffect(mouseEvent);
		model.stop();
	}

	@FXML
	private void releaseButtonEffect(MouseEvent mouseEvent) {
		Node target = (Node) mouseEvent.getTarget();
		target.setEffect(pressedEffect.getInput());
	}

	@FXML
	public void setEnterButtonEffect(MouseEvent mouseEvent) {
		Node target = (Node) mouseEvent.getTarget();
		target.setEffect(enterEffect);
	}

	@FXML
	public void removeAllButtonsEffects(MouseEvent mouseEvent) {
		Node target = (Node) mouseEvent.getTarget();
		target.setEffect(null);
	}
}
