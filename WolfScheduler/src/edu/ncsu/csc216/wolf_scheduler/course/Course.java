package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Description of Course
 * @author Nick Board
 *			class for a course objects contains fields for course
 *         object contains setter and getter methods contains overrides of
 *         equals(), hashCode(), and toString().  Abstracted from Activity class.
 */
public class Course extends Activity {      

	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	
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
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
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

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + ","
				+ getStartTime() + "," + getEndTime();
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#setMeetingDays(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
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
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#getShortDisplayArray()
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortArray = {this.getName(), this.getSection(), this.getTitle(), this.getMeetingString()};
		
		return shortArray;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#getLongDisplayArray()
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longArray = {this.getName(), this.getSection(), this.getTitle(), String.valueOf(this.getCredits()), this.getInstructorId(), this.getMeetingString(), ""};
		
		return longArray;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.wolf_scheduler.course.Activity#isDuplicate(edu.ncsu.csc216.wolf_scheduler.course.Activity)
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course && name.equals(((Course) activity).getName())) {
			return true;
		}
		return false;
	}

}
