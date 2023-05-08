package com.group7.meetr.viewmodel;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.data.remote.QueueHandler;

import org.jetbrains.annotations.NotNull;

public class ViewModelUtils {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
    private static final String MEETING_ID = "7";

    static void indexObserver() {
        DatabaseReference queueRef = database.getReference("Sessions")
                .child(MEETING_ID).child("Queue");

        DatabaseReference frontIndexRef = queueRef.child("frontIndex");
        DatabaseReference lastIndexRef = queueRef.child("lastIndex");

        ValueEventListener indexListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QueueHandler.callGetSpeakingQueue(MEETING_ID);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle error
            }
        };
        frontIndexRef.addValueEventListener(indexListener);
        lastIndexRef.addValueEventListener(indexListener);
    }
}
