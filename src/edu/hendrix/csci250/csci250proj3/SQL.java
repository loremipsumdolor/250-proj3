package edu.hendrix.csci250.csci250proj3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SQL {
	private Connection c;
	private Statement stmt;
	
	public SQL() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	public Course getCourse(int fastSearchCode) {
		Course courseData = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses WHERE fast_search IN ('" + fastSearchCode + "') LIMIT 1;" );
			while ( rs.next() ) {
				ArrayList<String> instructors = new ArrayList<String>(Arrays.asList(rs.getString("instructors").split(";")));
				ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(rs.getString("college_codes").split(", ")));
				courseData = new Course(rs.getString("course_code"), rs.getString("semester"), 
								rs.getString("subject_code"), rs.getString("course_number"), 
								rs.getString("section_number"), Integer.parseInt(rs.getString("fast_search")),
								rs.getString("title"), instructors, rs.getString("period"),
								rs.getString("building"), rs.getString("room"), rs.getString("description"),
								collegeCodes);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseData;
	}
	
	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> courseData = new ArrayList<Course>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses;" );
			while ( rs.next() ) {
				ArrayList<String> instructors = new ArrayList<String>(Arrays.asList(rs.getString("instructors").split(";")));
				ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(rs.getString("college_codes").split(", ")));
				courseData.add(new Course(rs.getString("course_code"), rs.getString("semester"), 
						rs.getString("subject_code"), rs.getString("course_number"), 
						rs.getString("section_number"), Integer.parseInt(rs.getString("fast_search")),
						rs.getString("title"), instructors, rs.getString("period"),
						rs.getString("building"), rs.getString("room"), rs.getString("description"),
						collegeCodes));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseData;
	}
}
