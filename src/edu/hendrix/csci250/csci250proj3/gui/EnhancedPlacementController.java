package edu.hendrix.csci250.csci250proj3.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import edu.hendrix.csci250.csci250proj3.AcademicSubject;
import edu.hendrix.csci250.csci250proj3.CollegiateCenterCode;
import edu.hendrix.csci250.csci250proj3.Course;
import edu.hendrix.csci250.csci250proj3.SQL;
import edu.hendrix.csci250.csci250proj3.Schedule;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;

import javafx.scene.control.Label;

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
	CheckMenuItem myScheduleMenuItem;
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
	
	Schedule schedule;

	@FXML
	private void initialize() {
		schedule = Schedule.getSchedule();
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
					dialog.setOnHiding(event -> {showSchedule();}); 
					dialog.show();
				} catch(Exception error) {
					error.printStackTrace();
					outputMessage(AlertType.ERROR, error.getMessage());
				}
		    }
		});
	}
	
	@FXML
	private void showAllCourses() {
		ArrayList<Course> courses = SQL.getAllCourses();
		ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(courses);
		courseList.getItems().addAll(coursesToAdd);
	}
	
	private void loadSchedule() {
		ArrayList<String> choices = new ArrayList<>(SQL.getSavedScheduleNames());
		ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);
		dialog.setTitle("Load Schedule");
		dialog.setHeaderText("Load Schedule");
		dialog.setContentText("Select a Name:");
		ButtonType loadButtonType = new ButtonType("Load", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().clear();
		dialog.getDialogPane().getButtonTypes().addAll(loadButtonType, ButtonType.CANCEL);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    ArrayList<Integer> loadedCourseSearch = SQL.getSavedSchedule(result.get());
		    ArrayList<Course> loadedCourses = new ArrayList<Course>();
		    for (Integer i : loadedCourseSearch) {
			    loadedCourses.add(SQL.getCourse(i));
		    }
		    for (Course c : loadedCourses) {
		    	schedule.addCourse(c);
		    }
		    courseList.getItems().clear();
		    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(loadedCourses);
		    courseList.getItems().addAll(coursesToAdd);
		    myScheduleMenuItem.setSelected(true);
		}
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
	private void showSchedule() {
		courseList.getItems().clear();
		if (myScheduleMenuItem.isSelected()) {
			ArrayList<Course> courses = schedule.getCourses();
			ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(courses);
			courseList.getItems().addAll(coursesToAdd);
		} else {
			showAllCourses();
		}
	}
	
	@FXML
	private void basicSearch() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search - Enhanced Placement");
		dialog.setHeaderText("Search Courses");
		dialog.setContentText("Please enter a search term:");
		ButtonType searchButtonType = new ButtonType("Search", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().clear();
		dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, ButtonType.CANCEL);
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
		Dialog<Course> dialog = new Dialog<>();
		dialog.setTitle("Advanced Search");
		dialog.setHeaderText("Advanced Course Search");
		ButtonType searchButtonType = new ButtonType("Search", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, ButtonType.CANCEL);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		ComboBox<String> subjects = new ComboBox<String>();
		ArrayList<AcademicSubject> subjectsListing = SQL.getAllAcademicSubjects();
		for (AcademicSubject s : subjectsListing) {
			subjects.getItems().add(s.getShortName());
		}
		TextField courseNumber = new TextField();
		TextField semester = new TextField();
		TextField fastSearchCode = new TextField();
		TextField title = new TextField();
		TextField instructors = new TextField();
		TextField period = new TextField();
		TextField building = new TextField();
		TextField room = new TextField();
		TextField description = new TextField();
		ComboBox<String> collegeCodes = new ComboBox<String>();
		ArrayList<CollegiateCenterCode> collegeCodesListing = SQL.getAllCodes();
		for (CollegiateCenterCode c : collegeCodesListing) {
			collegeCodes.getItems().add(c.getShortName());
		}
		grid.add(new Label("Subject:"), 0, 0);
		grid.add(subjects, 1, 0);
		grid.add(new Label("Course Number:"), 0, 1);
		grid.add(courseNumber, 1, 1);
		grid.add(new Label("Semester:"), 0, 2);
		grid.add(semester, 1, 2);
		grid.add(new Label("Fast Search Code:"), 0, 3);
		grid.add(fastSearchCode, 1, 3);
		grid.add(new Label("Title:"), 0, 4);
		grid.add(title, 1, 4);
		grid.add(new Label("Instructor:"), 0, 5);
		grid.add(instructors, 1, 5);
		grid.add(new Label("Time Code:"), 0, 6);
		grid.add(period, 1, 6);
		grid.add(new Label("Building:"), 0, 7);
		grid.add(building, 1, 7);
		grid.add(new Label("Room:"), 0, 8);
		grid.add(room, 1, 8);
		grid.add(new Label("Description:"), 0, 9);
		grid.add(description, 1, 9);
		grid.add(new Label("College Code:"), 0, 10);
		grid.add(collegeCodes, 1, 10);
		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == searchButtonType) {
		    	if (fastSearchCode.getText().equals("")) {
		    		fastSearchCode.setText("0");
		    	}
		        return new Course("", semester.getText(), subjects.getSelectionModel().getSelectedItem(),
		        		courseNumber.getText(), "", Integer.parseInt(fastSearchCode.getText()), title.getText(),
		        		new ArrayList<String>(Arrays.asList(instructors.getText())), period.getText(),
		        		building.getText(), room.getText(), description.getText(),
		        		new ArrayList<String>(Arrays.asList(collegeCodes.getSelectionModel().getSelectedItem())));
		    }
		    return null;
		});
		Optional<Course> result = dialog.showAndWait();
		if (result.isPresent()){
		    ArrayList<Course> coursesFound = SQL.getCoursesAdvancedSearch(result.get());
		    courseList.getItems().clear();
		    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(coursesFound);
		    courseList.getItems().addAll(coursesToAdd);
		}
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
