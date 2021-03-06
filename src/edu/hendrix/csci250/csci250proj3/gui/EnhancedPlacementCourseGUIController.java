package edu.hendrix.csci250.csci250proj3.gui;

import edu.hendrix.csci250.csci250proj3.CollegiateCenterCode;
import edu.hendrix.csci250.csci250proj3.Course;
import edu.hendrix.csci250.csci250proj3.SQL;
import edu.hendrix.csci250.csci250proj3.Schedule;
import edu.hendrix.csci250.csci250proj3.Period;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnhancedPlacementCourseGUIController {
	
	@FXML Label courseTitle;
	@FXML Label courseCode;
	@FXML Label semester;
	@FXML Label period;
	@FXML Tooltip periodTooltip;
	@FXML Label fastSearchCode;
	@FXML Label courseLocation;
	@FXML Label courseDescription;
	@FXML HBox instructorHBox;
	@FXML HBox collegeCodesHBox;
	@FXML Button close;
	@FXML Button addCourse;
	@FXML HBox buttons;
	Course course;
	Schedule schedule;
	
	public void initialize() {
		schedule = Schedule.getSchedule();
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
	
	@FXML
	void addCourse() {
		if (addCourse.getText().equals("Remove Course")) {
			schedule.removeCourse(course.getFastSearch());
			outputMessage(AlertType.INFORMATION, "Course has been removed from your schedule.");
			addCourse.setText("Add Course");
		} else {
			try {
				schedule.addCourse(course);
				outputMessage(AlertType.INFORMATION, "Course has been added to your schedule.");
				addCourse.setText("Remove Course");
				addCourse.setPrefWidth(125);
			} catch (Exception e) {
				e.printStackTrace();
				outputMessage(AlertType.ERROR, e.getMessage());
			}
		}
	}

	public void initializeCourse(int fastSearch) {
		try {
			course = SQL.getCourse(fastSearch);
			Period periodData = SQL.getPeriod(course.getPeriod());
			courseTitle.setText(course.getTitle());
			Stage stage = (Stage)close.getScene().getWindow();
			stage.setTitle(courseTitle.getText());
			courseCode.setText(course.getCourseCode());
			semester.setText(course.getSemester());
			fastSearchCode.setText(Integer.toString(fastSearch));
			for (String p: course.getInstructorsArrayList()) {
				Label profLabel = new Label();
				profLabel.setText(p);
				profLabel.setUnderline(true);
				profLabel.setPadding(new Insets(0, 3, 0, 0));
				profLabel.setOnMouseClicked(event -> showProfessor(((Label)event.getSource()).getText()));
				profLabel.setOnMouseEntered(event -> profLabel.getScene().setCursor(Cursor.HAND));
				profLabel.setOnMouseExited(event -> profLabel.getScene().setCursor(Cursor.DEFAULT));
				instructorHBox.getChildren().add(profLabel);
			}
			for (String c: course.getCollegeCodes()) {
				if (!c.equals(" ") && !c.equals("")) {
					Label codeLabel = new Label();
					codeLabel.setText(c);
					codeLabel.setUnderline(true);
					codeLabel.setPadding(new Insets(0, 3, 0, 0));
					codeLabel.setOnMouseClicked(event -> showCode(((Label)event.getSource()).getText()));
					codeLabel.setOnMouseEntered(event -> codeLabel.getScene().setCursor(Cursor.HAND));
					codeLabel.setOnMouseExited(event -> codeLabel.getScene().setCursor(Cursor.DEFAULT));
					collegeCodesHBox.getChildren().add(codeLabel);
				}
			}
			courseLocation.setText(course.getBuilding() + " " + course.getRoom());
			period.setText(course.getPeriod());
			period.setOnMouseEntered(event -> period.getScene().setCursor(Cursor.HAND));
			period.setOnMouseExited(event -> period.getScene().setCursor(Cursor.DEFAULT));
			if (periodData != null) {
				periodTooltip.setText(periodData.getDescription());
			}
			courseDescription.setText(course.getDescription());
			if (schedule.containsCourse(course)) {
				addCourse.setText("Remove Course");
				addCourse.setPrefWidth(125);
			}
		} catch (Exception e) {
			e.printStackTrace();
			outputMessage(AlertType.ERROR, e.getMessage());
			close();
		}
	}
	
	private void outputMessage(AlertType alertType, String message) {
		Alert alert = new Alert(alertType, message);
		alert.initOwner((Stage)close.getScene().getWindow());
		alert.showAndWait();
	}
	
	private void showCode(String shortName) {
		try {
			CollegiateCenterCode ccCode = SQL.getCode(shortName);
			if (ccCode != null) {
				Alert ccCodeBox = new Alert(AlertType.INFORMATION);
				ccCodeBox.initOwner((Stage)close.getScene().getWindow());
				ccCodeBox.setTitle(ccCode.getLongName());
				ccCodeBox.setHeaderText(ccCode.getLongName() + " (" + ccCode.getShortName() + ")");
				ccCodeBox.setContentText(ccCode.getDescription());
				ImageView graphic = new ImageView(new Image("file:ep-icon.png"));
				graphic.setFitHeight(48);
				graphic.setFitWidth(48);
				ccCodeBox.setGraphic(graphic);
				ccCodeBox.showAndWait();
			} else {
				outputMessage(AlertType.ERROR, "Code not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			outputMessage(AlertType.ERROR, e.getMessage());
		}
	}
	
	private void showProfessor(String name) {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(close.getScene().getWindow());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EnhancedPlacementProfessorGUI.fxml"));
			dialog.setScene(new Scene((BorderPane)loader.load()));
			dialog.getIcons().add(new Image("file:ep-icon.png"));
			EnhancedPlacementProfessorGUIController controller = loader.<EnhancedPlacementProfessorGUIController>getController();
			controller.initializeProfessor(name); 
			dialog.show();
		} catch(Exception e) {
			e.printStackTrace();
			outputMessage(AlertType.ERROR, e.getMessage());
		}
	}
}
