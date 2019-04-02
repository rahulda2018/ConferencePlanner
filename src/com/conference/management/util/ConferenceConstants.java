package com.conference.management.util;

/**
 * This class holds the final constants values
 */
public class ConferenceConstants {

	/**
	 * Morning 9:00 O'clock to 12 noon is equal to 180 minutes
	 */
    public static final int FIRST_HALF_DURATION = 180;
    
    /**
	 * Duration from afternoon-1:00 O'clock to 5:00 O'clock is equal to 240 minutes
	 */
    public static final int SECOND_HALF_DURATION = 240;
    
    /**
     * Total Track Duration: FIRST_HALF_DURATION + SECOND_HALF_DURATION = 420
     */
    public static final int TRACK_DURATION = 420;
    
    /**
     * input file which contains Talk list. 
     * To test difference scenarios this file needs to be updated
     */
    public static final String TALKS_INPUT_FILE = "test-input.txt";

}
