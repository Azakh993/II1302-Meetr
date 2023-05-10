package com.group7.meetr.data.model;

import com.group7.meetr.data.remote.UtilFunctions;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class Meeting {
    private static final PublishSubject<Long> meetingEndedTime = PublishSubject.create();
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

    public static void setQueue(ArrayList<Object> unparsedQueueArrayList) {
        queue = UtilFunctions.parseQueueArrayList(unparsedQueueArrayList);
    }

    public static void setMeetingEndedTime(Long meetingEnded) {
        meetingEndedTime.onNext(meetingEnded);
    }

    public static Observable<Long> observeMeetingEnded() {
        return meetingEndedTime;
    }
}
