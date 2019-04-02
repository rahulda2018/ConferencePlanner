package com.conference.management;

import java.util.logging.Logger;

import com.conference.management.exception.ConferenceException;
import com.conference.management.util.AppLogger;
import com.conference.management.util.ConferenceConstants;
import com.conference.management.util.ConferenceUtil;

/**
 * This is the  main class. 
 * 
 * Here the program input <code>ConferenceConstants.TALKS_INPUT_FILE</code>. 
 * Incase user needs to change the content or update Talks the test-input.txt needs to be updated.  
 */

public class ConferencePlannerMain {
	
	private Logger LOGGER = AppLogger.getLogger(ConferencePlannerMain.class.getName());
    
	private void planConference() {
		
		LOGGER.info("Starting ConferencePlanner Application.. ");
		//Instantiate Conference object.
	    ConferenceUtil conferenceUtil = new ConferenceUtil();
	
	    try {
	    	//Populate the Talks by reading the input file. 
	    	LOGGER.info("Populate the Talks by reading the input file.. ");
	    	
	        conferenceUtil.generateTalksFromInputFile(ConferenceConstants.TALKS_INPUT_FILE);
	        
	        LOGGER.info("All the Talks populated successfully by reading the input file.. ");
	    } catch (ConferenceException ex) {
	    	
	    	LOGGER.severe("Failed to generate and process tals for conference. Reason:\n" 
					+ ex.getMessage());
	    	ex.printStackTrace();
	    	
	    	System.err.println("Failed to generate and process tals for conference. Reason:\n" 
					+ ex.getMessage());
	    	
	    	System.exit(1);
	    }
	    
	    if(conferenceUtil.getConference().getTalkList() == null || 
	    		conferenceUtil.getConference().getTalkList().size() == 0) {
	    	
	    	LOGGER.severe("TalkList is Empty or Invalid, Please provide the valid Talk-List");
	    	System.err.println("TalkList is Empty or Invalid, Please provide the valid Talk-List");
	    	
	    	System.exit(1);
	    }
	    
	    //Calculating the number of tracts
	    int numberOfTracks = conferenceUtil.getConference().getCountTrack();
	    if(numberOfTracks == 0) {
	    	
	    	LOGGER.severe("Track is Invalid, Please provide the valid Talk-List");
	    	System.err.println("Track is Invalid, Please provide the valid Talk-List");
	    	
	    	System.exit(1);
	    	
	    }	
	    
	    
	    int startTalkIndex = 0;
	    
	    try {
		    LOGGER.info("Scheduling the Talks into Tracks.. No Of Tracks - " + numberOfTracks);
		    //Scheduling the Talks into Tracks.
		    for(int trackIndex = 0; trackIndex <numberOfTracks; trackIndex++) {
		    	LOGGER.info("Track Index: " + trackIndex + ", Talk Index: " + startTalkIndex);
		    	startTalkIndex = conferenceUtil.scheduleTheTalksIntoTracks(
										trackIndex, 
										conferenceUtil.getConference().getTalkList(), 
										startTalkIndex, 
										conferenceUtil.getConference().getCountTalks());
		    }
		    
		    LOGGER.info("Scheduled the Talks into Tracks successfully.. ");
	    } catch (ConferenceException ex) {
	    
	    	LOGGER.severe("Cannot schedule the Talks\nReason:\n" + ex.getMessage());
	    	System.err.println("Cannot schedule the Talks\nReason:\n" + ex.getMessage());
	    
	    	System.exit(1);
	    }
	    
	    LOGGER.info("Populating the Talk schedule into tracks: " + conferenceUtil.getConference());
	    
	    try {
	    	//Populate the Talk schedule into tracks.
	    	conferenceUtil.populateTheTalksIntoTracks(conferenceUtil.getConference().getTalkList());
	    	LOGGER.info("Populated the Talk schedule into tracks successfully.. ");
	    
	    } catch (ConferenceException ex) {
	    	
	    	LOGGER.severe("Cannot populate the Talks into the Track(s), "
	    			+ "Talk List is ivalid or empty, Please provide the valid Talk-List. \nReason:\n" + ex.getMessage());
	    	
	    	System.err.println("Cannot populate the Talks into the Track(s), "
	    			+ "Talk List is ivalid or empty, Please provide the valid Talk-List. \nReason:\n" + ex.getMessage());
	    	
	    	System.exit(1);
	    }
	}


    public static void main(String[] args) {
    	
    	ConferencePlannerMain conferencePlanner = new ConferencePlannerMain();
    	conferencePlanner.planConference();
     	
    }
}
