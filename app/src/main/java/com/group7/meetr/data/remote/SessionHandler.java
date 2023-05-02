package com.group7.meetr.data.remote;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionHandler {
    private static DatabaseReference mDatabase;

    /**
     * Class constructor. Initializes database reference.
     */

    public SessionHandler() {
        mDatabase = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/").getReference("/Sessions/");
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
        String s = FirebaseFunctionsManager.callNewMeeting(userMail,"7");
        if(s != null)
            Log.d("Created session", s);
    }

    public void sendProximityData(String sessionId, long timestamp) {
        mDatabase.child(sessionId).child("/QueueRequestData").push().setValue(timestamp);
    }
}
