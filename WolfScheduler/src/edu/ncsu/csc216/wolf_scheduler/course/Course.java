package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Description of Course
 * @author Nick Board
 *			class for a course objects contains fields for course
 *         object contains setter and getter methods contains overrides of
 *         equals(), hashCode(), and toString()
 */
public class Course {      
	
	
	
	
	
	

	/** Course's name. */
	private String name;
	/** Course's title. */
	private String title;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructor for a Course object
	 * 
	 * @param name
	 *            name of course
	 * @param title
	 *            title of course
	 * @param section
	 *            section of course
	 * @param credits
	 *            amount of credits for course
	 * @param instructorId
	 *            instructor's unity ID
	 * @param meetingDays
	 *            meeting days as series of chars
	 * @param startTime
	 *            starting time for course
	 * @param endTime
	 *            ending time for course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		setName(name);
		setTitle(title);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		setMeetingDays(meetingDays);
		setCourseTime(startTime, endTime);
	}

	/**
	 * Constructor for a course object with no starting or ending time
	 * 
	 * @param name
	 *            name of course
	 * @param title
	 *            title of course
	 * @param section
	 *            section of course
	 * @param credits
	 *            credit hours for house
	 * @param instructorId
	 *            instructor's unity ID
	 * @param meetingDays
	 *            meeting days as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**gets the name of a course
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**sets the name of a course, unless null, or the length is less than four or greater than six
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		if (name.length() < 4 || name.length() > 6) {
			throw new IllegalArgumentException();
		}
		this.name = name;
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

	/**gets the section number for a course
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**sets the section number for a course, as long as
	 * it isn't null, its length is three, and it is composed
	 * of digits
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException();
		}
		if (section.length() != 3) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < section.length(); i++) {
			if (!section.matches("[0-9]+")) {
				throw new IllegalArgumentException();
			}
		}
		this.section = section;
	}

	/**gets credits for a course
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**sets credits for a course,
	 * must be between 1 and 5
	 * @param credits
	 *            the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < 1 || credits > 5) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**gets Instructor's ID for a course
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**sets Instructor's ID for a course, unless null or empty
	 * @param instructorId
	 *            the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.equals("")) {
			throw new IllegalArgumentException();
		}
		this.instructorId = instructorId;
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
	public void setCourseTime(int startTime, int endTime) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + credits;
		result = prime * result + endTime;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (endTime != other.endTime)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
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
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (meetingDays.equals("A")) {
			return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays;
		}
		return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays + ","
				+ startTime + "," + endTime;
	}

}
