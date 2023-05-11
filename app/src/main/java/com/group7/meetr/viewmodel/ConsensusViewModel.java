package com.group7.meetr.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.QueueHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConsensusViewModel {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");

    private final MutableLiveData<List<String>> consenusLiveData = new MutableLiveData<>();
    private ArrayList<String> yesList = new ArrayList<>();
    private ArrayList<String> noList = new ArrayList<>();


    public ConsensusViewModel(ArrayList<String> yesList, ArrayList<String> noList) {
        this.yesList = yesList;
        this.noList = noList;
    }


    static void indexObserver() {
        String mid = Meeting.getMeetingID();

        DatabaseReference queueRef = database.getReference("Sessions")
                .child(mid).child("ListOfParticipants").child("consensus");

        DatabaseReference frontIndexRef = queueRef.child("positive");
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

}
