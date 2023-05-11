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
import com.group7.meetr.data.model.Consensus;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConsensusListViewModel {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
    private final MutableLiveData<Boolean> userConsensusAwaiting = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> consensusAgreedLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> consensusNotSureLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> consensusAwaitingLiveData = new MutableLiveData<>();


    public ConsensusListViewModel() {
        consensusObserver();
        consensusAgreedObserver();
        consensusNotSureObserver();
        consensusAwaitingObserver();
    }

    private void consensusAgreedObserver() {
        Consensus.getConsensusAgreedSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusAgreedLiveData);
    }

    private void consensusNotSureObserver() {
        Consensus.getConsensusNotSureSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusNotSureLiveData);
    }

    private void consensusAwaitingObserver() {
        Consensus.getConsensusAwaitingSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusAwaitingLiveData);
    }

    private void setConsensusAgreedLiveData(ArrayList<Object> consensusAgreed) {
        ArrayList<String> consensusAgreedList = parseToStringArray(consensusAgreed);
        consensusAgreedLiveData.setValue(consensusAgreedList);
    }

    private void setConsensusNotSureLiveData(ArrayList<Object> consensusNotSure) {
        ArrayList<String> consensusNotSureList = parseToStringArray(consensusNotSure);
        consensusNotSureLiveData.setValue(consensusNotSureList);
    }

    private void setConsensusAwaitingLiveData(ArrayList<Object> consensusAwaiting) {
        ArrayList<String> consensusAwaitingList = parseToStringArray(consensusAwaiting);
        consensusAwaitingLiveData.setValue(consensusAwaitingList);
    }

    public LiveData<ArrayList<String>> getConsensusAgreedLiveData() {
        return consensusAgreedLiveData;
    }

    public LiveData<ArrayList<String>> getConsensusNotSureLiveData() {
        return consensusNotSureLiveData;
    }

    public LiveData<ArrayList<String>> getConsensusAwaitingLiveData() {
        return consensusAwaitingLiveData;
    }

    private ArrayList<String> parseToStringArray(ArrayList<Object> consensusObjectArrayList){
        ArrayList<String> list = new ArrayList<>();
        for (Object user : consensusObjectArrayList) {
            list.add((String) user);
        }
        return list;
    }

    private void consensusObserver() {
        String meetingID = Meeting.getMeetingID();
        String userID = User.getUid();

        DatabaseReference consensusRef = database.getReference("Sessions")
                .child(meetingID).child("ListOfParticipants")
                .child(userID).child("consensus");

        ValueEventListener consensusListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null && dataSnapshot.getValue().equals("awaiting")) {
                    userConsensusAwaiting.setValue(true);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Log.d("consensusObserver", databaseError.getDetails());
            }
        };
        consensusRef.addValueEventListener(consensusListener);
    }

    public LiveData<Boolean> getUserConsensusAwaiting() {
        return userConsensusAwaiting;
    }
}
