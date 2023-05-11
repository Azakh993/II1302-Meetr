package com.group7.meetr.data.remote;

import static com.group7.meetr.data.remote.UtilFunctions.anyFunction;
import static com.group7.meetr.data.remote.UtilFunctions.fFunctions;

import android.util.Log;

import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.group7.meetr.data.model.Meeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ConsensusHandler {
    private static final PublishSubject<ArrayList<Object>> consensusAgreedSubject = PublishSubject.create();
    private static final PublishSubject<ArrayList<Object>> consensusNotSureSubject = PublishSubject.create();

    private static final ArrayList<Object> notSure = new ArrayList<>();
    private static final ArrayList<Object> agreed = new ArrayList<>();


    /**
     * Observe this is if you need the people who have agreed in the current consensusState.
     * @return returns an array of objects
     */
    public static Observable<ArrayList<Object>> getConsensusAgreedSubject() {
        return consensusAgreedSubject;
    }
    /**
     * Observe this is if you need the people who have not agreed in the current consensusState.
     * @return returns an array of objects
     */
    public static Observable<ArrayList<Object>> getConsensusNotSureSubject() {
        return consensusNotSureSubject;
    }

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

                if(outerHashMap.get("queue") != null) {
                    agreed.clear();
                    agreed.addAll((ArrayList<Object>) outerHashMap.get("concede"));
                    consensusAgreedSubject.onNext(agreed);

                    notSure.clear();
                    notSure.addAll((ArrayList<Object>) outerHashMap.get("concerned"));
                    consensusNotSureSubject.onNext(notSure);
                }
            }
        });

    }

}
