package com.tomtom.lejos.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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
import javafx.scene.text.Text;

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
	@FXML
	private Pane microcosmos;
	@FXML
	private Text cameraLabel;

	private Glow enterEffect;
	private InnerShadow pressedEffect;
	private DropShadow colorPresenterEffect;
	private Webcam webcam;

	private Thread cameraThread;

	public void setModel(Model model) {
		this.model = model;
		ObservableValue<Color> color = model.getColorPresenter();

		this.enterEffect = createEnterEffect();
		this.pressedEffect = createPressedEffect();
		this.colorPresenterEffect = createColorPresenterEffect(color);

		this.colorPresenter.setEffect(colorPresenterEffect);
		this.colorPresenter.fillProperty().bind(color);

		cameraThread = createCameraThread(0);
		cameraThread.start();
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
			System.out.println("Unparsaplne input for go to function: "
					+ xTextField.getText() + "  ,  " + yTextField.getText());
		}
	}
	
	@FXML
	public void gotoAction2(MouseEvent mouseEvent) {
		Bounds boundsInLocal = microcosmos.getBoundsInLocal();
		Bounds boundsInParent = microcosmos.getBoundsInParent();
		System.out.println("boundsInLocal = " + boundsInLocal);
		System.out.println("boundsInParent = " + boundsInParent);
		model.gotoAction(mouseEvent.getX(), mouseEvent.getY(), boundsInLocal);
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
	
	@FXML
	public void switchCamera(MouseEvent mouseEvent) {
		setPressedButtonEffect(mouseEvent);
		String camera1text = "Camera 1";
		String camera2text = "Camera 2";
		if (camera1text.equals(cameraLabel)) {
			cameraLabel.setText(camera2text);
			cameraThread.interrupt();
			cameraThread = createCameraThread(1);
			cameraThread.start();
		} else {
			cameraLabel.setText(camera1text);
			cameraThread.interrupt();
			cameraThread = createCameraThread(0);
			cameraThread.start();
		}
		
	}
	
	private Thread createCameraThread(final int cameraIndex) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				webcam = Webcam.getWebcams().get(cameraIndex);
				webcam.setViewSize(new Dimension(640, 480));
//				webcam.setViewSize(new Dimension(1280, 720));
				webcam.open();
				try {
				while (webcam.isOpen()) {
					BufferedImage image = webcam.getImage();
					final WritableImage imagefx = SwingFXUtils.toFXImage(image,
							null);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							camView.setImage(imagefx);
						}
					});
				};
				}
				finally {
					webcam.close();
				}
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		return thread;
	}

	private DropShadow createColorPresenterEffect(ObservableValue<Color> color) {
		DropShadow presentEffect = new DropShadow();
		presentEffect.setBlurType(BlurType.GAUSSIAN);
		presentEffect.setWidth(120.0);
		presentEffect.setHeight(120.0);
		presentEffect.setSpread(0.6);
		presentEffect.setColor(Color.WHITE);
		presentEffect.colorProperty().bind(color);
		return presentEffect;
	}

	private Glow createEnterEffect() {
		return new Glow(0.7);
	}

	private InnerShadow createPressedEffect() {
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setBlurType(BlurType.GAUSSIAN);
		innerShadow.setChoke(0.2);
		innerShadow.setColor(Color.web("#5F9BB5"));
		innerShadow.setWidth(40.0);
		innerShadow.setHeight(40.0);
		innerShadow.setInput(enterEffect);
		return innerShadow;
	}

}
