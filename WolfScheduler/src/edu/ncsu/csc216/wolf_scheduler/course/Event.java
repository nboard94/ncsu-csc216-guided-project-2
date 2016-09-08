/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * @author NBoar
 *
 */
public class Event extends Activity {

	private int weeklyRepeat;
	private String eventDetails;
	
	/**
	 * 
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, int weeklyRepeat, String eventDetails) {
		// TODO Auto-generated constructor stub
		super(title, meetingDays, startTime, endTime);
		this.setWeeklyRepeat(weeklyRepeat);
		this.setEventDetails(eventDetails);
	}
	
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
	
	/**
	 * @return the weeklyRepeat
	 */
	public int getWeeklyRepeat() {
		return weeklyRepeat;
	}

	/**
	 * @param weeklyRepeat the weeklyRepeat to set
	 */
	public void setWeeklyRepeat(int weeklyRepeat) {
		if (weeklyRepeat < 1 || weeklyRepeat > 4) {
			throw new IllegalArgumentException("Invalid weekly repeat");
		}
		
		this.weeklyRepeat = weeklyRepeat;
	}

	/**
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * @param eventDetails the eventDetails to set
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details");
		}
		
		this.eventDetails = eventDetails;
	}

	@Override
	public String[] getShortDisplayArray() {
		String[] shortArray = { "", "", this.getTitle(), this.getMeetingString()};
		return shortArray;
	}

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
		// TODO Auto-generated method stub
		return super.getMeetingString() + " (every " + getWeeklyRepeat() + " weeks)";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//title,meetingDays,startTime,endTime,weeklyRepeat,details
		return this.getTitle() + "," + this.getMeetingDays() + "," + this.getStartTime() + "," + this.getEndTime() + "," + this.getWeeklyRepeat() + "," + this.getEventDetails();
	}

}
