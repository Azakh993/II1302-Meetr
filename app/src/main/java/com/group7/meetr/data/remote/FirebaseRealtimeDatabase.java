package com.group7.meetr.data.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.activity.Participant;
import com.group7.meetr.activity.ParticipantAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * FirebaseRealtimeDatabase class provides methods to interact with Firebase Realtime Database
 */
public class FirebaseRealtimeDatabase {
    private  final FirebaseDatabase database;
    private  final String meetingID;
    ParticipantAdapter adapter;


    /**
     * FirebaseRealtimeDatabase class provides methods to interact with Firebase Realtime Database
     * @param database FirebaseDatabase instance to interact with Firebase Realtime Database
     * @param meetingID Meeting ID of the session
     */

    public FirebaseRealtimeDatabase(FirebaseDatabase database, String meetingID) {
        this.database = database;
        this.meetingID = meetingID;
        //this.adapter = new ParticipantAdapter();
    }


    /**
     * Adds a ValueEventListener to the Participants node of the session in Firebase Realtime Database
     */
    public void addParticipantsListener() {
        DatabaseReference participantsRef = database.getReference("Sessions").child(meetingID).child("Participants");

        ValueEventListener participantsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Participant> emailList = new ArrayList<>();
                for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                    Participant email = emailSnapshot.getValue(Participant.class);
                    emailList.add(email);
                }
                //Participant[] emails = emailList.toArray(new String[0]);
                // TODO: add a setter here to that updates the UI with the array of participants.
                //adapter.setUsers(emails);
                //activity.setUsers(emails);

            }


            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle error
            }
        };
        participantsRef.addValueEventListener(participantsListener);
    }



}
