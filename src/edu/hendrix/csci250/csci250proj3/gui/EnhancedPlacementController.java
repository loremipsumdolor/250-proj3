package edu.hendrix.csci250.csci250proj3.gui;

import java.util.ArrayList;
import java.util.Optional;

import edu.hendrix.csci250.csci250proj3.Course;
import edu.hendrix.csci250.csci250proj3.SQL;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class EnhancedPlacementController {
	@FXML
	MenuItem refreshMenuItem;
	@FXML
	MenuItem exitMenuItem;
	@FXML
	MenuItem basicSearchMenuItem;
	@FXML
	MenuItem advancedSearchMenuItem;
	@FXML
	MenuItem aboutMenuItem;
	@FXML
	TableView<Course> courseList;
	@FXML
	TableColumn<Course, String> courseCodeColumn;
	@FXML
	TableColumn<Course, String> courseTitleColumn;
	@FXML
	TableColumn<Course, String> professorColumn;
	@FXML
	TableColumn<Course, String> timeColumn;
	@FXML
	TableColumn<Course, Integer> searchCodeColumn;

	@FXML
	private void initialize() {
		exitMenuItem.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});
		ArrayList<Course> courses = SQL.getAllCourses();
		ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(courses);
		courseCodeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseCode"));
		courseTitleColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
		professorColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("instructors"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("period"));
		searchCodeColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("fastSearch"));
		courseList.getItems().addAll(coursesToAdd);
		courseList.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
	        double newColumnWidth = newSceneWidth.doubleValue() / 5;
	        courseCodeColumn.setPrefWidth(newColumnWidth);
	        courseTitleColumn.setPrefWidth(newColumnWidth);
	        professorColumn.setPrefWidth(newColumnWidth);
	        timeColumn.setPrefWidth(newColumnWidth);
	        searchCodeColumn.setPrefWidth(newColumnWidth);
		});


		courseList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	try {
					Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.initOwner(courseList.getScene().getWindow());
					FXMLLoader loader = new FXMLLoader(getClass().getResource("EnhancedPlacementCourseGUI.fxml"));
					dialog.setScene(new Scene((BorderPane)loader.load()));
					EnhancedPlacementCourseGUIController controller = loader.<EnhancedPlacementCourseGUIController>getController();
					controller.initializeCourse(newSelection.getFastSearch());
					dialog.show();
				} catch(Exception error) {
					error.printStackTrace();
					outputMessage(AlertType.ERROR, error.getMessage());
				}
		    }
		});
	}
	
	@FXML
	private void aboutDialog() {
		Alert aboutBox = new Alert(AlertType.INFORMATION);
		aboutBox.setTitle("About Enhanced Placement");
		aboutBox.setHeaderText("About Enhanced Placement");
		aboutBox.setContentText("Enhanced Placement v1.0\nCreated for Dr. Ferrer's CSCI 250 Spring 2017 class\n\nProject members:\n* Jacob Turner\n* Taylor Barker\n* Michael Spainhour\n* Uzair Tariq");
		aboutBox.showAndWait();
	}
	
	@FXML
	private void basicSearch() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search - Enhanced Placement");
		dialog.setHeaderText("Search Courses");
		dialog.setContentText("Please enter a search term:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    ArrayList<Course> coursesFound = SQL.getCoursesBasicSearch(result.get());
		    courseList.getItems().clear();
		    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(coursesFound);
		    courseList.getItems().addAll(coursesToAdd);
		}
	}
	
	@FXML
	private void advancedSearch() {
		outputMessage(AlertType.ERROR, "Advanced searching has not been implemented.");
	}
	
	@FXML
	private void refreshCourses() {
		outputMessage(AlertType.ERROR, "Refresh function has not been implemented.");
	}
	
	private void outputMessage(AlertType alertType, String message) {
		Alert alert = new Alert(alertType, message);
		alert.showAndWait();
	}

}
