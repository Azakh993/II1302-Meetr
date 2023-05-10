package com.group7.meetr.viewmodel;

import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.QueueHandler;

public class TalkingViewModel {
    private final String meetingID = Meeting.getMeetingID();

    public void finishedTalking() {
        QueueHandler.callFinishedTalking(meetingID);
    }
}
