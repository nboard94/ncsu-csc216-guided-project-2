package edu.ncsu.csc216.wolf_scheduler.course;

public abstract class Activity {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	protected String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
		
	}

	/**gets title for a course
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**sets title for a course, unless null or empty
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException();
		}
		this.title = title;
	}

	/**gets the days a course meets
	 * @return meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**sets the days a course meets, unless empty,
	 * must be composed of characters A,M,T,W,H, or F,
	 * and if A, then the length must be one
	 * @param meetingDays
	 *            the meetingDays to set
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

	/**Returns a string with the days and times a course takes place.
	 * Converts the military times into 12-hour, adds "am" and "pm".
	 * @return String A string describing the days a course meets
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

	/**gets the time a course starts
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**gets the time a course ends
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**sets the time a course starts and ends.
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
	
	public abstract String[] getShortDisplayArray();
	
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

}