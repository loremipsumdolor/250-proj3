package edu.hendrix.csci250.csci250proj3;

public class Course {
	
	private String name;
	private int classCode;
	
	public Course(String name) {
		this.setName(name);
		this.setClassCode(100);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClassCode() {
		return classCode;
	}

	public void setClassCode(int classCode) {
		this.classCode = classCode;
	}

}
