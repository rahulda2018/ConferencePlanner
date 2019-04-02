# Problem Statement
===============================
## conference-track-planner

You are planning a big programming conference and have received many proposals which have passed the initial screen process but you're having trouble fitting them into the time constraints of the day -- there are so many possibilities! So you write a program to do it for you.

* The conference has multiple tracks each of which has a morning and afternoon session.  
* Each session contains multiple talks.  
* Morning sessions begin at 9am and must finish by 12 noon, for lunch.  
* Afternoon sessions begin at 1pm and must finish in time for the networking event.  
* The networking event can start no earlier than 4:00 and no later than 5:00.  
* No talk title has numbers in it.  
* All talk lengths are either in minutes (not hours) or lightning (5 minutes).  
* Presenters will be very punctual; there needs to be no gap between sessions.  

_**NOTE: Depending on how you choose to complete this problem, your solution may give a different ordering or combination of talks into tracks. This is acceptable; you don’t need to exactly duplicate the sample output given here.**_

### Test input:  
```

Writing Fast Tests Against Enterprise Rails 60min  
Overdoing it in Python 45min  
Lua for the Masses 30min  
Ruby Errors from Mismatched Gem Versions 45min  
Common Ruby Errors 45min  
Rails for Python Developers lightning  
Communicating Over Distance 60min  
Accounting-Driven Development 45min  
Woah 30min  
Sit Down and Write 30min  
Pair Programming vs Noise 45min  
Rails Magic 60min  
Ruby on Rails: Why We Should Move On 60min  
Clojure Ate Scala (on my project) 45min  
Programming in the Boondocks of Seattle 30min  
Ruby vs. Clojure for Back-End Development 30min  
Ruby on Rails Legacy App Maintenance 60min  
A World Without HackerNews 30min  
User Interface CSS in Rails Apps 30min  
```

### Test output:  
```
Track 1:  

09:00AM Writing Fast Tests Against Enterprise Rails 60min  
10:00AM Overdoing it in Python 45min  
10:45AM Lua for the Masses 30min  
11:15AM Ruby Errors from Mismatched Gem Versions 45min  
12:00PM Lunch  
01:00PM Ruby on Rails: Why We Should Move On 60min  
02:00PM Common Ruby Errors 45min  
02:45PM Pair Programming vs Noise 45min  
03:30PM Programming in the Boondocks of Seattle 30min  
04:00PM Ruby vs. Clojure for Back-End Development 30min  
04:30PM User Interface CSS in Rails Apps 30min  
05:00PM Networking Event  

Track 2:  

09:00AM Communicating Over Distance 60min  
10:00AM Rails Magic 60min  
11:00AM Woah 30min  
11:30AM Sit Down and Write 30min  
12:00PM Lunch  
01:00PM Accounting-Driven Development 45min  
01:45PM Clojure Ate Scala (on my project) 45min  
02:30PM A World Without HackerNews 30min  
03:00PM Ruby on Rails Legacy App Maintenance 60min  
04:00PM Rails for Python Developers lightning  
05:00PM Networking Event  
```
====================================================

# Solution
===============

1) There are two sessions - 
	- Morning session - 180 mins(9:00am to 12:00pm), 
	- Lunch Break - 60 mins (12:00pm to 1:00pm) 
	- Afternoon Session - 240mins(1:00pm to 5:00pm) 
	- Network event starts at 5 O'clock

2) First reading from the file and creating a talk-list by parsing the title, duration etc
3) Arranging all the tasks in descending order based on their duration
4) Now, process the each Talk track by track. 
5) Incase track count is 2, then first processing for track-1 and allocating the 'n' no of Talks,
6) Then on next round processing rest talks (total talks - 'n' talks) for next track until all talks get allocated.
7) Now, pick up the Talk one by one and allocate till morning session is available, means unless it goes beyond 180 mins
8) Same processing doing for afternoon session also unless it goes beyond 240 mins.
9) At the End, it returns number of Talks that has been allocated for that particular Track
10) And then leftover Talks are processed for next track
11) During 12:00pm to 1:00pm marking as Lunch Break
12) At 5:00pm marking as Networking Event

    
#### Programming Language: Java 8.x
#### OS: macOS High Sierra - 10.13.6 
#### Source Code
#### ConferencePlanner.zip needs to be extracted under your any working directory
#### Create a Java Project in any IDE like eclipse or IntelliJ and import the ConferencePlanner folder
#### Main class : ConferencePlannerMain.java is the main class to run the Java application 
#### Package Structure:

            - ConferencePlanner
                README.md
                test-input.txt
                - src
                    - com
                        - conference
                            - management
                                ConferencePlannerMain.java
                                - entity
                                    Conference.java
                                    Talk.java
                                - util
                                    ConferenceConstants.java
                                    ConferenceUtil.java
                                    TalkTimeComparator.java
                - test
                    - com
                        - conference
                            - management
                                - entity
                                    ConferenceTest.java
                                    TalkTest.java
                                - util
                                    ConferenceUtilTest.java
    
   
   #### Input File: test-input.txt
   
   ### Output:
   ==========
   
   Test Input :
   
   Writing Fast Tests Against Enterprise Rails 60min
   Overdoing it in Python 45min
   Lua for the Masses 30min
   Ruby Errors from Mismatched Gem Versions 45min
   Common Ruby Errors 45min
   Rails for Python Developers lightning
   Communicating Over Distance 60min
   Accounting-Driven Development 45min
   Woah 30min
   Sit Down and Write 30min
   Pair Programming vs Noise 45min
   Rails Magic 60min
   Ruby on Rails: Why We Should Move On 60min
   Clojure Ate Scala (on my project) 45min
   Programming in the Boondocks of Seattle 30min
   Ruby vs. Clojure for Back-End Development 30min
   Ruby on Rails Legacy App Maintenance 60min
   A World Without HackerNews 30min
   User Interface CSS in Rails Apps 30min
   
   Test output:
   
   Track 1:
   
   09:00 AM Ruby on Rails Legacy App Maintenance 60min
   10:00 AM Ruby on Rails: Why We Should Move On 60min
   11:00 AM Rails Magic 60min
   12:00 PM Lunch
   13:00 PM Communicating Over Distance 60min
   14:00 PM Writing Fast Tests Against Enterprise Rails 60min
   15:00 PM Clojure Ate Scala (on my project) 45min
   15:45 PM Pair Programming vs Noise 45min
   5:00 PM Networking Event
   
   
   Track 2:
   
   09:00 AM Accounting-Driven Development 45min
   09:45 AM Common Ruby Errors 45min
   10:30 AM Ruby Errors from Mismatched Gem Versions 45min
   11:15 AM Overdoing it in Python 45min
   12:00 PM Lunch
   13:00 PM User Interface CSS in Rails Apps 30min
   13:30 PM A World Without HackerNews 30min
   14:00 PM Ruby vs. Clojure for Back-End Development 30min
   14:30 PM Programming in the Boondocks of Seattle 30min
   15:00 PM Sit Down and Write 30min
   15:30 PM Woah 30min
   16:00 PM Lua for the Masses 30min
   16:30 PM Rails for Python Developers 5min
   5:00 PM Networking Event



---------------------------------- 
