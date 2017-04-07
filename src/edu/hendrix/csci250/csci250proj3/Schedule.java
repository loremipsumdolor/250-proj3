package edu.hendrix.csci250.csci250proj3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Schedule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Course> schedule = new ArrayList<>(5);
	public static final String ScheduleFile = "scheduleFile.txt";
	private final static Schedule instance = new Schedule();
	
	public static Schedule getSchedule() {
		return instance;
	}
	
	public void addCourse(Course course) {
		schedule.add(course);
	}
	
	public void removeCourse(Course course) {
		schedule.remove(course);
	}
	
	public String getCourse(int index) {
		return schedule.get(index).getTitle().toString();
	}
	
	public boolean containsCourse(Course course) {
		return schedule.contains(course);
	}
	
	public String getCourses() {
		String courses = "";
		for (Course c : schedule) {
			courses += c.getTitle() + "\n";
		}
		return courses;
	}
	
	public static void saveSchedule(Schedule schedule) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(ScheduleFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(schedule);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static Schedule loadSchedule() {
		if (fileExists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(ScheduleFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Schedule tempSchedule = (Schedule) ois.readObject();
				ois.close();
				return tempSchedule;
			} catch (ClassNotFoundException | IOException e){
				e.printStackTrace();
			}
		}	
		Schedule tempSchedule = new Schedule();
		return tempSchedule;
	}
	
	private static boolean fileExists() {
		return new File(ScheduleFile).isFile();
	}
	
	private void outputMessage(AlertType alertType, String message) {
		Alert alert = new Alert(alertType, message);
		alert.showAndWait();
	}
}
