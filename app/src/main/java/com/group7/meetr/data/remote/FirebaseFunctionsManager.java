package com.group7.meetr.data.remote;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
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
            createQueue();
        String auth = EmailPasswordActivity.getmAuth().getUid();
        Map<String, Object> data = new HashMap<>();
        data.put("text", auth);
        data.put("push", true);
        fFunctions.getHttpsCallable("enqueue").call(data);

        }
        public static void createQueue(){
            if(fFunctions == null){
                fFunctions = FirebaseFunctions.getInstance("us-central1");
                String auth = EmailPasswordActivity.getmAuth().getUid();
                Map<String, Object> data = new HashMap<>();
                data.put("push", true);
                fFunctions.getHttpsCallable("initQ").call(data);
            }

        }
}
