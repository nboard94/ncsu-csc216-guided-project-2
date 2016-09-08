package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**Manages interactions with a course catalog or a student's schedule
 * 
 * @author Nick Board
 */
public class WolfScheduler {
	
	/** Catalog of all available classes*/
	ArrayList<Course> catalog;
	/** All classes in a particular schedule*/
	ArrayList<Course> schedule;
	/** Schedule's title*/
	String scheduleTitle;
	
	/**Constructs a WolfScheduler object.  Initializes the catalog and schedule
	 * arrays, sets the default schedule title, and tries to read course 
	 * records into the catalog
	 * @param fileName Filename for setting up the catalog
	 * @throws IllegalArgumentException Thrown if the specified file isn't found
	 */
	public WolfScheduler(String fileName) {
		catalog = new ArrayList<Course>();
		schedule = new ArrayList<Course>();
		this.setTitle("My Schedule");
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
	

	/**Retrieves a course from the course catalog
	 * @param name Name of a course to compare to catalog
	 * @param section Section of a course to compare to catalog
	 * @return Returns a course object if found in the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		Course currentCourse;
		for (int i = 0; i < catalog.size(); i++) {
			currentCourse = catalog.get(i);
			if (currentCourse.getName().equals(name) && currentCourse.getSection().equals(section)) {
				return currentCourse;
			}
		}
		return null;
	}


	/**Adds a course to a schedule
	 * @param name Name of course to add to schedule
	 * @param section Section of course to add to schedule
	 * @return true If the specified course can be added to the schedule
	 */
	public boolean addCourse(String name, String section) {
		
		Course currentCourse;
	
		
		for (int i = 0; i < schedule.size(); i++) {
			currentCourse = schedule.get(i);
			if (currentCourse.getName().equals(name)) {
				throw new IllegalArgumentException("You are already enrolled in " + name);
			}
		}
		
		for (int i = 0; i < catalog.size(); i++) {
			currentCourse = catalog.get(i);
			if (currentCourse.getName().equals(name) && currentCourse.getSection().equals(section)) {
				schedule.add(currentCourse);
				return true;
			}
		}

		return false;
	}


	/**Removes a course from a schedule
	 * @param name Name of course to remove from schedule
	 * @param section Section of course to remove from schedule
	 * @return true If course can be removed
	 */
	public boolean removeCourse(String name, String section) {
		Course currentCourse;
		
		for (int i = 0; i < schedule.size(); i++) {
			currentCourse = schedule.get(i);
			if (currentCourse.getName().equals(name) && currentCourse.getSection().equals(section)) {
				schedule.remove(currentCourse);
				return true;
			}
		}
		return false;
	}


	/**
	 * Resets a schedule by initializing and empty ArrayList of Courses
	 * and sets schedule to that empty ArrayList
	 */
	public void resetSchedule() {
		ArrayList<Course> emptySchedule = new ArrayList<Course>();
		schedule = emptySchedule;	
	}


	/**Retrieves the entire course catalog
	 * @return catalogArray The entire course catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][3];
		
		if (catalog.size() == 0) {
			return catalogArray;
		}
		
		Course currentCourse;
		for (int i = 0; i < catalog.size(); i++) {
			currentCourse = catalog.get(i);
			catalogArray[i][0] = currentCourse.getName();
			catalogArray[i][1] = currentCourse.getSection();
			catalogArray[i][2] = currentCourse.getTitle();
		}
		
		return catalogArray;
	}

	/**Retrieves scheduled courses
	 * @return scheduleArray all scheduled courses
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][3];
		
		if (schedule.size() == 0) {
			return scheduleArray;
		}
		
		Course currentCourse;
		for (int i = 0; i < schedule.size(); i++) {
			currentCourse = schedule.get(i);
			scheduleArray[i][0] = currentCourse.getName();
			scheduleArray[i][1] = currentCourse.getSection();
			scheduleArray[i][2] = currentCourse.getTitle();
		}
		
		return scheduleArray;
	}

	/**Retrieves all information for scheduled courses
	 * @return scheduleArray detailed version of all scheduled courses
	 */
	public String[][] getFullScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][6];
		
		if (schedule.size() == 0) {
			return scheduleArray;
		}
		
		Course currentCourse;
		for (int i = 0; i < schedule.size(); i++) {
			currentCourse = schedule.get(i);
			scheduleArray[i][0] = currentCourse.getName();
			scheduleArray[i][1] = currentCourse.getSection();
			scheduleArray[i][2] = currentCourse.getTitle();
			scheduleArray[i][3] = String.valueOf(currentCourse.getCredits());
			scheduleArray[i][4] = currentCourse.getInstructorId();
			scheduleArray[i][5] = currentCourse.getMeetingString();
		}
		
		return scheduleArray;
	}

	/**Gets the schedule's title
	 * @return scheduleTitle the title of the schedule
	 */
	public String getTitle() {
		return this.scheduleTitle;
	}


	/**Sets the title
	 * @param title What to set the title to
	 * @throws IllegalArgumentException The title can't be null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.scheduleTitle = title;
	}


	/**Exports schedule to a document
	 * @param fileName the file to export to
	 * @throws IllegalArgumentException If there is an error saving the file
	 */
	public void exportSchedule(String fileName) {
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}

}
