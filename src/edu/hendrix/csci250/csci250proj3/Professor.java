package edu.hendrix.csci250.csci250proj3;

import java.util.HashMap;

public class Professor {
	
	private String name, title, office, email, phone, picture;
	private HashMap<String, Integer> coursesTeaching;
	
	public Professor(String name, String title, String office, String email, String phone, String picture) {
		this.name = name;
		this.title = title;
		this.office = office;
		this.email = email;
		this.phone = phone;
		this.coursesTeaching = new HashMap<String, Integer>();
		this.picture = picture;
	}
	
	public void addCourse(String courseInfo, int fastSearch) {
		this.coursesTeaching.put(courseInfo, fastSearch);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTitle() {
		return title;
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
	
	public HashMap<String, Integer> getCourseList() {
		return this.coursesTeaching;
	}
	
	public int getCourseInList(String courseInfo) {
		return this.coursesTeaching.get(courseInfo);
	}
	
	public String getPicture() {
		return this.picture;
	}
}
