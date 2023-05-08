package com.group7.meetr.data.remote;

import static com.group7.meetr.data.remote.UtilFunctions.anyFunction;
import static com.group7.meetr.data.remote.UtilFunctions.fFunctions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.group7.meetr.viewmodel.LoginPageViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SessionHandler {

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

    /**
     / meetingExists
     * Checks if a meeting exists at the indicated meeting ID.
     * @return a boolean indicating existence of mID as a }
     */
    public static boolean callCheckIfMeetingExists(String mid){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        Map<String, Object> data = new HashMap<>();
        data.put("mID", mid);
        anyFunction("meetingExists", data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FFunctionsManager:meetingExists","Task not successful...");
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

                Log.d("FFunctionsManager:meetingExists","Task succeeded!");
                result.set((boolean)task.getResult().get("exists"));
            }
        });
        return result.get();
    }
}
