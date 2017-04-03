package edu.hendrix.csci250.csci250proj3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SQL {
	private static Connection c;
	private static Statement stmt;
	
	public static Course getCourse(int fastSearchCode) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		Course courseData = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses WHERE fast_search IN ('" + fastSearchCode + "') LIMIT 1;" );
			while ( rs.next() ) {
				ArrayList<String> instructors = new ArrayList<String>(Arrays.asList(rs.getString("instructors").split("; ")));
				ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(rs.getString("college_codes").split(", ")));
				courseData = new Course(rs.getString("course_code"), rs.getString("semester"), 
								rs.getString("subject_code"), rs.getString("course_number"), 
								rs.getString("section_number"), Integer.parseInt(rs.getString("fast_search")),
								rs.getString("title"), instructors, rs.getString("period"),
								rs.getString("building"), rs.getString("room"), rs.getString("description"),
								collegeCodes);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseData;
	}
	
	public static ArrayList<Course> getAllCourses() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		ArrayList<Course> courseData = new ArrayList<Course>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses;" );
			while ( rs.next() ) {
				ArrayList<String> instructors = new ArrayList<String>(Arrays.asList(rs.getString("instructors").split("; ")));
				ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(rs.getString("college_codes").split(", ")));
				courseData.add(new Course(rs.getString("course_code"), rs.getString("semester"), 
						rs.getString("subject_code"), rs.getString("course_number"), 
						rs.getString("section_number"), Integer.parseInt(rs.getString("fast_search")),
						rs.getString("title"), instructors, rs.getString("period"),
						rs.getString("building"), rs.getString("room"), rs.getString("description"),
						collegeCodes));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseData;
	}
	
	public static ArrayList<Course> getCoursesBasicSearch(String searchTerm) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		ArrayList<Course> courseData = new ArrayList<Course>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses WHERE (title LIKE '%" + searchTerm + "%' or instructors LIKE '%" + searchTerm + "%' or description LIKE '%" + searchTerm + "%');" );
			while ( rs.next() ) {
				ArrayList<String> instructors = new ArrayList<String>(Arrays.asList(rs.getString("instructors").split("; ")));
				ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(rs.getString("college_codes").split(", ")));
				courseData.add(new Course(rs.getString("course_code"), rs.getString("semester"), 
						rs.getString("subject_code"), rs.getString("course_number"), 
						rs.getString("section_number"), Integer.parseInt(rs.getString("fast_search")),
						rs.getString("title"), instructors, rs.getString("period"),
						rs.getString("building"), rs.getString("room"), rs.getString("description"),
						collegeCodes));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseData;
	}
	
	public static CollegiateCenterCode getCode(String shortName) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		CollegiateCenterCode ccCode = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM collegiate_center_codes WHERE short_name IN ('" + shortName + "') LIMIT 1;" );
			while ( rs.next() ) {
				ccCode = new CollegiateCenterCode(rs.getString("short_name"), 
						rs.getString("long_name"), rs.getString("description"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ccCode;
	}
	
	public static ArrayList<CollegiateCenterCode> getAllCodes() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		ArrayList<CollegiateCenterCode> codeData = new ArrayList<CollegiateCenterCode>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses;" );
			while ( rs.next() ) {
				codeData.add(new CollegiateCenterCode(rs.getString("short_name"), 
						rs.getString("long_name"), rs.getString("description")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codeData;
	}
	
	public static TimeCode getTimeCode(String code) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		TimeCode timeCode = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM time_codes WHERE code IN ('" + code + "') LIMIT 1;" );
			while ( rs.next() ) {
				timeCode = new TimeCode(rs.getString("code"), rs.getString("description"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timeCode;
	}
	
	public static ArrayList<TimeCode> getAllTimeCodes() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		ArrayList<TimeCode> timeCodeData = new ArrayList<TimeCode>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM time_codes;" );
			while ( rs.next() ) {
				timeCodeData.add(new TimeCode(rs.getString("code"), rs.getString("description")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timeCodeData;
	}
}