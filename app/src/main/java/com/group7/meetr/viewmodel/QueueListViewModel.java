package com.group7.meetr.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QueueListViewModel {
    private final FirebaseDatabase database;
    private static MutableLiveData<List<String>> queueLiveData = new MutableLiveData<>();
    private static ArrayList<Object> queue = new ArrayList<>();
    private static List<String> queueList;
    private final String MEETINGID = "7";



    public QueueListViewModel() {
        this.database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public void indexObserver() {
        DatabaseReference queueRef = database.getReference("Sessions")
                .child(MEETINGID).child("Queue");

        DatabaseReference frontIndexRef = queueRef.child("frontIndex");
        DatabaseReference lastIndexRef = queueRef.child("lastIndex");

        ValueEventListener indexListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseFunctionsManager.callGetSpeakingQueue(MEETINGID);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle error
            }
        };
        frontIndexRef.addValueEventListener(indexListener);
        lastIndexRef.addValueEventListener(indexListener);
    }

    public static void setQueue(ArrayList<Object> queue) {
        QueueListViewModel.queue = queue;
        QueueListViewModel.setQueueList();
        QueueListViewModel.queueLiveData.setValue(QueueListViewModel.queueList);
    }

    public static LiveData<List<String>> getQueueLiveData() {
        return QueueListViewModel.queueLiveData;
    }

    private static void setQueueList() {
        for(Object item : queue) {
            ArrayList<String> arrayListItem = (ArrayList<String>) item;
            queue.add(arrayListItem.get(1));
        }
    }
}