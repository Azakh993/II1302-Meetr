package com.group7.meetr.data.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionHandler {
    private static DatabaseReference mDatabase;

    /**
     * Class constructor. Initializes database reference.
     */
    public SessionHandler(FirebaseDatabase database) {
        mDatabase = database.getReference("/Sessions/");
    }

    public SessionHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference("/Sessions/");
    }

    /**
     * Joins a hardcoded meeting session and adds the signed in user's email address
     */
    public void joinSession(String email) {
        String sessionID = "7";
        mDatabase.child(sessionID).child("Participants").push().setValue(email);
    }

    /**
     * Adds a session identification number in database as a child to "Sessions";
     * represents the existence of a meeting session, as well as the current moderator
     * under the created session.
     */
    public void createSession(String userMail) {
        String sessionID = "7";
        mDatabase.child(sessionID).child("Moderator").setValue(userMail);
    }

    public void sendProximityData(String sessionId, boolean handRaised, String timestamp) {
        DatabaseReference proximityDataRef = mDatabase.child(sessionId).child("QueueRequestData").push();
        proximityDataRef.child("handRaised").setValue(handRaised);
        proximityDataRef.child("timestamp").setValue(timestamp);
    }
}
