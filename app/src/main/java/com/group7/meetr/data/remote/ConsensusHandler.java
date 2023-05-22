package com.group7.meetr.data.remote;

import static com.group7.meetr.data.model.Consensus.setConsensusAgreedSubject;
import static com.group7.meetr.data.model.Consensus.setConsensusAwaitingSubject;
import static com.group7.meetr.data.model.Consensus.setConsensusNotSureSubject;
import static com.group7.meetr.data.model.Consensus.setUserConsensusAwaiting;
import static com.group7.meetr.data.remote.UtilFunctions.anyFunction;
import static com.group7.meetr.data.remote.UtilFunctions.fFunctions;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConsensusHandler {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");

    /**
     * Starts consensus on current meeting.
     */
    public static void callStartConsensus(String meetingID){
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        anyFunction("callForConsensus", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:callStartConsensus", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:callStartConsensus", "Task succeeded!");
            }
        });

    }

    /**
     *
     * @param agrees
     * @param meetingID
     */
    public static void callSetConsensusStance(boolean agrees, String meetingID){
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        data.put("concedes", agrees);
        anyFunction("setConsensusStance", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:setConsensusStance", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:setConsensusStance", "Task succeeded!");
            }
        });

    }
    public static void callEndConsensus(String meetingID){
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        anyFunction("endConsensusMode", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:callEndConsensus", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:callEndConsensus", "Task succeeded!");
            }
        });

    }
    public static void callGetConsensusStances(String meetingID){
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        anyFunction("getConsensusStances", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:callGetConsensusStances", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:callGetConsensusStances", "Task succeeded!");
                Map<String, Object> outerHashMap = task.getResult();

                if(outerHashMap.get("concede") != null) {
                    setConsensusAgreedSubject((ArrayList<Object>) outerHashMap.get("concede"));
                    setConsensusNotSureSubject((ArrayList<Object>) outerHashMap.get("concerned"));
                    setConsensusAwaitingSubject((ArrayList<Object>) outerHashMap.get("awaiting"));
                }
            }
        });

    }

    public static void consensusObserver() {
        String meetingID = Meeting.getMeetingID();
        String userID = User.getUid();

        DatabaseReference consensusRef = database.getReference("Sessions")
                .child(meetingID).child("ListOfParticipants")
                .child(userID).child("consensus");

        ValueEventListener consensusListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null && dataSnapshot.getValue().equals("awaiting")) {
                    setUserConsensusAwaiting(true);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Log.d("consensusObserver", databaseError.getDetails());
            }
        };
        consensusRef.addValueEventListener(consensusListener);
    }
}
