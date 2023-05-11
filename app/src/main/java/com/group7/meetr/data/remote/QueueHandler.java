package com.group7.meetr.data.remote;

import static com.group7.meetr.data.remote.UtilFunctions.anyFunction;
import static com.group7.meetr.data.remote.UtilFunctions.fFunctions;

import android.util.Log;

import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class QueueHandler {
    private static final PublishSubject<ArrayList<Object>> callGetSpeakingQueueResultSubject = PublishSubject.create();
    private static final ArrayList<Object> speakingQueue = new ArrayList<>();

    /**
     * Calls enqueue function on Firebase side. This will enqueue a user using their information.
     * Uses the generic task.
     *
     * @param meetingID       the meeting ID to enqueue in.
     * @param participantName the name to use. usually email atm?
     */
    public static void callEnqueue(String meetingID, String participantName, long timestamp) {
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");

        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        data.put("participant", participantName);
        data.put("timestamp", timestamp); // NOT USED BY SERVER ATM

        anyFunction("enqueue", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:enqueue", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:enqueue", "Task succeeded!");

            }
        });
    }

    public static void callInclusiveEnqueue(String meetingID, String participantName, long timestamp) {
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");

        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        data.put("participant", participantName);
        data.put("timestamp", timestamp); // NOT USED BY SERVER ATM

        anyFunction("inclusiveQueue", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:enqueue", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:enqueue", "Task succeeded!");

            }
        });
    }

    public static void callReplyEnqueue(String meetingID, String participantName, long timestamp) {
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");

        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        data.put("participant", participantName);
        data.put("timestamp", timestamp); // NOT USED BY SERVER ATM

        anyFunction("replyToSpeaker", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:enqueue", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:enqueue", "Task succeeded!");

            }
        });
    }

    /**
     * Ask server for the getspeakingqueue.
     *
     * @param meetingID meeting you are asking about :)
     * @return returns an arraylist of people
     */
    public static void callGetSpeakingQueue(String meetingID) {
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);

        anyFunction("getSpeakingQueue", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:getQueue","Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:getQueue","Task succeeded!");
                Map<String, Object> outerHashMap = task.getResult();

                if(outerHashMap.get("queue") != null) {
                    speakingQueue.clear();
                    speakingQueue.addAll((ArrayList<Object>) outerHashMap.get("queue"));
                    Meeting.setQueue(speakingQueue);
                    callGetSpeakingQueueResultSubject.onNext(speakingQueue);
                }
            }
        });
    }

    /**
     * Calls finished talking. Returns nothing and requires nothing as UID is gotten
     * from LoginPageViewModel
     */
    public static void callFinishedTalking(String meetingID) {
        if (fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);

        anyFunction("finishedTalking", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:finishedTalking", "Task not successful...");
                task.getException().printStackTrace();

                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            } else {
                Log.d("FFunctionsManager:FinishedTalking", "Task succeeded!");
            }
        });
    }

    public static Observable<ArrayList<Object>> observeQueue() {
        return callGetSpeakingQueueResultSubject;
    }

    public static void sendProximityData(String sessionId, long timestamp) {
        callEnqueue(sessionId, User.getEmail(), timestamp);
    }
}
