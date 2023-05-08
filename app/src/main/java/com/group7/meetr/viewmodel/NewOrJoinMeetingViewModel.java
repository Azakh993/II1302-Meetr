package com.group7.meetr.viewmodel;

import com.group7.meetr.data.model.Meeting;

public class NewOrJoinMeetingViewModel {
    private static Meeting currentMeeting;


    /**
     * Creates a Meeting instance.
     * @param currentMeetingID
     */
    public static void setCurrentMeetingID(String currentMeetingID) {
        currentMeeting = new Meeting(currentMeetingID);
    }

    public static Meeting getCurrentMeeting() {
        return currentMeeting;
    }
    public static String getCurrentMeetingID(){
        return currentMeeting.getMeetingID();
    }

    public static void setCurrentMeeting(Meeting currentMeeting) {
        NewOrJoinMeetingViewModel.currentMeeting = currentMeeting;
    }
}
