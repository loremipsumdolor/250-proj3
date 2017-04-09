package edu.hendrix.csci250.csci250proj3.gui;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;

import edu.hendrix.csci250.csci250proj3.Course;
import edu.hendrix.csci250.csci250proj3.Professor;
import edu.hendrix.csci250.csci250proj3.SQL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnhancedPlacementProfessorGUIController {
	@FXML Label professorName;
	@FXML Label title;
	@FXML Label office;
	@FXML Label email;
	@FXML Label phoneNumber;
	@FXML ListView<String> coursesOffered;
	@FXML ImageView professorImage;
	@FXML Button closeButton;
	
	Professor professor;
	
	public void initialize() {
		coursesOffered.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	try {
					Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.initOwner(coursesOffered.getScene().getWindow());
					FXMLLoader loader = new FXMLLoader(getClass().getResource("EnhancedPlacementCourseGUI.fxml"));
					dialog.setScene(new Scene((BorderPane)loader.load()));
					dialog.getIcons().add(new Image("file:ep-icon.png"));
					EnhancedPlacementCourseGUIController controller = loader.<EnhancedPlacementCourseGUIController>getController();
					controller.initializeCourse(professor.getCourseInList(newSelection)); 
					dialog.show();
				} catch(Exception e) {
					e.printStackTrace();
					outputMessage(AlertType.ERROR, e.getMessage());
				}
		    }
		});
	}
	
	@FXML
	void close() {
		try {
			Stage stage = (Stage)closeButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initializeProfessor(String name) {		
		try {
			Stage stage = (Stage)closeButton.getScene().getWindow();
			stage.setTitle(name);
			professor = SQL.getProfessor(name);
			ArrayList<Course> professorCourses = SQL.getProfessorCourses(name);
			for (Course c : professorCourses) {
				String courseInfo = c.getCourseCode() + " - " + c.getTitle();
				professor.addCourse(courseInfo, c.getFastSearch());
				coursesOffered.getItems().add(courseInfo);
			}
			professorName.setText(professor.getName());
			title.setText(professor.getTitle());
			office.setText(professor.getOffice());
			email.setText(professor.getEmail());
			email.setOnMouseClicked(event -> openEmail(((Label)event.getSource()).getText()));
			email.setOnMouseEntered(event -> email.getScene().setCursor(Cursor.HAND));
			email.setOnMouseExited(event -> email.getScene().setCursor(Cursor.DEFAULT));
			phoneNumber.setText(professor.getPhoneNumber());
			phoneNumber.setOnMouseClicked(event -> openPhone(((Label)event.getSource()).getText()));
			phoneNumber.setOnMouseEntered(event -> email.getScene().setCursor(Cursor.HAND));
			phoneNumber.setOnMouseExited(event -> email.getScene().setCursor(Cursor.DEFAULT));
			try {
				professorImage.setImage(new Image(professor.getPicture()));
			} catch (Exception e) {}
		} catch (Exception e) {
			e.printStackTrace();
			outputMessage(AlertType.ERROR, e.getMessage());
			close();
		}
	}
	
	private void outputMessage(AlertType alertType, String message) {
		Alert alert = new Alert(alertType, message);
		alert.initOwner((Stage)professorName.getScene().getWindow());
		alert.showAndWait();
	}
	
	private void openEmail(String email) {
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI("mailto:" + email));
		} catch (Exception e) {
			e.printStackTrace();
			outputMessage(AlertType.ERROR, e.getMessage());
		}
	}
	
	private void openPhone(String phoneNumber) {
		Desktop d = Desktop.getDesktop();
		try {
			String formattedNumber = phoneNumber.replaceAll("[^\\d]", "");
			d.browse(new URI("tel:" + formattedNumber));
		} catch (Exception e) {
			e.printStackTrace();
			outputMessage(AlertType.ERROR, e.getMessage());
		}
	}
}
