package com.conference.management.entity;

/**
 * 
 * This is a talk class, it holds all information related to its talk 
 */
public class Talk {

    //Member variables
    private int duration;
    private String talkTitle;
    private int id;
    private boolean lunchFlag = false;
    private String networkingTitle;
    private boolean networkingFlag = false;
    private String sessionTime;
    private String lunchTitle;
    private String trackTitle;
    
    /**
     * Constructor will instantiate the Talk object
     * @param minutes 	Duration of the Tals
     * @param title		Title of the Talk
     * @param id		id of the Talk
     */
    public Talk(int minutes,String title,int id) {
        this.duration = minutes;
        this.talkTitle = title;
        this.id = id;

    }

    /* Setter and Getter methods for all member variables */
    
    public int getDuration() {
        return duration;
    }
    
    public void setMinutes(int minutes) {
        this.duration = minutes;
    }
    
    public String getTitle() {
        return talkTitle;
    }
    
    public void setTitle(String title) {
        this.talkTitle = title;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSessionTime() {
        return sessionTime;
    }
    
    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getLunchTitle() {
        return lunchTitle;
    }
    
    public void setLunchTitle(String lunchTitle) {
        this.lunchTitle = lunchTitle;
    }
    
    public boolean isLunchFlag() {
        return lunchFlag;
    }
    
    public void setLunchFlag(boolean lunchFlag) {
        this.lunchFlag = lunchFlag;
    }
    
    public String getNetworkingTitle() {
        return networkingTitle;
    }
    
    public void setNetworkingTitle(String networkingTitle) {
        this.networkingTitle = networkingTitle;
    }
    
    public boolean isNetworkingFlag() {
        return networkingFlag;
    }
    
    public void setNetworkingFlag(boolean networkingFlag) {
        this.networkingFlag = networkingFlag;
    }
    
    public String getTrackTitle() {
        return trackTitle;
    }
    
    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }   
    
    public String toString() {
    	return "Id:" + id + ", Duration: " + duration + ", Talk Title: "+ talkTitle + 
    				", Lunch: " + (lunchFlag? lunchTitle: lunchFlag) + 
    				", Network Flag: " + (networkingFlag? networkingTitle : networkingFlag);
        
    }
}
