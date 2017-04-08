package edu.hendrix.csci250.csci250proj3;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Refresher {
	private final static String HENDRIX_COURSE_URL = "http://hoike.hendrix.edu/api/CourseModel?$filter=YearCode%20eq%202017&$orderby=CourseCode&$skip=0";

	public static void refreshDB() throws Exception {
		ArrayList<Course> courses = new ArrayList<Course>();
		Scanner scanner = new Scanner(new URL(HENDRIX_COURSE_URL).openStream(), "UTF-8");
		scanner.useDelimiter("\\A");
		JsonArray items = Json.parse(scanner.next()).asObject().get("value").asArray();
		scanner.close();
		for (JsonValue item : items) {
			String courseCode = item.asObject().getString("CourseCode", null).replaceAll("  ", " ").trim();
			String semester = item.asObject().getString("TermCode", null);
			String subjectCode = item.asObject().getString("SubjectCode", null);
			String courseNumber = item.asObject().getString("CourseNumber", null);
			String sectionNumber = item.asObject().getString("SectionCode", null);
			int fastSearch = item.asObject().getInt("FastSearchId", 00000);
			String title = item.asObject().getString("Title", null).replaceAll("  ", " ").trim();
			JsonArray professorArray = item.asObject().get("Instructors").asArray();
			ArrayList<String> instructors = new ArrayList<String>();
			for (JsonValue p : professorArray) {
				String professorName = p.asObject().getString("FirstName", null) + " " + p.asObject().getString("LastName", null);
				instructors.add(professorName);
			}
			String period = item.asObject().getString("Period", null);
			JsonObject locationObject = item.asObject().get("Schedule").asObject();
			String building = locationObject.getString("Building", null);
			String room = locationObject.getString("Room", null);
			ArrayList<String> collegeCodes = new ArrayList<String>(Arrays.asList(item.asObject().getString("CollegiateCodes", "[]").split(", ")));
			String description = item.asObject().getString("Description", null);
			courses.add(new Course(courseCode, semester, subjectCode, courseNumber, sectionNumber, fastSearch, title, instructors, period, building, room, description, collegeCodes));
		}
	}
}
