package com.tomtom.lejos.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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
import javafx.scene.layout.StackPane;
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
	private Shape calibratingCenter;
	@FXML
	private StackPane microcosmos;
	@FXML
	private Text cameraLabel;
	@FXML
	private Text calibLabel;
	@FXML
	private StackPane calibrationButton;

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
		Webcam.setHandleTermSignal(true);
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

	private int calibrationClickCount = 0;
	private boolean switchCalibrationMode = false;
	private Point2D calibrationFirstPoint = null;
	private Point2D calibrationSecondPoint = null;

	@FXML
	public void gotoAction2(MouseEvent mouseEvent) throws NumberFormatException, IOException {
		if (switchCalibrationMode) {
			calibrationClickCount++;
			if (calibrationClickCount == 1) {
				double x = mouseEvent.getX();
				double y = mouseEvent.getY();
				calibrationFirstPoint = new Point2D(x, y);
			}
			if (calibrationClickCount > 1) {
				switchCalibrationMode = false;
				calibrationClickCount = 0;
				double x = mouseEvent.getX();
				double y = mouseEvent.getY();
				calibrationSecondPoint = new Point2D(x, y);
				model.calculateCalbration(calibrationFirstPoint,
						calibrationSecondPoint);
				calibrationButton.setEffect(null);
				microcosmos.setEffect(null);
				calibratingCenter.setVisible(false);
			}
		} else {
			Bounds boundsInLocal = microcosmos.getBoundsInLocal();
			Bounds boundsInParent = microcosmos.getBoundsInParent();
			// System.out.println("boundsInLocal = " + boundsInLocal);
			// System.out.println("boundsInParent = " + boundsInParent);
			model.gotoAction(mouseEvent.getX(), mouseEvent.getY(),
					boundsInLocal);
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

	@FXML
	public void switchCamera(MouseEvent mouseEvent) {
		setPressedButtonEffect(mouseEvent);
		String camera1text = "Camera 1";
		String camera2text = "Camera 2";
		if (camera1text.equals(cameraLabel.getText())) {
			cameraLabel.setText(camera2text);
			handleClose(webcam);
			cameraThread.interrupt();
			
			cameraThread = createCameraThread(1);
			cameraThread.start();
		} else {
			cameraLabel.setText(camera1text);
			handleClose(webcam);
			cameraThread.interrupt();
			cameraThread = createCameraThread(0);
			cameraThread.start();
		}

	}

	@FXML
	public void calibrationMode(MouseEvent mouseEvent) {
		setPressedButtonEffect(mouseEvent);
		switchCalibrationMode = true;
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.TOMATO);
		dropShadow.setWidth(120);
		dropShadow.setHeight(120);
		dropShadow.setSpread(0.6);
		microcosmos.setEffect(dropShadow);
		calibratingCenter.setVisible(true);
	}

	private Thread createCameraThread(final int cameraIndex) {
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				Webcam webcam2 = Webcam.getWebcams().get(cameraIndex);

				webcam2.setViewSize(new Dimension(640, 480));
				// webcam.setViewSize(new Dimension(1280, 720));
				webcam2.open();
				webcam = webcam2;
				try {
					while (webcam2.isOpen()) {
						BufferedImage image = webcam2.getImage();
						if (image == null) {
							continue;
						}
						final WritableImage imagefx = SwingFXUtils.toFXImage(
								image, null);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								camView.setImage(imagefx);
							}
						});
					};
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					handleClose(webcam2);
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
	
	public void handleClose(Webcam webcam2) {
		System.out.println("cam is closing after close request!!");
		if (webcam2 != null) {
			System.out.println("close camera in thread's finally");
			//webcam2.setViewSize(defaultDimantion);
			webcam2.close();
		}
	}
}
