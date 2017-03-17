package edu.hendrix.csci250.csci250proj3;

public class Professor {
	
	private String name;
	private String email;
	
	public Professor(String name, String email) {
		this.setName(name);
		this.setEmail(email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
