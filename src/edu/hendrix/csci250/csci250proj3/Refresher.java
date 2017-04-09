package edu.hendrix.csci250.csci250proj3;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

public class Refresher {
	private final static String HENDRIX_COURSE_URL = "http://hoike.hendrix.edu/api/CourseModel?$filter=YearCode%20eq%202017&$orderby=CourseCode&$skip=0";

	public static void refreshDB() throws Exception {
		String building = "";
		String room = "";
		String description = "";
		FileOps.backupDB();
		SQL.deleteAllCourses();
		ArrayList<Course> courses = new ArrayList<Course>();
		Scanner scanner = new Scanner(new URL(HENDRIX_COURSE_URL).openStream(), "UTF-8");
		scanner.useDelimiter("\\A");
		JsonArray items = Json.parse(scanner.next()).asObject().get("value").asArray();
		scanner.close();
		for (JsonValue item : items) {
			String courseCode = item.asObject().getString("CourseCode", "").replaceAll("  ", " ").trim();
			String semester = item.asObject().getString("TermCode", "");
			String subjectCode = item.asObject().getString("SubjectCode", "");
			String courseNumber = item.asObject().getString("CourseNumber", "");
			String sectionNumber = item.asObject().getString("SectionCode", "");
			int fastSearch = item.asObject().getInt("FastSearchId", 00000);
			String title = item.asObject().getString("Title", "").replaceAll("  ", " ").trim();
			JsonArray professorArray = item.asObject().get("Instructors").asArray();
			ArrayList<String> instructors = new ArrayList<String>();
			for (JsonValue p : professorArray) {
				String professorName = p.asObject().getString("LastName", "") + ", " + p.asObject().getString("FirstName", "");
				instructors.add(professorName);
			}
			String period = item.asObject().getString("Period", "");
			JsonArray locationArray = item.asObject().get("Schedule").asArray();
			for (JsonValue l : locationArray) {
				if (building.equals("")) {
					building = l.asObject().getString("Building", "");
				}
				if (room.equals("")) {
					room = l.asObject().getString("Room", "");
				}
			}
			JsonArray collegeCodesArray = item.asObject().get("CollegiateCodes").asArray();
			ArrayList<String> collegeCodes = new ArrayList<String>();
			for (JsonValue collegeCode : collegeCodesArray) {
				collegeCodes.add(collegeCode.asString());
			}
			try {
				description = item.asObject().getString("Description", "");
			} catch (UnsupportedOperationException e) {}
			courses.add(new Course(courseCode, semester, subjectCode, courseNumber, sectionNumber, fastSearch, title, instructors, period, building, room, description, collegeCodes));
		}
		SQL.addCourses(courses);
		FileOps.deleteDBBackup();
	}
}
