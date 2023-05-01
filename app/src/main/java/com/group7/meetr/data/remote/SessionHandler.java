package com.group7.meetr.data.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.activity.EmailPasswordActivity;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.Participant;

public class SessionHandler {
    private static DatabaseReference mDatabase;

    /**
     * Class constructor. Initializes database reference.
     */
    public SessionHandler(FirebaseDatabase database) {
        mDatabase = database.getReference("/SessionTestBETA/");
    }

    /**
     * Joins a hardcoded meeting session and adds the signed in user's email address
     */
    public void joinSession(String meetingID) {
        //Meeting meeting = new Meeting(meetingID);
        //mDatabase.child(meeting.getMeetingID()).child("participants").push().setValue(new Participant(EmailPasswordActivity.getmAuth().getUid()));
    }

    /**
     * Adds a session identification number in database as a child to "Sessions";
     * represents the existence of a meeting session, as well as the current moderator
     * under the created session.
     */
    public void dbCreateSession(String userMail) {
        Meeting meeting = new Meeting();
        //mDatabase.child("meet-" + meeting.getMeetingID()).setValue(meeting);
    }
}
