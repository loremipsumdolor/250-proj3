package edu.hendrix.csci250.csci250proj3.gui;

import edu.hendrix.csci250.csci250proj3.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class EnhancedPlacementController {
	@FXML
	MenuItem searchMenuItem;
	@FXML
	MenuItem exitMenuItem;
	@FXML
	MenuItem editCourseMenuItem;
	@FXML
	MenuItem editProfessorMenuItem;
	@FXML
	MenuItem aboutMenuItem;
	@FXML
	TableView<Course> courseList;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	private void aboutDialog() {
		Alert aboutBox = new Alert(AlertType.INFORMATION);
		aboutBox.setTitle("About Enhanced Placement");
		aboutBox.setHeaderText("About Enhanced Placement");
		aboutBox.setContentText("Enhanced Placement v1.0\nCreated for Dr. Ferrer's CSCI 250 Spring 2017 class\n\nProject members:\n* Jacob Turner\n* Taylor Barker\n* Michael Spainhour\n* Uzair Tariq");
		aboutBox.showAndWait();
	}
	
	private void outputMessage(AlertType alertType, String message) {
		Alert alert = new Alert(alertType, message);
		alert.showAndWait();
	}

}
