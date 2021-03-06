package com.tomtom.lejos.view;

import java.io.IOException;
import java.net.URL;

import com.tomtom.lejos.model.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunController extends Application{
	
	private static Model model;
	
	public static void main(String[] args) throws IOException {
		model = new Model();
		launch(args);
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		Parent sceneGraph = createSceneGraph(model);
		Scene scene = new Scene(sceneGraph);
		mainStage.setScene(scene);
		mainStage.setFullScreen(true);
		mainStage.show();
		
	}

	private Parent createSceneGraph(Model model) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		URL resource = getClass().getResource("view.fxml");
		fxmlLoader.setLocation(resource);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(resource.openStream());
		Controller controller = (Controller)(fxmlLoader.getController());
		controller.setModel(model);
		return root;
	}

}
