package com.group7.meetr.data.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Meeting {
    List<Participant> participants;// meetings/MID/participants/UID and boolean for if mod??
    //2
    //3
    //(each queueposition) UID + timestamp + reason?
    Queue queue;

    String meetingID;
    //TIME startTime;
    Timestamp startTime;
    //TIME endTime; //endtime == null mötet aktivt


    public List<Participant> getParticipants() {
        return participants;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public Queue getQueue() {
        return queue;
    }

    /**
     * Create a copy of a current meeting session by basing it of data in Firebase;
     * Use firebaserealtimedatabase please...
     * Use cloud functions to instanciate Queue.
     * @param meetingID the meeting id that is being recreated locally.
     */
    public Meeting(String meetingID){
        this.meetingID = meetingID;
    }

    /**
     * End meeting and saves every person to db.
     */
    public void endMeeting(){

    }
}
