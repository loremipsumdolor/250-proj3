package edu.hendrix.csci250.csci250proj3.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EnhancedPlacement extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("EnhancedPlacementGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Enhanced Placement");
			primaryStage.getIcons().add(new Image("file:ep-icon.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}