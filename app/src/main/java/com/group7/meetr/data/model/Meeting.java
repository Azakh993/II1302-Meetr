package com.group7.meetr.data.model;

import com.group7.meetr.data.remote.UtilFunctions;

import java.util.ArrayList;

public class Meeting {
    private static ArrayList<String> queue;
    private static String meetingID;

    public static String getMeetingID() {
        return meetingID;
    }

    public static void setMeetingID(String meetingID) {
        Meeting.meetingID = meetingID;
    }

    public ArrayList<String> getQueue() {
        return queue;
    }

    /**
     * End meeting and saves every person to db.
     */
    public void endMeeting(){

    }

    public static void setQueue(ArrayList<Object> unparsedQueueArrayList) {
        queue = UtilFunctions.parseQueueArrayList(unparsedQueueArrayList);
    }
}
