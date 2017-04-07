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
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE fast_search IN ('" + fastSearchCode + "') LIMIT 1;");
			if (rs.next()) {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses;");
			while (rs.next()) {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE (title LIKE '%" + searchTerm + "%' or instructors LIKE '%" + searchTerm + "%' or description LIKE '%" + searchTerm + "%');");
			while (rs.next()) {
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
	
	public static ArrayList<Course> getCoursesAdvancedSearch(Course tempCourse) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		boolean addAnd = false;
		ArrayList<Course> courseData = new ArrayList<Course>();
		StringBuilder searchString = new StringBuilder();
		if (tempCourse != null) {
			searchString.append(" WHERE (");
			if (!tempCourse.getSemester().equals("")) {
				searchString.append("semester LIKE '%" + tempCourse.getSemester() + "%'");
				addAnd = !addAnd;
			}
			if (!(tempCourse.getSubjectCode() == null)) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("subject_code LIKE '%" + tempCourse.getSubjectCode() + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getCourseNumber().equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("course_number LIKE '%" + tempCourse.getCourseNumber() + "%'");
				addAnd = !addAnd;
			}
			if (!Integer.toString(tempCourse.getFastSearch()).equals("0")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("fast_search LIKE '%" + Integer.toString(tempCourse.getFastSearch()) + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getTitle().equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("title LIKE '%" + tempCourse.getTitle() + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getInstructorsArrayList().get(0).equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("instructors LIKE '%" + tempCourse.getInstructorsArrayList().get(0) + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getPeriod().equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("period LIKE '%" + tempCourse.getPeriod() + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getBuilding().equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("building LIKE '%" + tempCourse.getBuilding() + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getRoom().equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("room LIKE '%" + tempCourse.getRoom() + "%'");
				addAnd = !addAnd;
			}
			if (!tempCourse.getDescription().equals("")) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("description LIKE '%" + tempCourse.getDescription() + "%'");
				addAnd = !addAnd;
			}
			if (!(tempCourse.getCollegeCodes().get(0) == null)) {
				if (addAnd) {
					searchString.append(" and ");
					addAnd = !addAnd;
				}
				searchString.append("college_codes LIKE '%" + tempCourse.getCollegeCodes().get(0) + "%'");
				addAnd = !addAnd;
			}
			searchString.append(");");
		}
		try {
			stmt = c.createStatement();
			System.out.println(searchString.toString());
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses" + searchString.toString());
			while (rs.next()) {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM collegiate_center_codes WHERE short_name IN ('" + shortName + "') LIMIT 1;");
			if (rs.next()) {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM collegiate_center_codes;");
			while (rs.next()) {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM time_codes WHERE code IN ('" + code + "') LIMIT 1;");
			if (rs.next()) {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM time_codes;");
			while (rs.next()) {
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

	public static AcademicSubject getAcademicSubject(String shortName) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		AcademicSubject academicSubject = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM academic_subjects WHERE code IN ('" + shortName + "') LIMIT 1;");
			if (rs.next()) {
				academicSubject = new AcademicSubject(rs.getString("short_name"), rs.getString("long_name"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return academicSubject;
	}
	
	public static ArrayList<AcademicSubject> getAllAcademicSubjects() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		ArrayList<AcademicSubject> academicSubjectData = new ArrayList<AcademicSubject>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM academic_subjects;");
			while (rs.next()) {
				academicSubjectData.add(new AcademicSubject(rs.getString("short_name"), rs.getString("long_name")));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return academicSubjectData;
	}

	public static ArrayList<String> getScheduleNames() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		ArrayList<String> scheduleNames = new ArrayList<String>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM saved_schedules;");
			while (rs.next()) {
				scheduleNames.add(rs.getString("name"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scheduleNames;
	}
	
	public static int[] getSchedule(String name) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		int[] scheduleCodes = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM saved_schedules WHERE code IN ('" + name + "') LIMIT 1;");
			if (rs.next()) {
				scheduleCodes = Arrays.asList(rs.getString("codes").split(", ")).stream().mapToInt(Integer::parseInt).toArray();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scheduleCodes;
	}
	
	public static void saveSchedule(String name, int[] codes) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		try {
			stmt = c.createStatement();
			stmt.executeUpdate("INSERT INTO saved_schedules (name,codes) VALUES (" + name + ", '" + codes.toString() + "' );");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteSchedule(String name) {
		try {
			stmt = c.createStatement();
			stmt.executeUpdate("DELETE FROM saved_schedules WHERE name IN ('" + name + "');");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}