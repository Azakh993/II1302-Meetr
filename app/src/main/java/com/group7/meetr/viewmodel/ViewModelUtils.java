package com.group7.meetr.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.QueueHandler;

import org.jetbrains.annotations.NotNull;

public class ViewModelUtils {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");

    static void indexObserver() {
        String mid = Meeting.getMeetingID();

        DatabaseReference queueRef = database.getReference("Sessions")
                .child(mid).child("Queue");

        DatabaseReference frontIndexRef = queueRef.child("frontIndex");
        DatabaseReference lastIndexRef = queueRef.child("lastIndex");

        ValueEventListener indexListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QueueHandler.callGetSpeakingQueue(Meeting.getMeetingID());
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Log.d("indexObserver", databaseError.getDetails());
            }
        };
        frontIndexRef.addValueEventListener(indexListener);
        lastIndexRef.addValueEventListener(indexListener);
    }

    static void endTimeObserver() {
        String meetingID = Meeting.getMeetingID();

        DatabaseReference endTime = database.getReference("Sessions")
                .child(meetingID).child("endTime");

        ValueEventListener endTimeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Meeting.setMeetingEndedTime((Long) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Log.d("endTimeObserver", databaseError.getDetails());
            }
        };
        endTime.addValueEventListener(endTimeListener);
    }
}
