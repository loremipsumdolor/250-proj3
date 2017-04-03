package edu.hendrix.csci250.csci250proj3;

public class CollegiateCenterCode {
	private String shortName;
	private String longName;
	private String description;
	
	public CollegiateCenterCode() {}
	
	public CollegiateCenterCode(String shortName, String longName, String description) {
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public String getLongName() {
		return longName;
	}
	
	public String getDescription() {
		return description;
	}
}
