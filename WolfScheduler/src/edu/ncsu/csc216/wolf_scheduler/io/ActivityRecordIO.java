package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;

/** writes any abstract activity, event or course, to a file
 * @author NBoar
 *
 */
public class ActivityRecordIO {

	/**
	 * Writes the given list of activities to 
	 * @param fileName fileName to write activities to
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
