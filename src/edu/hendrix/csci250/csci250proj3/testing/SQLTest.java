package edu.hendrix.csci250.csci250proj3.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import edu.hendrix.csci250.csci250proj3.CollegiateCenterCode;
import edu.hendrix.csci250.csci250proj3.Course;
import edu.hendrix.csci250.csci250proj3.Professor;
import edu.hendrix.csci250.csci250proj3.SQL;

public class SQLTest {

	@Test
	public void SQLCourseTest() {
		Course testCourse = new Course("CSCI 352 01", "2S", "CSCI", "352", "01", 24196,
				"Scalable Software", new ArrayList<String>(Arrays.asList("Ferrer, Gabe")),
				"A3", "MCREY", "315", "Introduction to the computer science concepts necessary for the development of large software systems. Further exploration of object-oriented development, testing, and version control techniques introduced in CSCI 151. Emphasis placed on user-centric interface design and writing precise requirements. Projects incorporate relevant technologies for modern software design, including network programming, databases, and mobile devices. Assignments emphasize the integration of multiple concepts in the context of developing realistic software applications. Students complete several projects in teams. Prerequisite: CSCI 151", new ArrayList<String>(Arrays.asList("NS")));
		Course sqlCourse = null;
		try {
			sqlCourse = SQL.getCourse(24196);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(testCourse.getBuilding(), sqlCourse.getBuilding());
		assertEquals(testCourse.getCollegeCodes(), sqlCourse.getCollegeCodes());
		assertEquals(testCourse.getCourseCode(), sqlCourse.getCourseCode());
		assertEquals(testCourse.getCourseNumber(), sqlCourse.getCourseNumber());
		assertEquals(testCourse.getDescription(), sqlCourse.getDescription());
		assertEquals(testCourse.getFastSearch(), sqlCourse.getFastSearch());
		assertEquals(testCourse.getInstructors(), sqlCourse.getInstructors());
		assertEquals(testCourse.getPeriod(), sqlCourse.getPeriod());
		assertEquals(testCourse.getRoom(), sqlCourse.getRoom());
		assertEquals(testCourse.getSectionNumber(), sqlCourse.getSectionNumber());
		assertEquals(testCourse.getSemester(), sqlCourse.getSemester());
		assertEquals(testCourse.getSubjectCode(), sqlCourse.getSubjectCode());
		assertEquals(testCourse.getTitle(), sqlCourse.getTitle());
	}
	
	@Test
	public void SQLProfessorTest() {
		Professor testProfessor = new Professor("Ferrer, Gabe", "Math/Computer Science Professor",
				"Mathematics and Computer Science Dept.", "ferrer@hendrix.edu", "(501) 450-3879",
				"https://www.hendrix.edu/PatronPictures/000138484.jpg");
		Professor sqlProfessor = null;
		try {
			sqlProfessor = SQL.getProfessor("Ferrer, Gabe");
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(testProfessor.getName(), sqlProfessor.getName());
		assertEquals(testProfessor.getTitle(), sqlProfessor.getTitle());
		assertEquals(testProfessor.getOffice(), sqlProfessor.getOffice());
		assertEquals(testProfessor.getEmail(), sqlProfessor.getEmail());
		assertEquals(testProfessor.getPhoneNumber(), sqlProfessor.getPhoneNumber());
		assertEquals(testProfessor.getPicture(), sqlProfessor.getPicture());
	}
	
	@Test
	public void SQLCollegiateCenterCodeTest() {
		CollegiateCenterCode testCCCode = new CollegiateCenterCode("AC", "Artistic Creativity",
				"Experiences in which students explore their creative potential in art, music, dance, drama, film, or creative writing.");
		CollegiateCenterCode sqlCCCode = null;
		try {
			sqlCCCode = SQL.getCode("AC");
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(testCCCode.getShortName(), sqlCCCode.getShortName());
		assertEquals(testCCCode.getLongName(), sqlCCCode.getLongName());
		assertEquals(testCCCode.getDescription(), sqlCCCode.getDescription());
	}

}
