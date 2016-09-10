/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Description of Event
 * @author Nick Board
 *			class for a course objects contains fields for event
 *         object contains setter and getter methods contains overrides of
 *         equals(), hashCode(), and toString().  Abstracted from Activity class.
 */
public class Event extends Activity {

	/** Number of times that an event will repeat */
	private int weeklyRepeat;
	/** String of extra details about an event */
	private String eventDetails;
	
	/** Constructor for Event object with start and endtime
	 * @param title title of the event
	 * @param meetingDays days the event will meet
	 * @param startTime starting time of the event
	 * @param endTime ending time of the event
	 * @param weeklyRepeat number of times the event will repeat
	 * @param eventDetails additional details for the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, int weeklyRepeat, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		this.setWeeklyRepeat(weeklyRepeat);
		this.setEventDetails(eventDetails);
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#setMeetingDays(java.lang.String)
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null) {
			throw new IllegalArgumentException();
		}
		if (!meetingDays.toUpperCase().matches("[USMTWHF]+")) {
			throw new IllegalArgumentException();
		}

		this.meetingDays = meetingDays;
	}
	
	/** returns the weeklyRepeat integer
	 * @return the weeklyRepeat
	 */
	public int getWeeklyRepeat() {
		return weeklyRepeat;
	}

	/** sets the weekly repeat integer, must be between 1-4
	 * @param weeklyRepeat the weeklyRepeat to set
	 * @throws IllegalArgumentException if invalid repeat integer, not 1-4
	 */
	public void setWeeklyRepeat(int weeklyRepeat) {
		if (weeklyRepeat < 1 || weeklyRepeat > 4) {
			throw new IllegalArgumentException("Invalid weekly repeat");
		}
		
		this.weeklyRepeat = weeklyRepeat;
	}

	/** returns the eventDetails string
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**sets the eventDetails string for an event
	 * @param eventDetails the eventDetails to set
	 * @throws IllegalArgumentException eventDetails cannot be null
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details");
		}
		
		this.eventDetails = eventDetails;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#getShortDisplayArray()
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortArray = { "", "", this.getTitle(), this.getMeetingString()};
		return shortArray;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#getLongDisplayArray()
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longArray = {"", "", this.getTitle(), "", "", this.getMeetingString(), this.getEventDetails()};
		return longArray;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#getMeetingString()
	 */
	@Override
	public String getMeetingString() {
		return super.getMeetingString() + " (every " + getWeeklyRepeat() + " weeks)";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getTitle() + "," + this.getMeetingDays() + "," + this.getStartTime() + "," + this.getEndTime() + "," + this.getWeeklyRepeat() + "," + this.getEventDetails();
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#isDuplicate(edu.ncsu.csc216.wolf_scheduler.course.Activity)
	 */
	@Override
	public boolean isDuplicate(Activity activity) {

		if (activity instanceof Event && this.getTitle().equals(activity.getTitle())) {
			return true;
		}
		
		return false;
	}

}
