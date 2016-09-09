package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Description of Course
 * @author Nick Board
 *			Abstract class that contains methods for both the
 *			Course and Event classes.  
 */
public abstract class Activity {

	/** @inheritDoc */
	private String title;
	/** @inheritDoc */
	protected String meetingDays;
	/** @inheritDoc */
	private int startTime;
	/** @inheritDoc */
	private int endTime;

	/**Abstract Activity constructor
	 * @param title
	 * @param meetingDays
	 * @param startTime
	 * @param endTime
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
		
	}

	/**
	 * @inheritDoc
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @inheritDoc
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException();
		}
		this.title = title;
	}

	/**
	 * @inheritDoc
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * @inheritDoc
	 */
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null) {
			throw new IllegalArgumentException();
		}
		if (!meetingDays.toUpperCase().matches("[AMTWHF]+")) {
			throw new IllegalArgumentException();
		}
		if (meetingDays.toUpperCase().contains("A") && meetingDays.length() != 1) {
			throw new IllegalArgumentException();
		}
	
		this.meetingDays = meetingDays;
	}

	/**Returns a string with the days and times an activity takes place.
	 * Converts the military times into 12-hour, adds "am" and "pm".
	 * @return String A string describing the days an activity meets
	 */
	public String getMeetingString() {
		String days = getMeetingDays();
		if (days.equals("A")) {
			return "Arranged";
		}
	
		int milTimeStart = getStartTime();
		int startHours = milTimeStart / 100;
		int startMinutes = milTimeStart % 100;
		String startTimeNew = "";
	
		if (startHours < 12) {
			startTimeNew = startHours + ":" + String.format("%02d", startMinutes) + "AM";
		}
		if (startHours == 12) {
			startTimeNew = startHours + ":" + String.format("%02d", startMinutes) + "PM";
		}
		if (startHours > 12) {
			startHours = startHours - 12;
			startTimeNew = startHours + ":" + String.format("%02d", startMinutes) + "PM";
		}
	
		int milTimeEnd = getEndTime();
		int endHours = milTimeEnd / 100;
		int endMinutes = milTimeEnd % 100;
		String endTimeNew = "";
	
		if (endHours < 12) {
			endTimeNew = endHours + ":" + String.format("%02d", endMinutes) + "AM";
		}
		if (endHours == 12) {
			endTimeNew = endHours + ":" + String.format("%02d", endMinutes) + "PM";
		}
		if (endHours > 12) {
			endHours = endHours - 12;
			endTimeNew = endHours + ":" + String.format("%02d", endMinutes) + "PM";
		}
	
		return days + " " + startTimeNew + "-" + endTimeNew;
	}

	/**gets the time an activity starts
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**gets the time an activity ends
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**sets the time an activity starts and ends.
	 * Checks for valid times and makes sure endTime
	 * is after startTime
	 * @param startTime
	 *            the startTime to set
	 * @param endTime the endTime to set
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (startTime < 0 || startTime > 2359) {
			startTime = 0;
			throw new IllegalArgumentException();
		}
		if (endTime < 0 || endTime > 2359) {
			endTime = 0;
			throw new IllegalArgumentException();
		}
		if ((startTime % 100) - 60 >= 0) {
			throw new IllegalArgumentException();
		}
		if ((endTime % 100) - 60 >= 0) {
			throw new IllegalArgumentException();
		}
		if (endTime < startTime) {
			throw new IllegalArgumentException();
		}
		if (this.meetingDays.toUpperCase().contains("A") && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException();
		}
	
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Abstract method header to make sure this method is in both Course and Event
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Abstract method header to make sure this method is in both Course and Event
	 */
	public abstract String[] getLongDisplayArray();

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	/**
	 * Abstract method header to make sure this method is in both Course and Event
	 */
	public abstract boolean isDuplicate(Activity activity);


}