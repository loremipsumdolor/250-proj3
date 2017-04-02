package edu.hendrix.csci250.csci250proj3.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EnhancedPlacementCourseGUIController {
	
	@FXML Label courseTitle;
	@FXML Label courseCode;
	@FXML Label instructor;
	@FXML Label timeCode;
	@FXML Label fastSearch;
	@FXML Label location;
	@FXML Label collegeCodes;
	@FXML Label courseDescription;
	@FXML HBox instructorHBox;
	@FXML HBox collegeCodesHBox;
	@FXML Button close;
	
	void initialize() {
		
	}
	
	@FXML
	void close() {
		try {
			Stage stage = (Stage)close.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
