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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	HBox searchHBox;
	@FXML
	Label searchLabel;
	@FXML
	Button clearSearchButton;
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
	Course tempCourse = null;
	String searchString = "";

	@FXML
	private void initialize() {
		searchHBox.managedProperty().bind(searchHBox.visibleProperty());
		searchHBox.setVisible(false);
		schedule = Schedule.getSchedule();
		exitMenuItem.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});
		courseCodeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseCode"));
		courseTitleColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
		professorColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("instructors"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("period"));
		searchCodeColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("fastSearch"));
		courseList.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
	        double newColumnWidth = newSceneWidth.doubleValue() / 5;
	        courseCodeColumn.setPrefWidth(newColumnWidth);
	        courseTitleColumn.setPrefWidth(newColumnWidth);
	        professorColumn.setPrefWidth(newColumnWidth);
	        timeColumn.setPrefWidth(newColumnWidth);
	        searchCodeColumn.setPrefWidth(newColumnWidth);
		});
		try {
			ArrayList<Course> courses = SQL.getAllCourses();
			ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(courses);
			courseList.getItems().addAll(coursesToAdd);
		} catch (Exception e) {
			outputMessage(AlertType.ERROR, e.getMessage());
			Platform.exit();
			System.exit(0);
		}
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
	private void clearSearch() {
		searchString = "";
		tempCourse = null;
		searchHBox.setVisible(false);
		showAllCourses();
	}
	
	@FXML
	private void showAllCourses() {
		if (tempCourse == null && searchString.equals("")) {
			try {
				courseList.getItems().clear();
				ArrayList<Course> courses = SQL.getAllCourses();
				ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(courses);
				courseList.getItems().addAll(coursesToAdd);
			} catch (Exception e) {
				outputMessage(AlertType.ERROR, e.getMessage());
			}
		} else {
			ArrayList<Course> coursesFound = null;
			if (!searchString.equals("")) {
				try {
					coursesFound = SQL.getCoursesBasicSearch(searchString);
				} catch (Exception e) {
					outputMessage(AlertType.ERROR, e.getMessage());
				}
			} else if (!(tempCourse == null)) {
				try {
					coursesFound = SQL.getCoursesAdvancedSearch(tempCourse);
				} catch (Exception e) {
					outputMessage(AlertType.ERROR, e.getMessage());
				}
			}
			courseList.getItems().clear();
		    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(coursesFound);
		    courseList.getItems().addAll(coursesToAdd);
		}
	}
	
	@FXML
	private void loadSchedule() {
		ArrayList<String> choices = null;
		try {
			 choices = new ArrayList<>(SQL.getScheduleNames());
		} catch (Exception e) {
			outputMessage(AlertType.ERROR, e.getMessage());
		}
		ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);
		dialog.setTitle("Load Schedule");
		dialog.setHeaderText("Load Schedule");
		dialog.setContentText("Select a name:");
		ButtonType loadButtonType = new ButtonType("Load", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().clear();
		dialog.getDialogPane().getButtonTypes().addAll(loadButtonType, ButtonType.CANCEL);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			try {
				int[] loadedCourseSearch = SQL.getSchedule(result.get());
			    ArrayList<Course> loadedCourses = new ArrayList<Course>();
			    for (int i : loadedCourseSearch) {
				    loadedCourses.add(SQL.getCourse(i));
			    }
			    for (Course c : loadedCourses) {
			    	try {
						schedule.addCourse(c);
					} catch (Exception e) {
						outputMessage(AlertType.ERROR, e.getMessage());
					}
			    }
			    courseList.getItems().clear();
			    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(loadedCourses);
			    courseList.getItems().addAll(coursesToAdd);
			    myScheduleMenuItem.setSelected(true);
			} catch (Exception e) {
				outputMessage(AlertType.ERROR, e.getMessage());
			}
		}
	}
	
	@FXML
	private void saveSchedule() {
		int[] courseCodes = new int[schedule.getLength()];
		for (int i = 0; i < schedule.getLength(); i++) {
		    courseCodes[i] = schedule.getCourse(i).getFastSearch();
		}
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Save Schedule");
		dialog.setHeaderText("Save Schedule");
		dialog.setContentText("Enter a name:");
		ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().clear();
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
		Optional<String> nameResult = dialog.showAndWait();
		if (nameResult.isPresent()){
			try {
				if (!(SQL.getSchedule(nameResult.get()) == null)) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirm Overwrite");
					alert.setHeaderText("Overwrite Confirmation");
					alert.setContentText("Name already exists. Overwrite?");
					Optional<ButtonType> confirmResult = alert.showAndWait();
					if (confirmResult.get() == ButtonType.OK) {
						SQL.deleteSchedule(nameResult.get());
					}
				}
				SQL.saveSchedule(nameResult.get(), courseCodes);
			} catch (Exception e) {
				outputMessage(AlertType.ERROR, e.getMessage());
			}
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
			searchString = result.get();
			searchHBox.setVisible(true);
			searchLabel.setText("Basic Search: " + searchString);
			try {
				ArrayList<Course> coursesFound = SQL.getCoursesBasicSearch(searchString);
			    courseList.getItems().clear();
			    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(coursesFound);
			    courseList.getItems().addAll(coursesToAdd);
			} catch (Exception e) {
				outputMessage(AlertType.ERROR, e.getMessage());
			}
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
		try {
			ArrayList<AcademicSubject> subjectsListing = SQL.getAllAcademicSubjects();
			for (AcademicSubject s : subjectsListing) {
				subjects.getItems().add(s.getShortName());
			}
		} catch (Exception e) {
			outputMessage(AlertType.ERROR, e.getMessage());
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
		try {
			ArrayList<CollegiateCenterCode> collegeCodesListing = SQL.getAllCodes();
			for (CollegiateCenterCode c : collegeCodesListing) {
				collegeCodes.getItems().add(c.getShortName());
			}
		} catch (Exception e) {
			outputMessage(AlertType.ERROR, e.getMessage());
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
			tempCourse = result.get();
			searchHBox.setVisible(true);
			searchLabel.setText("Advanced Search");
			try {
				ArrayList<Course> coursesFound = SQL.getCoursesAdvancedSearch(result.get());
			    courseList.getItems().clear();
			    ObservableList<Course> coursesToAdd = FXCollections.observableArrayList(coursesFound);
			    courseList.getItems().addAll(coursesToAdd);
			} catch (Exception e) {
				outputMessage(AlertType.ERROR, e.getMessage());
				courseList.getItems().clear();
			}
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
