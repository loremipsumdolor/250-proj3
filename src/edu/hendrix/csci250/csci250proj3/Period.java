package edu.hendrix.csci250.csci250proj3;

public class Period {
	private String code, description;
	
	public Period() {}
	
	public Period(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
}
