package edu.hendrix.csci250.csci250proj3;

import java.util.ArrayList;
import java.util.Iterator;

public class Schedule {

	private static ArrayList<Course> schedule = new ArrayList<>();
	private final static Schedule instance = new Schedule();
	
	public static Schedule getSchedule() {
		return instance;
	}
	
	public void clearSchedule() {
		schedule.clear();
	}
	
	public void addCourse(Course course) throws Exception {
		int firstSemCourses = 0;
		int secondSemCourses = 0;
		if (schedule.contains(course)) {
			throw new Exception("Course already in schedule.");
		}
		for (Course c : schedule) {
			if (c.getSemester().equals(course.getSemester())) {
				if (course.getPeriod().equals(c.getPeriod())) {
					throw new Exception("You already have a class in this time slot.");
				}
			}
			if (c.getSemester().equals("1S")) {
				firstSemCourses++;
			} else {
				secondSemCourses++;
			}
		}
		if (firstSemCourses == 4 && !course.getCollegeCodes().contains("PA")) {
			throw new Exception("Your first semester schedule is full, but you may add a PA credit.");
		} else if (secondSemCourses == 4 && !course.getCollegeCodes().contains("PA")) {
			throw new Exception("Your second semester schedule is full, but you may add a PA credit.");
		} else if (firstSemCourses > 4) {
			throw new Exception("Your first semester schedule is full.");
		} else if (secondSemCourses > 4) {
			throw new Exception("Your second semester schedule is full.");
		}
		schedule.add(course);
	}
	
	public int getLength() {
		return schedule.size();
	}
	
	public void removeCourse(int fastSearch) {
		Iterator<Course> iter = schedule.iterator();
		while (iter.hasNext()) {
		    Course c = iter.next();
		    if (c.getFastSearch() == fastSearch)
		        iter.remove();
		}
	}
	
	public boolean containsCourse(Course course) {
		for (Course c : schedule) {
			if (c.getFastSearch() == course.getFastSearch()) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Course> getCourses() {
		return schedule;
	}
}
