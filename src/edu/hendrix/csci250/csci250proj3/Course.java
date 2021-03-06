package edu.hendrix.csci250.csci250proj3;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	private String courseCode, semester, subjectCode, courseNumber, sectionNumber, title, period, building, room, description;
	private int fastSearch;
	private ArrayList<String> collegeCodes, instructors;
	
	public Course() {}
	
	public Course(String courseCode, String semester, String subjectCode, String courseNumber, String sectionNumber, int fastSearch, String title, ArrayList<String> instructors, String period, String building, String room, String description, ArrayList<String> collegeCodes) {
		this.courseCode = courseCode;
		this.semester = semester;
		this.subjectCode = subjectCode;
		this.courseNumber = courseNumber;
		this.sectionNumber = sectionNumber;
		this.title = title;
		this.period = period;
		this.building = building;
		this.room = room;
		this.description = description;
		this.fastSearch = fastSearch;
		this.collegeCodes = collegeCodes;
		this.instructors = instructors;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getSemester() {
		return semester;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public String getSectionNumber() {
		return sectionNumber;
	}

	public String getTitle() {
		return title;
	}

	public String getPeriod() {
		return period;
	}

	public String getBuilding() {
		return building;
	}

	public String getRoom() {
		return room;
	}

	public String getDescription() {
		return description;
	}

	public int getFastSearch() {
		return fastSearch;
	}

	public ArrayList<String> getCollegeCodes() {
		return collegeCodes;
	}

	public String getInstructors() {
		return instructors.toString().replace("[", "").replace("]", "").trim();
	}
	
	public ArrayList<String> getInstructorsArrayList() {
		return instructors;
	}
	
}
