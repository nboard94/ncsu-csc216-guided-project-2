package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 */
public class CourseRecordIO {

	 /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File(fileName));
	    ArrayList<Course> courses = new ArrayList<Course>();
	    while (fileReader.hasNextLine()) {
	        try {
	            Course course = readCourse(fileReader.nextLine());
	            boolean duplicate = false;
	            for (int i = 0; i < courses.size(); i++) {
	                Course c = courses.get(i);
	                if (course.getName().equals(c.getName()) &&
	                        course.getSection().equals(c.getSection())) {
	                    //it's a duplicate
	                    duplicate = true;
	                }
	            }
	            if (!duplicate) {
	                courses.add(course);
	            }
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return courses;
	}

	/**Takes in a line from an input file and 
	 * tokenizes it.  Then the parameters for the
	 * course objects are set.
	 * @throws IllegalArgumentException if there is an issue reading in the line
	 * @param nextLine
	 * @return
	 */
	private static Course readCourse(String nextLine) {
		Scanner lineScan = new Scanner(nextLine);
		lineScan.useDelimiter(",");

		try {
			
			Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", "MW", 1330, 1445);
			
			c.setName(lineScan.next());
			c.setTitle(lineScan.next());
			c.setSection(lineScan.next());
			c.setCredits(lineScan.nextInt());
			c.setInstructorId(lineScan.next());
			c.setMeetingDays(lineScan.next());
			
			if (lineScan.hasNextInt()) {
				c.setActivityTime(lineScan.nextInt(), lineScan.nextInt());
			}
			
			lineScan.close();
			return c;
					
		}
		catch (NoSuchElementException e) {
			lineScan.close();
			throw new IllegalArgumentException();
		}
		
	}
	
	

}
