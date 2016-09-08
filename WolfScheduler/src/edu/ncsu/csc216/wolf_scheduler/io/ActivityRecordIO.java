package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;

public class ActivityRecordIO {

	public ActivityRecordIO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Writes the given list of Courses to 
	 * @param fileName fileName to write courses to
	 * @param activities array containing course objects
	 * @throws IOException if there is an issue writing to the output file
	 */
	public static void writeActivityRecords(String fileName, ArrayList<Activity> activities) throws IOException {
		
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (int i = 0; i < activities.size(); i++) {
		    fileWriter.println(activities.get(i).toString());
		}
	
		fileWriter.close();
	}

}
