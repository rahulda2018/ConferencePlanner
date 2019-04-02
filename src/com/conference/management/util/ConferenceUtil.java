package com.conference.management.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import com.conference.management.entity.Conference;
import com.conference.management.entity.Talk;
import com.conference.management.exception.ConferenceException;

/**
 * This is an Utility class for scheduling the Talks in the Conference into multiple Tracks
 * 
 * As per program Input will consider the following assumption 
 * 
 * <ul>
 * 		<li>The conference has multiple tracks each of which has a morning and afternoon session.</li>  
 * 		<li>Each session contains multiple talks.</li>  
 * 		<li>Morning sessions begin at 9am and must finish by 12 noon, for lunch.</li>  
 * 		<li>Afternoon sessions begin at 1pm and must finish in time for the networking event.</li>  
 * 		<li>The networking event can start no earlier than 4:00 and no later than 5:00.</li>  
 * 		<li>No talk title has numbers in it.</li>  
 * 		<li>All talk lengths are either in minutes (not hours) or lightning (5 minutes).</li>  
 * 		<li>Presenters will be very punctual; there needs to be no gap between sessions.</li>  
 * </ul>
 */
public class ConferenceUtil {
	
	private Logger LOGGER = AppLogger.getLogger(ConferenceUtil.class.getName());
    
	private Conference conference = null;
   
	/**
	 * Constructor to instantiate the Conference object
	 */
    public ConferenceUtil() {
    	conference = new Conference();
	}
    
    /**
     * Getter method to retrieve the Conference data
     * @return Returns <code>Conference</code> object which holds the conference related data  
     */
	public Conference getConference() {
		return conference;
	}
	
    /**
     * 
     * This is an utility method to read the file, populate the Talks List by extracting the talk-title, talk-time, 
     * total track count and generating the trackTalk <code>java.util.List</code> that contains 
     * <code>TalkEntity<code> object, and then sorting the list depending on the Talk's time.
     * 
     * @param fileName	File name of the TalksInput file or full path including the file name
     * @exception	FileNotFoundException 	If the 'fileName' not found
     * 				IOException		 		If failed to read the file after loading the file or failed to close the buffer reader
     * 
     */
    public void generateTalksFromInputFile(String fileName) throws ConferenceException {
    	
        int id =0;
        int noOfTracks = 0;
        FileInputStream fileInputStream = null;

        //Opening the file that contains Talks list
        try {
            fileInputStream = new FileInputStream(fileName);
            LOGGER.fine("Successfully opened the file " + fileName);
        } catch (FileNotFoundException e) {
        	LOGGER.severe("Failed to load file - " + fileName + ": " + e.getMessage());
        	e.printStackTrace();
        	
            throw new ConferenceException("Failed to load file -" + fileName + ": " + e.getMessage(), e);
        }
        
        //Reading the file 
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

        String strLine;

        int intMinutes;
        int totalMinutes = 0;

        System.out.println("Test Input :");
        System.out.println("");

        //Reading input file line by line
        try {
        	LOGGER.fine("Reading input file line by line..");
            
            while ((strLine = br.readLine()) != null) {
            	
                //if the line contains // then ignore assuming the comments in the file
            	if(strLine.contains("//") || strLine.isEmpty()) {
            		LOGGER.fine("assuming the comments in the file:  " + strLine);
                    
            		continue;
                }

                id = id +1 ;
                strLine = strLine.trim();
                
                System.out.println(strLine);
                LOGGER.fine("LINE: " + strLine);
                
                //parsing the title
                String title = strLine.substring(0, strLine.lastIndexOf(" "));
                LOGGER.fine("TITLE:: " + title);
                
                //parsing minutes
                String strMinutesWithUnit = strLine.substring(strLine.lastIndexOf(" ") + 1);
                String strMinutes = strLine.replaceAll("\\D+", "");
                LOGGER.fine("MinutesWithUnit: " + strMinutesWithUnit + ", Minutes before parsing: " + strMinutes);
                
                //Rule: All talk lengths are either in minutes (not hours) or lightning (5 minutes).
                if("lightning".equals(strMinutesWithUnit)) {
                	  
                    intMinutes = 5;
                    totalMinutes = totalMinutes + intMinutes;
                    LOGGER.fine("lightning - 5 mins, total - " + totalMinutes);
                } else {
                    intMinutes = Integer.parseInt(strMinutes);
                    totalMinutes = totalMinutes + intMinutes;
                    LOGGER.fine("total - " + totalMinutes);
                }

                // Instantiating Talk object
                Talk singleTalk = new Talk(intMinutes,title,id);

                // Adding the Talk object to the List of Track Talks
                conference.getTalkList().add(singleTalk);
                
                LOGGER.fine("No of Talk List - " + conference.getTalkList().size());

            }
        } catch (IOException ex) {
        	
        	LOGGER.severe("Failed to read the file after loading " + ex.getMessage());
            
        	throw new ConferenceException("Failed to read the file after loading " + ex.getMessage(), ex);
        }

        // Set the total no. of count talks.
        conference.setCountTalks(id);

        // set total no. of minutes of talks.
        conference.setTotalTrackMinutes(totalMinutes);

        // Calculate the no. of tracks
        int totalMinutesInDouble =  totalMinutes*1;

        int numberOfTracks =  totalMinutesInDouble/ConferenceConstants.TRACK_DURATION;

        double fractionalPart = numberOfTracks % 1;
        double integralPart = numberOfTracks - fractionalPart;

        int leftMinutes = totalMinutes - (int)integralPart*ConferenceConstants.TRACK_DURATION;

        LOGGER.fine("Time Left - " + leftMinutes);
        
        // if it is more than 1 or 1.x will consider the ceiling value -> 2 Tracks
        if (leftMinutes > 0) {
            noOfTracks = (int) integralPart + 1;
        } else {
            noOfTracks = (int) integralPart;
        }

        conference.setCountTrack(noOfTracks);
        
        LOGGER.fine("conference values - " + conference.toString());

        // Sort all talks based on the talks-time in descending order.
        Collections.sort(conference.getTalkList(), new TalkTimeComparator());

        //Close the input stream
        try {
            br.close();
        } catch (IOException e) {
            throw new ConferenceException("Failed to close the Buffer Reader", e);
        }

        System.out.println("");
    }

    
    /**
     * This method will read the talkList and schedule the Talks into Tracks
     * As per program Input will consider the following assumption 
	 * 
	 * <ul>
	 * 		<li>The conference has multiple tracks each of which has a morning and afternoon session.</li>  
	 * 		<li>Each session contains multiple talks.</li>  
	 * 		<li>Morning sessions begin at 9am and must finish by 12 noon, for lunch.</li>  
	 * 		<li>Afternoon sessions begin at 1pm and must finish in time for the networking event.</li>  
	 * 		<li>The networking event can start no earlier than 4:00 and no later than 5:00.</li>  
	 * 		<li>No talk title has numbers in it.</li>  
	 * 		<li>All talk lengths are either in minutes (not hours) or lightning (5 minutes).</li>  
	 * 		<li>Presenters will be very punctual; there needs to be no gap between sessions.</li>  
	 * </ul>
	 * 
     * @param trackIndex		Track Index
     * @param talkList			Track Talk List	
     * @param startTalkIndex	TalkIndex, starting from 0
     * @param totalTalkCount
     * 
     * @return Return the Talk Index from the List
     */
    public int scheduleTheTalksIntoTracks(int trackIndex, List<Talk> talkList, int startTalkIndex , 
    														int totalTalkCount) throws ConferenceException {
    	

		if(talkList == null || talkList.size() < 1) {
		 	LOGGER.severe("List is Empty, please schedule the Talks by calling 'scheduleTheTalksIntoTracks'");
		 	throw new ConferenceException("List is Empty, please schedule the Talks by calling 'scheduleTheTalksIntoTracks'");
		}
		
		if(totalTalkCount < 1) {
		 	LOGGER.severe("TotalTalkCount is invalid, please populate the Talk-list before scheduling the Conference or check the talk list");
		 	throw new ConferenceException("TotalTalkCount is invalid, please populate the Talk-list before scheduling the Conference or check the talk list");
		}
    	 
    	 
    	int morningHalf = ConferenceConstants.FIRST_HALF_DURATION;
        int afterNoonHalf = ConferenceConstants.SECOND_HALF_DURATION;
        
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        
    	//Starting from 9 AM 
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        LOGGER.fine("Setting the time from 9 AM onwards ");
        
        int talkIndex = 0;
        String talkTitle = null;
        
        LOGGER.fine("Scheduling the Talks for the first half (before lunch break - 12:00 pm)");
        
        for( talkIndex = startTalkIndex; talkIndex < totalTalkCount; talkIndex++ ) {

            // Get the combination of 180 and allocate it
            if (morningHalf >= talkList.get(talkIndex).getDuration()) {
                morningHalf = morningHalf - talkList.get(talkIndex).getDuration();
                talkTitle = sdf.format(cal.getTime()) + " " + talkList.get(talkIndex).getTitle() + 
                				" " + talkList.get(talkIndex).getDuration() + "min";
                talkList.get(talkIndex).setTitle(talkTitle);
                
                //adding the duration of the Talk
                cal.add(Calendar.MINUTE, talkList.get(talkIndex).getDuration());
                
                talkList.get(talkIndex).setTrackTitle("Track" + " " + (trackIndex + 1));
            }
            LOGGER.fine("Morning - Talk:" + talkList.get(talkIndex).toString());
            
            // incase Talk duration is lesser than the time left in the morning, can not fit into first half
            if (morningHalf < talkList.get(talkIndex).getDuration()) {
                break;
            }

            if (morningHalf > 0) {
                continue;
            }

            if (morningHalf <= 0) {
                break;
            }
        }
        
        //Lunch Break
        talkList.get(talkIndex).setLunchFlag(true);
        talkList.get(talkIndex).setLunchTitle("12:00 PM" + " " + "Lunch");
        
        LOGGER.fine("Set the Lunch Break at 12 PM for 1 hour " + talkList.get(talkIndex).toString());
        
        // 60 mins Lunch break
        cal.add(Calendar.MINUTE, 60);
        
        LOGGER.fine("Scheduling the Talks for the second half (after lunch break - 1:00 pm)");
        

        //Next index onwards should be allocated after lunch slots
        for(talkIndex = talkIndex+1; talkIndex < totalTalkCount; talkIndex++) {
            
        	// Get the combination of 240 and allocate it
            if ( afterNoonHalf >= talkList.get(talkIndex).getDuration() ) {
            	
                afterNoonHalf = afterNoonHalf - talkList.get(talkIndex).getDuration();
                talkTitle = sdf.format(cal.getTime()) + " " + talkList.get(talkIndex).getTitle() 
                							+ " " + talkList.get(talkIndex).getDuration() + "min";
                talkList.get(talkIndex).setTitle(talkTitle);
                
                //adding the duration of the Talk
                cal.add(Calendar.MINUTE, talkList.get(talkIndex).getDuration());
                
                talkList.get(talkIndex).setTrackTitle("Track" + " " + (trackIndex + 1));
            }
            LOGGER.fine("Afternoon - Talk:" + talkList.get(talkIndex).toString());
            
            // incase Talk duration is lesser than the time left in the evening, can not fit into second half
            if (afterNoonHalf < talkList.get(talkIndex).getDuration()) {
                break;
            }

            if (afterNoonHalf > 0) {
                continue;
            }

            if (afterNoonHalf <= 0) {
                break;
            }
        }

        if(totalTalkCount == talkIndex) {
        	--talkIndex;
        }
        
        
        //After 4 o'clock
        talkList.get(talkIndex).setNetworkingFlag(true);
        talkList.get(talkIndex).setNetworkingTitle("5:00 PM" + " " + "Networking Event");
        
        LOGGER.fine("Set the Networking Event after 5 o'clock " + talkList.get(talkIndex).toString());
        
        talkIndex++;
        return talkIndex;

    }

    /**
     * This method will print the output
     * 
     * Traverse Get the Talks from the List of talkList, and processed it one by one. every-time its extract the prepared trackTitle and
     * print it as the output of talks into tracks.
     * */
    public void populateTheTalksIntoTracks(List<Talk> talkList) throws ConferenceException  {

        System.out.println("Test output:");
        System.out.println("");
        String trackTitle = "0";
        
        if(talkList == null || talkList.size() < 1) {
        	LOGGER.severe("List is Empty, please populate the Talk-list before scheduling the Conference");
        	throw new ConferenceException("List is Empty, please populate the Talk-list before scheduling the Conference");
        }

        // Output the talks into tracks based on the totalTalks and the count of Talks.
        for(int trackCountIndex=0; trackCountIndex<talkList.size(); trackCountIndex++) {

        	
            // Print the Track Title
        	if(trackTitle == null || talkList.get(trackCountIndex).getTrackTitle() == null) {
        		LOGGER.severe("Track Title is empty, please populate the Talk-list before scheduling the Conference");
            	throw new ConferenceException("List is Empty, please populate the Talk-list before scheduling the Conference");
        	}
        	
            if(!trackTitle.equals(talkList.get(trackCountIndex).getTrackTitle())) {
            	LOGGER.fine("Printing Talk : : " + talkList.get(trackCountIndex).toString());
            	
                System.out.println(talkList.get(trackCountIndex).getTrackTitle() + ":");
                System.out.println("");
                trackTitle = talkList.get(trackCountIndex).getTrackTitle();
            }

            // Print the prepared talk's title for this Track
            System.out.println(talkList.get(trackCountIndex).getTitle());

            // if lunch flag set then output the Lunch Title
            if(talkList.get(trackCountIndex).isLunchFlag()) {
            	LOGGER.fine("Printing Lunch : " + talkList.get(trackCountIndex).toString());
            	
                System.out.println(talkList.get(trackCountIndex).getLunchTitle());
            }

            // if networking flag set then output the Networking Title
            if(talkList.get(trackCountIndex).isNetworkingFlag()) {
            	LOGGER.fine("Printing Networking Event : " + talkList.get(trackCountIndex).toString());
                System.out.println(talkList.get(trackCountIndex).getNetworkingTitle());
                // simple convention to display extra lines.
                System.out.println("");
                System.out.println("");
            }
        }
    }
}


