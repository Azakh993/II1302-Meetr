package com.group7.meetr.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QueueListViewModel {
    private final FirebaseDatabase database;
    private final MutableLiveData<ArrayList<Object>> functionsOutput;
    private final String MEETINGID = "7";


    public QueueListViewModel() {
        this.database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
        functionsOutput = new MutableLiveData<>();
    }

    public void indexObserver() {
        DatabaseReference queueRef = database.getReference("Sessions")
                .child(MEETINGID).child("Queue");

        DatabaseReference firstIndexRef = queueRef.child("firstIndex");
        DatabaseReference lastIndexRef = queueRef.child("lastIndex");

        ValueEventListener indexListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                functionsOutput.setValue(FirebaseFunctionsManager.callGetSpeakingQueue(MEETINGID));
                Log.d("Data Changed", (String) "Haha");
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle error
            }
        };
        firstIndexRef.addValueEventListener(indexListener);
        lastIndexRef.addValueEventListener(indexListener);
    }

}
