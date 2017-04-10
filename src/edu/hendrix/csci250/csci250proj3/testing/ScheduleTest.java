package edu.hendrix.csci250.csci250proj3.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import edu.hendrix.csci250.csci250proj3.Course;
import edu.hendrix.csci250.csci250proj3.Schedule;

public class ScheduleTest {
	
	Schedule schedule;
	
	
	@Before
	public void setup() {
		schedule = new Schedule();
		schedule.clearSchedule();
	}
	
	@Test
	public void testConstuctor() {
		assertEquals(0, schedule.getLength());
	}
	
	@Test
	public void testAddCourse() throws Exception {
		Course testCourse1 = new Course("CSCI 352 01", "2S", "CSCI", "352", "01", 24196, "Scalable Software",
				new ArrayList<String>(Arrays.asList("Ferrer, Gabe")), "A3", "MCREY", "315",
				"Introduction to the computer science concepts necessary for the development of large software systems. Further exploration of object-oriented development, testing, and version control techniques introduced in CSCI 151. Emphasis placed on user-centric interface design and writing precise requirements. Projects incorporate relevant technologies for modern software design, including network programming, databases, and mobile devices. Assignments emphasize the integration of multiple concepts in the context of developing realistic software applications. Students complete several projects in teams. Prerequisite: CSCI 151",
				new ArrayList<String>(Arrays.asList("NS")));
		schedule.addCourse(testCourse1);
		assertEquals(1, schedule.getLength());
	}

	@Test(expected=Exception.class)
	public void testAddSameCourse() throws Exception {
		Course course1 = new Course("CSCI 352 01", "2S", "CSCI", "352", "01", 24196, "Scalable Software",
				new ArrayList<String>(Arrays.asList("Ferrer, Gabe")), "A3", "MCREY", "315",
				"Introduction to the computer science concepts necessary for the development of large software systems. Further exploration of object-oriented development, testing, and version control techniques introduced in CSCI 151. Emphasis placed on user-centric interface design and writing precise requirements. Projects incorporate relevant technologies for modern software design, including network programming, databases, and mobile devices. Assignments emphasize the integration of multiple concepts in the context of developing realistic software applications. Students complete several projects in teams. Prerequisite: CSCI 151",
				new ArrayList<String>(Arrays.asList("NS")));
		Course course2 = new Course("CSCI 352 01", "2S", "CSCI", "352", "01", 24196, "Scalable Software",
				new ArrayList<String>(Arrays.asList("Ferrer, Gabe")), "A3", "MCREY", "315",
				"Introduction to the computer science concepts necessary for the development of large software systems. Further exploration of object-oriented development, testing, and version control techniques introduced in CSCI 151. Emphasis placed on user-centric interface design and writing precise requirements. Projects incorporate relevant technologies for modern software design, including network programming, databases, and mobile devices. Assignments emphasize the integration of multiple concepts in the context of developing realistic software applications. Students complete several projects in teams. Prerequisite: CSCI 151",
				new ArrayList<String>(Arrays.asList("NS")));
		schedule.addCourse(course1);
		schedule.addCourse(course2);
	}
	
	@Test(expected = Exception.class)
	public void testAddTimeConflictingCourse() throws Exception {
		Course course1 = new Course("CSCI 352 01", "2S", "CSCI", "352", "01", 24196, "Scalable Software",
				new ArrayList<String>(Arrays.asList("Ferrer, Gabe")), "A3", "MCREY", "315",
				"Introduction to the computer science concepts necessary for the development of large software systems. Further exploration of object-oriented development, testing, and version control techniques introduced in CSCI 151. Emphasis placed on user-centric interface design and writing precise requirements. Projects incorporate relevant technologies for modern software design, including network programming, databases, and mobile devices. Assignments emphasize the integration of multiple concepts in the context of developing realistic software applications. Students complete several projects in teams. Prerequisite: CSCI 151",
				new ArrayList<String>(Arrays.asList("NS")));
		Course course2 = new Course("ANTH 200 01", "2S", "ANTH", "352", "01", 24196, "Buried Cities and Lost Tribes",
				new ArrayList<String>(Arrays.asList("Hill, Brett")), "A3", "MILLS", "B", "This is a test",
				new ArrayList<String>(Arrays.asList("NS")));
		schedule.addCourse(course1);
		schedule.addCourse(course2);
	}
}
