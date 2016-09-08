package edu.ncsu.csc216.wolf_scheduler.scheduler;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * Tests the WolfScheduler class.
 * 
 * @author Sarah Heckman
 */
public class WolfSchedulerTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/**
	 * Resets course_records.txt for use in other tests.
	 */
	@Before
	public void setUp() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	/**
	 * Tests WolfScheduler().
	 */
	@Test
	public void testWolfScheduler() {
		//Test with invalid file.  Should have an empty catalog and schedule. 
		WolfScheduler ws1 = new WolfScheduler(invalidTestFile);
		assertEquals(0, ws1.getCourseCatalog().length);
		assertEquals(0, ws1.getScheduledCourses().length);
		assertEquals(0, ws1.getFullScheduledCourses().length);
		assertEquals("My Schedule", ws1.getTitle());
		ws1.exportSchedule("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//Test with valid file containing 8 courses.  Will test other methods in other tests.
		WolfScheduler ws2 = new WolfScheduler(validTestFile);
		assertEquals(8, ws2.getCourseCatalog().length);		
	}
	
	/**
	 * Test WolfScheduler.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Attempt to get a course that doesn't exist
		assertNull(ws.getCourseFromCatalog("CSC492", "001"));
		
		//Attempt to get a course that does exist
		Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(c, ws.getCourseFromCatalog("CSC216", "001"));
	}
	
	/**
	 * Test WolfScheduler.addCourse().
	 */
	@Test
	public void testAddCourse() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Attempt to add a course that doesn't exist
		assertFalse(ws.addCourse("CSC492", "001"));
		assertEquals(0, ws.getScheduledCourses().length);
		assertEquals(0, ws.getFullScheduledCourses().length);
		
		Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		
		//Attempt to add a course that does exist
		assertTrue(ws.addCourse(NAME, SECTION));
		assertEquals(1, ws.getScheduledCourses().length);
		assertEquals(1, ws.getFullScheduledCourses().length);
		String [] course = ws.getFullScheduledCourses()[0];
		assertEquals(NAME, course[0]);
		assertEquals(SECTION, course[1]);
		assertEquals(TITLE, course[2]);
		assertEquals("" + CREDITS, course[3]);
		assertEquals(INSTRUCTOR_ID, course[4]);
		assertEquals(c.getMeetingString(), course[5]);
		
		//Attempt to add a course that already exists, even if different section
		try {
			ws.addCourse(NAME, "002");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in CSC216", e.getMessage());
		}
	}
	
	/**
	 * Test WolfScheduler.removeCourse().
	 */
	@Test
	public void testRemoveCourse() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Attempt to remove from empty schedule
		assertFalse(ws.removeCourse(NAME, SECTION));
		
		//Add some courses and remove them
		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC226", "001"));
		assertTrue(ws.addCourse("CSC116", "002"));
		assertEquals(3, ws.getScheduledCourses().length);
		assertEquals(3, ws.getFullScheduledCourses().length);
		
		//Check that removing a course that doesn't exist when there are 
		//scheduled courses doesn't break anything
		assertFalse(ws.removeCourse("CSC492", "001"));
		assertEquals(3, ws.getScheduledCourses().length);
		assertEquals(3, ws.getFullScheduledCourses().length);
		
		//Remove CSC226
		assertTrue(ws.removeCourse("CSC226", "001"));
		assertEquals(2, ws.getScheduledCourses().length);
		assertEquals(2, ws.getFullScheduledCourses().length);
		
		//Remove CSC116
		assertTrue(ws.removeCourse("CSC116", "002"));
		assertEquals(1, ws.getScheduledCourses().length);
		assertEquals(1, ws.getFullScheduledCourses().length);
		
		//Remove CSC216
		assertTrue(ws.removeCourse(NAME, SECTION));
		assertEquals(0, ws.getScheduledCourses().length);
		assertEquals(0, ws.getFullScheduledCourses().length);
		
		//Check that removing all doesn't break future adds
		assertTrue(ws.addCourse("CSC230", "001"));
		assertEquals(1, ws.getScheduledCourses().length);
		assertEquals(1, ws.getFullScheduledCourses().length);
	}
	
	/**
	 * Test WolfScheduler.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Add some courses and reset schedule
		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC226", "001"));
		assertTrue(ws.addCourse("CSC116", "002"));
		assertEquals(3, ws.getScheduledCourses().length);
		assertEquals(3, ws.getFullScheduledCourses().length);
		
		ws.resetSchedule();
		assertEquals(0, ws.getScheduledCourses().length);
		assertEquals(0, ws.getFullScheduledCourses().length);
		
		//Check that resetting doesn't break future adds
		assertTrue(ws.addCourse("CSC230", "001"));
		assertEquals(1, ws.getScheduledCourses().length);
		assertEquals(1, ws.getFullScheduledCourses().length);
	}
	
	/**
	 * Test WolfScheduler.getCourseCatalog().
	 */
	@Test
	public void testGetCourseCatalog() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Get the catalog and make sure contents are correct
		//Name, section, title
		String [][] catalog = ws.getCourseCatalog();
		//Row 0
		assertEquals("CSC116", catalog[0][0]);
		assertEquals("001", catalog[0][1]);
		assertEquals("Intro to Programming - Java", catalog[0][2]);
		//Row 1
		assertEquals("CSC116", catalog[1][0]);
		assertEquals("002", catalog[1][1]);
		assertEquals("Intro to Programming - Java", catalog[1][2]);
		//Row 2
		assertEquals("CSC116", catalog[2][0]);
		assertEquals("003", catalog[2][1]);
		assertEquals("Intro to Programming - Java", catalog[2][2]);
		//Row 3
		assertEquals("CSC216", catalog[3][0]);
		assertEquals("001", catalog[3][1]);
		assertEquals("Programming Concepts - Java", catalog[3][2]);
		//Row 4
		assertEquals("CSC216", catalog[4][0]);
		assertEquals("002", catalog[4][1]);
		assertEquals("Programming Concepts - Java", catalog[4][2]);
		//Row 5
		assertEquals("CSC216", catalog[5][0]);
		assertEquals("601", catalog[5][1]);
		assertEquals("Programming Concepts - Java", catalog[5][2]);
		//Row 6
		assertEquals("CSC226", catalog[6][0]);
		assertEquals("001", catalog[6][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", catalog[6][2]);
		//Row 7
		assertEquals("CSC230", catalog[7][0]);
		assertEquals("001", catalog[7][1]);
		assertEquals("C and Software Tools", catalog[7][2]);
	}
	
	/**
	 * Test WolfScheduler.getScheduledCourses().
	 */
	@Test
	public void testGetScheduledCourses() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Add some courses and get schedule
		//Name, section, title
		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC226", "001"));
		assertTrue(ws.addCourse("CSC116", "002"));
		
		String [][] schedule = ws.getScheduledCourses();
		//Row 1
		assertEquals("CSC216", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Programming Concepts - Java", schedule[0][2]);
		//Row 1
		assertEquals("CSC226", schedule[1][0]);
		assertEquals("001", schedule[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", schedule[1][2]);
		//Row 2
		assertEquals("CSC116", schedule[2][0]);
		assertEquals("002", schedule[2][1]);
		assertEquals("Intro to Programming - Java", schedule[2][2]);
	}
	
	/**
	 * Test WolfScheduler.getFullScheduledCourses()
	 */
	@Test
	public void testGetFullScheduledCourses() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Add some courses and get full schedule
		//Name, section, title, credits, instructor id, meeting string
		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC226", "001"));
		assertTrue(ws.addCourse("CSC116", "002"));
		
		String [][] schedule = ws.getFullScheduledCourses();
		//Row 1
		assertEquals("CSC216", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Programming Concepts - Java", schedule[0][2]);
		assertEquals("4", schedule[0][3]);
		assertEquals("sesmith5", schedule[0][4]);
		assertEquals("TH 1:30PM-2:45PM", schedule[0][5]);
		//Row 1
		assertEquals("CSC226", schedule[1][0]);
		assertEquals("001", schedule[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", schedule[1][2]);
		assertEquals("3", schedule[1][3]);
		assertEquals("tmbarnes", schedule[1][4]);
		assertEquals("MWF 9:35AM-10:25AM", schedule[1][5]);
		//Row 2
		assertEquals("CSC116", schedule[2][0]);
		assertEquals("002", schedule[2][1]);
		assertEquals("Intro to Programming - Java", schedule[2][2]);
		assertEquals("3", schedule[2][3]);
		assertEquals("spbalik", schedule[2][4]);
		assertEquals("MW 11:20AM-1:10PM", schedule[2][5]);
	}
	
	/**
	 * Test WolfScheduler.setTitle().
	 */
	@Test
	public void testSetTitle() {
		WolfScheduler ws = new WolfScheduler(validTestFile);
		
		//Set Title and check that changed
		ws.setTitle("New Title");
		assertEquals("New Title", ws.getTitle());
		
		//Check that exception is thrown if null title and no
		//change to title already there.
		try {
			ws.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("New Title", ws.getTitle());
		}
	}
	
	/**
	 * Test WolfScheduler.exportSchedule().
	 */
	@Test
	public void testExportSchedule() {
		//Test that empty schedule exports correctly
		WolfScheduler ws = new WolfScheduler(validTestFile);
		ws.exportSchedule("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//Add courses and test that exports correctly
		ws.addCourse("CSC216", "002");
		ws.addCourse("CSC226", "001");
		assertEquals(2, ws.getScheduledCourses().length);
		ws.exportSchedule("test-files/actual_schedule_export.txt");
		checkFiles("test-files/expected_schedule_export.txt", "test-files/actual_schedule_export.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new File (expFile));
			Scanner actScanner = new Scanner(new File(actFile));
			
			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}