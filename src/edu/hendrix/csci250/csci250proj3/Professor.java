package edu.hendrix.csci250.csci250proj3;

import java.util.ArrayList;

public class Professor {
	
	private String name, office, email, phone, picture;
	private ArrayList<Course> coursesTeaching;
	
	public Professor(String name, String office, String email, String phone, String picture) {
		this.name = name;
		this.office = office;
		this.email = email;
		this.phone = phone;
		this.coursesTeaching = new ArrayList<Course>();
		this.picture = picture;
	}
	
	public void addCourse(Course course) {
		this.coursesTeaching.add(course);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getOffice() {
		return this.office;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNumber() {
		return this.phone;
	}
	
	public ArrayList<Course> getCourseList() {
		return this.coursesTeaching;
	}
	
	public String getPicture() {
		return this.picture;
	}
}
