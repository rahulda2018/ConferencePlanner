package com.conference.management.util;

import java.util.Comparator;

import com.conference.management.entity.Talk;

/**
 * 
 * The comparator to sort the TalkEntity according to the duration in descending order.
 */
public class TalkTimeComparator implements Comparator<Talk>{

    @Override
    public int compare(Talk t1, Talk t2) {
    	
        if(t1.getDuration() < t2.getDuration() ) {
            return 1;
        }
        
		return -1;
    }
}
