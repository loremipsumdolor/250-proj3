package edu.hendrix.csci250.csci250proj3;

public class AcademicSubject {
	
	private String shortName, longName;
	
	public AcademicSubject(String shortName, String longName) {
		this.shortName = shortName;
		this.longName = longName;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public String getLongName() {
		return this.longName;
	}

}
