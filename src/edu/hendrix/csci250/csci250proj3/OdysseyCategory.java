package edu.hendrix.csci250.csci250proj3;

public class OdysseyCategory {
	
	private String shortName, longName, description;
	
	public OdysseyCategory(String shortName, String longName, String description) {
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public String getLongName() {
		return this.longName;
	}
	
	public String getDescription() {
		return this.description;
	}
}
