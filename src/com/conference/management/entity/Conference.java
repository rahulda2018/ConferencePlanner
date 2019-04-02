package com.conference.management.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Conference entity class which will hold the all Conference related data 
 */
public class Conference {
	
	//Private Member Variables
	private List<Talk> talkList = null;
	private int totalTrackMinutes = 0;
	private int countTrack = 0;
	private int countTalks = 0;

	/**
	 * Constructor will instantiate the Conference object and talk-list
	 */
	public Conference() {
		talkList = new ArrayList<Talk>();
		
	}
	
	/* Setter and Getter methods for all member variables */
	
	public List<Talk> getTalkList() {
		return talkList;
	}

	public void setTalkList(List<Talk> trackTalks) {
		this.talkList = trackTalks;
	}

	public int getTotalTrackMinutes() {
		return totalTrackMinutes;
	}

	public void setTotalTrackMinutes(int totalTrackMinutes) {
		this.totalTrackMinutes = totalTrackMinutes;
	}

	public int getCountTrack() {
		return countTrack;
	}

	public void setCountTrack(int countTrack) {
		this.countTrack = countTrack;
	}

	public int getCountTalks() {
		return countTalks;
	}

	public void setCountTalks(int countTalks) {
		this.countTalks = countTalks;
	}
	
	public String toString() {
		return "Tract Count:" + countTrack + ", Talks Count: " + countTalks + ", Total Track Minutes: " 
						+ totalTrackMinutes + ", talkList size: " + talkList.size() ;
	}
}