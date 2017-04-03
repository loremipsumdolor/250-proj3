package edu.hendrix.csci250.csci250proj3;

public class TimeCode {
	private String code, description;
	
	public TimeCode() {}
	
	public TimeCode(String code, String description) {
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
