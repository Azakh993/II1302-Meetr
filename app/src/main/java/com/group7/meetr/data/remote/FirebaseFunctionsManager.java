package com.group7.meetr.data.remote;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.group7.meetr.viewmodel.InMeetingViewModel;
import com.group7.meetr.viewmodel.LoginPageViewModel;
import com.group7.meetr.viewmodel.QueueListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseFunctionsManager {
    private static Map<String, Object> callGetSpeakingQueueResult = new HashMap<>();
    static FirebaseFunctions fFunctions;
    /**
     * Private helper function to be run and awaited results.
     * @param input object to input
     * @return returns the task that hasnt finished yet.
     */
    private static Task<Map<String, Object>> newMeeting(Map<String, Object> input) {
        // Create the arguments to the callable function.

        input.put("push", true);
        input.put("uid", LoginPageViewModel.getCurrentUser().getUid());

        return fFunctions
                .getHttpsCallable("newMeeting")
                .call(input)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Log.d("FFunctionsManager:newMeeting","Task successful?...");

                        Map<String, Object> result = (Map<String, Object>)  task.getResult().getData();
                        return (Map<String, Object>) result;
                    }
                });
    }
    /**
     * Call a new meeting using the FirebaseFunctions.
     * @param moderatorName whats the name of the moderator.
     * @param meetingID the meetingID to use to create.
     * @return the meeting id as an ínteger. Should defintely be string...
     */
    public static String callNewMeeting(String moderatorName, String meetingID) {
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");

        //{moderator: moderatorUserName<String>, meetingID: meetingID<String>}
        Map<String, Object> sendInfo = new HashMap<>();
        final String[] result = {null};
        sendInfo.put("moderator", moderatorName);
        sendInfo.put("mID", meetingID);
        newMeeting(sendInfo).addOnCompleteListener(new OnCompleteListener<Map<String, Object>>() {
            @Override
            public void onComplete(@NonNull Task<Map<String, Object>> task) {
                if (!task.isSuccessful()) {
                    Exception e = task.getException();
                    if (e instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                        FirebaseFunctionsException.Code code = ffe.getCode();
                        Object details = ffe.getDetails();
                    }
                }
                else
                {
                    result[0] = String.valueOf(task.getResult().get("sid"));
                    Log.d("fornite",(task.getResult().get("meetingID").toString()));
                }

            }
        });
        return result[0];
    }

    /**
     * Private function that runs any function on Firebase side.
     * Use this to make a task that runs your functions defined in firebase.
     * @param functionName name of function. capitalized sensitive
     * @param data Data to send. JSON Map format. STRING -> Object. Object any java default object.
     * @return returns task. run whencomplete on it to get actual data.
     */
    private static Task<Map<String, Object>> anyFunction(String functionName, Map<String, Object> data){
        data.put("push", true);
        data.put("uid", LoginPageViewModel.getCurrentUser().getUid());
        return fFunctions
                .getHttpsCallable(functionName)
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Object result = task.getResult().getData();
                        if(result == null) return null;
                        if(result.getClass() == ArrayList.class)
                        return (Map<String, Object>) ((ArrayList<Object>) task.getResult().getData()).get(0);
                        else
                            return (Map<String, Object>)task.getResult().getData();

                    }
                });
    }
    /**
     * Use this to execute any function defined in firebase.
     * @param functionName name of function. capitalized sensitive
     * @param data Data to send. JSON Map format. STRING -> Object. Object any java default object.
     * @return returns task. run whencomplete on it to get actual data.
     * Map<String, Object> map.put(blahblabhbl).
     * callanyfunction("test", map);
     */
    public static Map<String, Object> callAnyFunction(String functionName, Map<String, Object> data){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> result = new HashMap<>();
        anyFunction(functionName, data).addOnCompleteListener(new OnCompleteListener<Map<String, Object>>() {
            @Override
            public void onComplete(@NonNull Task<Map<String, Object>> task) {
                if (!task.isSuccessful()) {
                    Exception e = task.getException();
                    if (e instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                        FirebaseFunctionsException.Code code = ffe.getCode();
                        Object details = ffe.getDetails();
                    }
                }
                else
                { result.putAll(task.getResult()); }
            }
        });
        return result;
    }
    /**
     * Calls enqueue function on Firebase side. This will enqueue a user using their information.
     * Uses the generic task.
     * @param meetingID the meeting ID to enqueue in.
     * @param participantName the name to use. usually email atm?
     */
    public static void callEnqueue(String meetingID, String participantName, long timestamp){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        data.put("participant", participantName);
        data.put("timestamp", timestamp); // NOT USED BY SERVER ATM

        anyFunction("enqueue", data).addOnCompleteListener(new OnCompleteListener<Map<String, Object>>() {
            @Override
            public void onComplete(@NonNull Task<Map<String, Object>> task) {
                if (!task.isSuccessful()) {
                    Log.d("FFunctionsManager:ENQUEUE","Task not successful...");
                    task.getException().printStackTrace();



                    Exception e = task.getException();
                    if (e instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                        FirebaseFunctionsException.Code code = ffe.getCode();
                        Object details = ffe.getDetails();
                    }
                }
                else
                {
                    Log.d("FFunctionsManager:ENQUEUE","Task succeeded!");

                }
            }
        });
    }

    /**
     * Ask server for the getspeakingqueue.
     * @param meetingID meeting you are asking about :)
     * @return returns an arraylist of people
     */
    public static ArrayList<Object> callGetSpeakingQueue(String meetingID){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);



        anyFunction("getSpeakingQueue", data).addOnCompleteListener(new OnCompleteListener<Map<String, Object>>() {
            @Override
            public void onComplete(@NonNull Task<Map<String, Object>> task) {
                if (!task.isSuccessful()) {
                    Log.d("FFunctionsManager:GETQUEUE","Task not successful...");
                    task.getException().printStackTrace();

                    Exception e = task.getException();
                    if (e instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                        FirebaseFunctionsException.Code code = ffe.getCode();
                        Object details = ffe.getDetails();
                    }
                }
                else
                {
                    Log.d("FFunctionsManager:getQueue","Task succeeded!");
                    Map<String, Object> outerHashMap = task.getResult();
                    callGetSpeakingQueueResult.put("queue", outerHashMap.get("queue"));
                    if(callGetSpeakingQueueResult.get("queue") != null) {
                        InMeetingViewModel.setLiveData((ArrayList<Object>) callGetSpeakingQueueResult.get("queue"));
                        QueueListViewModel.setQueue((ArrayList<Object>) callGetSpeakingQueueResult.get("queue"));
                    }
                }
            }
        });
        return (ArrayList<Object>) callGetSpeakingQueueResult.get("queue");
    }

    /**
     * Calls finished talking. Returns nothing and requires nothing as UID is gotten
     * from LoginPageViewModel
     */
    public static void callFinishedTalking(String meetingID){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        anyFunction("finishedTalking", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:finishedTalking","Task not successful...");
                task.getException().printStackTrace();


                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            }
            else
            {
                Log.d("FFunctionsManager:FinishedTalking","Task succeeded!");
            }
        });
    }

    /** Joins a meeting using backend.
     * @param meetingID meetingid to join
     */
    public static void callJoinMeeting(String meetingID, String name){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        Map<String, Object> data = new HashMap<>();
        data.put("mID", meetingID);
        data.put("name", name);
        anyFunction("joinMeeting", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:finishedTalking","Task not successful...");
                task.getException().printStackTrace();


                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            }
            else
            {
                Log.d("FFunctionsManager:FinishedTalking","Task succeeded!");
            }
        });
    }

}
