package com.group7.meetr.data.remote;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.group7.meetr.activity.EmailPasswordActivity;

import java.util.HashMap;
import java.util.Map;

public class FirebaseFunctionsManager {
    static FirebaseFunctions fFunctions;

    /**
     * Puts the current authed user from getmAuth into queue on firebase using firebase
     * functions joinQ function.
     */
    public static void putInQueueOnFirebase(){
        if(fFunctions == null)
            initQ();
        String auth = EmailPasswordActivity.getmAuth().getUid();
        Map<String, Object> data = new HashMap<>();// meeting.mid
        //meeting.startTime
        data.put("text", auth);
        data.put("push", true);
        fFunctions.getHttpsCallable("enqueue").call(data);

        }
        public static Task<Object> initQ(){
                fFunctions = FirebaseFunctions.getInstance("europe-west1");
                Map<String, Object> data = new HashMap<>();
                data.put("push", true);
                return fFunctions.getHttpsCallable("initQ").call(data)
                        .continueWith(new Continuation<HttpsCallableResult, Object>() {
                            @Override
                            public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                                // This continuation runs on either success or failure, but if the task
                                // has failed then getResult() will throw an Exception which will be
                                // propagated down.
                                String result = (String) task.getResult().getData();
                                Log.d("!Ffunction result", result);
                                return result;
                            }
                        });
        }
        public static void callInitQ(){
            initQ().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    task.getResult();
                    Log.d("Ffunction", "Gettem result");

                }
            });
        }


    /**
     * Private helper function to be run and awaited results.
     * @param input object to input
     * @return returns the task that hasnt finished yet.
     */
    private static Task<Map<String, Object>> newMeeting(Map<String, Object> input) {
        // Create the arguments to the callable function.

        input.put("push", true);

        return fFunctions
                .getHttpsCallable("newMeeting")
                .call(input)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Map<String, Object> result = (Map<String, Object>)  task.getResult().getData();
                        return (Map<String, Object>) result;
                    }
                });
    }

    /**
     * Call a new meeting using the FirebaseFunctions.
     * @param moderatorName whats the name of the moderator.
     * @param meetingID
     * @return the meeting id as an ínteger. Should defintely be string...
     */
    public static String callNewMeeting(String moderatorName, String meetingID) {
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");

        //{moderator: moderatorUserName<String>, meetingID: meetingID<String>}
        Map<String, Object> sendInfo = new HashMap<>();
        final String[] result = {null};
        sendInfo.put("moderator", moderatorName);
        sendInfo.put("meetingID", meetingID);
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
                    Log.d("fornite",(task.getResult().get("sid").toString()));
                }

            }
        });
        return result[0];
    }
    private static Task<Map<String, Object>> anyFunction(String functionName, Map<String, Object> data){
        return fFunctions
                .getHttpsCallable(functionName)
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        return (Map<String, Object>) task.getResult().getData();
                    }
                });
    }
    /**
     *
     * @param functionName the function name found in Firebase.
     * @param data data blob that needs to be pushed. do not include push true, already doing that.
     */
    public static Map<String, Object> callAnyFunction(String functionName, Map<String, Object> data){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        final Object[] result = {null};
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
                {
                    result[0] =  task.getResult();
                }
            }
        });
        return (Map<String, Object>) result[0];
    }
}
