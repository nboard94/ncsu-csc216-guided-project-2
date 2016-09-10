package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
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
	ArrayList<Activity> schedule;
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
		schedule = new ArrayList<Activity>();
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

	/** adds a course to the schedule if it can be matched to a course in the catalog
	 * and isn't a duplicate of one already in the schedule
	 * @param name name of the course to add
	 * @param section section of the course to add
	 * @throws IllegalArgumentException if that course is already in the schedule
	 * @return false if the course can't be added
	 */
	public boolean addCourse(String name, String section) {
		
		Activity currentCourse;
		Course courseToAdd = new Course (name, "title", section, 3, "id", "MWF");

		for (int i = 0; i < catalog.size(); i++) {
			currentCourse = catalog.get(i);
			if (currentCourse instanceof Course && ((Course) currentCourse).getName().equals(name) && ((Course)currentCourse).getSection().equals(section)) {
				courseToAdd.setTitle(currentCourse.getTitle());
				courseToAdd.setCredits(((Course) currentCourse).getCredits());
				courseToAdd.setInstructorId(((Course) currentCourse).getInstructorId());
				courseToAdd.setMeetingDays(currentCourse.getMeetingDays());
				courseToAdd.setActivityTime(currentCourse.getStartTime(), currentCourse.getEndTime());
			}
		}
			
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i) instanceof Course) {
				currentCourse = schedule.get(i);
				if (currentCourse.isDuplicate(courseToAdd)) {
					throw new IllegalArgumentException("You are already enrolled in " + name);
				}
				
			}
		}
		
		for (int i = 0; i < catalog.size(); i++) {
			currentCourse = catalog.get(i);
			if 	(((Course) currentCourse).getName().equals(courseToAdd.getName()) &&
				((Course) currentCourse).getSection().equals(courseToAdd.getSection())) {
				schedule.add(courseToAdd);
				return true;
			}
		}

		return false;
	}


 /** adds event to the schedule as long as it isn't a duplicate
 * @param eventTitle title of event to add to the schedule
 * @param eventMeetingDays days the event to add to the schedule will meet
 * @param eventStartTime stating time of event to add to the schedule
 * @param eventEndTime ending time of event to add to the schedule
 * @param eventWeeklyRepeat number of times the event will repeat
 * @param eventDetails extra details about the event
 * @throws IllegalArgumentException if event is a duplicate
 * @return false if event can't be added
 */
public boolean addEvent(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime,
			int eventWeeklyRepeat, String eventDetails) {
		
		Event eventToAdd = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventWeeklyRepeat, eventDetails);
		
		if (schedule.size() < 1) {
			schedule.add(eventToAdd);
			return true;
		}
		
		

		for (int i = 0; i < schedule.size(); i++) {

				if (schedule.get(i).isDuplicate(eventToAdd)) {
					throw new IllegalArgumentException("You have already created an event called " + eventTitle);
				}
				else if (!schedule.get(i).isDuplicate(eventToAdd)) {
					schedule.add(eventToAdd);
					return true;
				}
		}
		
		return false;
		
	}


	/**Removes a course from a schedule
	 * @param idx index of activity to remove in the schedule
	 * @return true If event can be removed
	 */
	public boolean removeActivity(int idx) {
		if (!schedule.isEmpty() ) {
			if (idx > (schedule.size() - 1)) {
				return false;
			}		
			schedule.remove(idx);
			return true;
		}
		
		return false;
	}


	/**
	 * Resets a schedule by initializing and empty ArrayList of activities
	 * and sets schedule to that empty ArrayList
	 */
	public void resetSchedule() {
		ArrayList<Activity> emptySchedule = new ArrayList<Activity>();
		schedule = emptySchedule;	
	}


	/**Retrieves the entire course catalog
	 * @return catalogArray The entire course catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][4];
		
		if (catalog.size() == 0) {
			return catalogArray;
		}
		
		Course currentCourse;
		for (int i = 0; i < catalog.size(); i++) {
			currentCourse = catalog.get(i);
			catalogArray[i][0] = currentCourse.getName();
			catalogArray[i][1] = currentCourse.getSection();
			catalogArray[i][2] = currentCourse.getTitle();
			catalogArray[i][3] = currentCourse.getMeetingString();
		}
		
		return catalogArray;
	}

	/**Retrieves scheduled activities
	 * @return scheduleArray all scheduled activities
	 */
	public String[][] getScheduledActivities() {
		String[][] scheduleArray = new String[schedule.size()][3];
		
		if (schedule.size() == 0) {
			return scheduleArray;
		}
		
		Activity currentActivity;
		for (int i = 0; i < schedule.size(); i++) {
			currentActivity = schedule.get(i);
			if (currentActivity instanceof Course) {
				scheduleArray[i][0] = ((Course) currentActivity).getName();
				scheduleArray[i][1] = ((Course) currentActivity).getSection();
				scheduleArray[i][2] = currentActivity.getTitle();
			}
			
			else if (currentActivity instanceof Event) {
				scheduleArray[i][0] = currentActivity.getTitle();
				scheduleArray[i][1] = currentActivity.getMeetingString();
				scheduleArray[i][2] = ((Event) currentActivity).getEventDetails();
			}
		}
	
		return scheduleArray;
	}

	/**Retrieves all information for scheduled activities
	 * @return scheduleArray detailed version of all scheduled activities
	 */
	public String[][] getFullScheduledActivities() {
		String[][] scheduleArray = new String[schedule.size()][7];
		
		if (schedule.size() == 0) {
			return scheduleArray;
		}
		
		Course currentCourse;
		Event currentEvent;
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i) instanceof Course) {
				currentCourse = ((Course)schedule.get(i));
				scheduleArray[i][0] = currentCourse.getName();
				scheduleArray[i][1] = currentCourse.getSection();
				scheduleArray[i][2] = currentCourse.getTitle();
				scheduleArray[i][3] = String.valueOf(currentCourse.getCredits());
				scheduleArray[i][4] = currentCourse.getInstructorId();
				scheduleArray[i][5] = currentCourse.getMeetingString();
				scheduleArray[i][6] = "";
			}
			
			else if (schedule.get(i) instanceof Event) {
				currentEvent = ((Event)schedule.get(i));
				scheduleArray[i][0] = "";
				scheduleArray[i][1] = "";
				scheduleArray[i][2] = currentEvent.getTitle();
				scheduleArray[i][3] = "";
				scheduleArray[i][4] = "";
				scheduleArray[i][5] = currentEvent.getMeetingString();
				scheduleArray[i][6] = currentEvent.getEventDetails();
			}
		}
		
		return scheduleArray;
	}

	/**Sets the title of the schedule
	 * @param title What to set the title to
	 * @throws IllegalArgumentException The title can't be null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.scheduleTitle = title;
	}


	/**Gets the schedule's title
	 * @return scheduleTitle the title of the schedule
	 */
	public String getTitle() {
		return this.scheduleTitle;
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
