package com.group7.meetr.data.remote;

import com.google.firebase.database.FirebaseDatabase;

/**
 * FirebaseRealtimeDatabase class provides methods to interact with Firebase Realtime Database
 */
public class DatabaseHandler {
    private final FirebaseDatabase database;
    private final String meetingID;

    /**
     * FirebaseRealtimeDatabase class provides methods to interact with Firebase Realtime Database
     * @param meetingID Meeting ID of the session
     */

    public DatabaseHandler(String meetingID) {
        this.database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");;
        this.meetingID = meetingID;
    }

    /**
     * Adds a ValueEventListener to the Participants node of the session in Firebase Realtime Database
     */
    /*public void addParticipantsListener() {
        DatabaseReference participantsRef = database.getReference("Sessions").child(meetingID).child("Participants");

        ValueEventListener participantsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> emailList = new ArrayList<>();
                for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                    String email = emailSnapshot.getValue(String.class);
                    emailList.add(email);
                }
                String[] emails = emailList.toArray(new String[0]);
                // TODO: add a setter here to that updates the UI with the array of participants.
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle error
            }
        };
        participantsRef.addValueEventListener(participantsListener);
    }*/
}
