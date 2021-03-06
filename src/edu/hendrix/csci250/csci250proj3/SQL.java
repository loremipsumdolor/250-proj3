package edu.hendrix.csci250.csci250proj3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SQL {
	private static Connection c;
	private static Statement stmt;
	
	private static void openConnection() throws Exception {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
			stmt = c.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("SQL connection error. Is epdb.db present in the root directory?");
		}
	}
	
	private static void closeConnection() throws Exception {
		stmt.close();
		c.close();
	}
	
	public static Course getCourse(int fastSearchCode) throws Exception {
		openConnection();
		Course courseData = null;
		try {
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
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve course with ID " + Integer.toString(fastSearchCode) + ".");
		}
		return courseData;
	}
	
	public static ArrayList<Course> getAllCourses() throws Exception {
		openConnection();
		ArrayList<Course> courseData = new ArrayList<Course>();
		try {
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
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve courses.");
		}
		return courseData;
	}
	
	public static void addCourses(ArrayList<Course> courses) throws Exception {
		openConnection();
		try {
			PreparedStatement pstmt = c.prepareStatement("INSERT INTO courses (course_code,semester,subject_code,course_number,section_number,fast_search,title,instructors,period,building,room,description,college_codes) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
			for (Course course : courses) {
				pstmt.setString(1, course.getCourseCode());
				pstmt.setString(2, course.getSemester());
				pstmt.setString(3, course.getSubjectCode());
				pstmt.setString(4, course.getCourseNumber());
				pstmt.setString(5, course.getSectionNumber());
				pstmt.setInt(6, course.getFastSearch());
				pstmt.setString(7, course.getTitle());
				pstmt.setString(8, course.getInstructorsArrayList().toString().replaceAll("\",\"", "\";\"").replace("[", "").replace("]", ""));
				pstmt.setString(9, course.getPeriod());
				pstmt.setString(10, course.getBuilding());
				pstmt.setString(11, course.getRoom());
				pstmt.setString(12, course.getDescription());
				pstmt.setString(13, course.getCollegeCodes().toString().replace("[", "").replace("]", ""));
				pstmt.executeUpdate();
			}
			pstmt.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not save schedule.");
		}
	}
	
	public static void deleteAllCourses() throws Exception {
		openConnection();
		try {
			stmt.executeUpdate("DELETE FROM courses;");
			stmt.executeUpdate("VACUUM;");
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No courses found.");
		}
	}
	
	public static ArrayList<Course> getCoursesBasicSearch(String searchTerm) throws Exception {
		openConnection();
		ArrayList<Course> courseData = new ArrayList<Course>();
		try {
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
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No courses found.");
		}
		return courseData;
	}
	
	public static ArrayList<Course> getCoursesAdvancedSearch(Course tempCourse) throws Exception {
		openConnection();
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
			if (!(tempCourse.getPeriod() == null)) {
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
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No courses found.");
		}
		return courseData;
	}
	
	public static Professor getProfessor(String name) throws Exception {
		openConnection();
		Professor professorData = null;
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM professors WHERE name IN ('" + name + "') LIMIT 1;");
			if (rs.next()) {
				professorData = new Professor(rs.getString("name"), rs.getString("title"), 
								rs.getString("office"), rs.getString("email"), rs.getString("phone"),
								rs.getString("picture"));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve professor with name " + name + ".");
		}
		return professorData;
	}
	
	public static ArrayList<Course> getProfessorCourses(String name) throws Exception {
		openConnection();
		ArrayList<Course> professorCourseData = new ArrayList<Course>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE (instructors LIKE '%" + name + "%');");
			while (rs.next()) {
				ArrayList<String> instructors = new ArrayList<String>(Arrays.asList(rs.getString("instructors").split("; ")));
				ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(rs.getString("college_codes").split(", ")));
				professorCourseData.add(new Course(rs.getString("course_code"), rs.getString("semester"), 
						rs.getString("subject_code"), rs.getString("course_number"), 
						rs.getString("section_number"), Integer.parseInt(rs.getString("fast_search")),
						rs.getString("title"), instructors, rs.getString("period"),
						rs.getString("building"), rs.getString("room"), rs.getString("description"),
						collegeCodes));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No courses found.");
		}
		return professorCourseData;
	}
	
	public static CollegiateCenterCode getCode(String shortName) throws Exception {
		openConnection();
		CollegiateCenterCode ccCode = null;
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM collegiate_center_codes WHERE short_name IN ('" + shortName + "') LIMIT 1;");
			if (rs.next()) {
				ccCode = new CollegiateCenterCode(rs.getString("short_name"), 
						rs.getString("long_name"), rs.getString("description"));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve Collegiate Center Code " + shortName + ".");
		}
		return ccCode;
	}
	
	public static ArrayList<CollegiateCenterCode> getAllCodes() throws Exception {
		openConnection();
		ArrayList<CollegiateCenterCode> codeData = new ArrayList<CollegiateCenterCode>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM collegiate_center_codes;");
			while (rs.next()) {
				codeData.add(new CollegiateCenterCode(rs.getString("short_name"), 
						rs.getString("long_name"), rs.getString("description")));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve Collegiate Center Codes.");
		}
		return codeData;
	}
	
	public static Period getPeriod(String code) throws Exception {
		openConnection();
		Period period = null;
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM periods WHERE code IN ('" + code + "') LIMIT 1;");
			if (rs.next()) {
				period = new Period(rs.getString("code"), rs.getString("description"));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve time code " + code + ".");
		}
		return period;
	}
	
	public static ArrayList<Period> getAllPeriods() throws Exception {
		openConnection();
		ArrayList<Period> periodData = new ArrayList<Period>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM periods;");
			while (rs.next()) {
				periodData.add(new Period(rs.getString("code"), rs.getString("description")));
			}
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve time codes.");
		}
		return periodData;
	}

	public static AcademicSubject getAcademicSubject(String shortName) throws Exception {
		openConnection();
		AcademicSubject academicSubject = null;
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM academic_subjects WHERE code IN ('" + shortName + "') LIMIT 1;");
			if (rs.next()) {
				academicSubject = new AcademicSubject(rs.getString("short_name"), rs.getString("long_name"));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve academic subject " + shortName + ".");
		}
		return academicSubject;
	}
	
	public static ArrayList<AcademicSubject> getAllAcademicSubjects() throws Exception {
		openConnection();
		ArrayList<AcademicSubject> academicSubjectData = new ArrayList<AcademicSubject>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM academic_subjects;");
			while (rs.next()) {
				academicSubjectData.add(new AcademicSubject(rs.getString("short_name"), rs.getString("long_name")));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve academic subjects.");
		}
		return academicSubjectData;
	}

	public static ArrayList<String> getScheduleNames() throws Exception {
		openConnection();
		ArrayList<String> scheduleNames = new ArrayList<String>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM saved_schedules;");
			while (rs.next()) {
				scheduleNames.add(rs.getString("name"));
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve schedule names.");
		}
		return scheduleNames;
	}
	
	public static ArrayList<Integer> getSchedule(String name) throws Exception {
		openConnection();
		ArrayList<Integer> courseCodes = new ArrayList<Integer>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM saved_schedules WHERE name IN ('" + name + "') LIMIT 1;");
			if (rs.next()) {
				ArrayList<String> scheduleCodesStringAL = new ArrayList<String>(Arrays.asList(rs.getString("codes").split(",")));
				for(String courseCode : scheduleCodesStringAL){
					courseCodes.add(Integer.parseInt(courseCode.trim()));
				}
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Schedule not found.");
		}
		return courseCodes;
	}
	
	public static void saveSchedule(String name, String codes) throws Exception {
		openConnection();
		try {
			stmt.executeUpdate("INSERT INTO saved_schedules (name,codes) VALUES ('" + name + "', '" + codes + "' );");
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not save schedule.");
		}
	}
	
	public static void deleteSchedule(String name) throws Exception {
		openConnection();
		try {
			stmt.executeUpdate("DELETE FROM saved_schedules WHERE name IN ('" + name + "');");
			stmt.executeUpdate("VACUUM;");
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not delete schedule.");
		}
	}
}