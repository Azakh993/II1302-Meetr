package com.group7.meetr.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.group7.meetr.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilFunctions {
    static FirebaseFunctions fFunctions;

    /**
     * Private function that runs any function on Firebase side.
     * Use this to make a task that runs your functions defined in firebase.
     * @param functionName name of function. capitalized sensitive
     * @param data Data to send. JSON Map format. STRING -> Object. Object any java default object.
     * @return returns task. run when complete on it to get actual data.
     */
    static Task<Map<String, Object>> anyFunction(String functionName, Map<String, Object> data){
        data.put("push", true);
        data.put("uid", User.getUid());
        return fFunctions
                .getHttpsCallable(functionName)
                .call(data)
                .continueWith(task -> {
                    Object result = task.getResult().getData();
                    if(result == null) return null;
                    if(result.getClass() == ArrayList.class)
                        return (Map<String, Object>) ((ArrayList<Object>) task.getResult().getData()).get(0);
                    else if (result.getClass() == Boolean.class) { //TODO: change this function to always return exists:boolean so I Don't have to do this....
                        Map<String, Object> s = new HashMap<>();
                        s.put("exists", result);
                        return s;
                    } else
                        return (Map<String, Object>)task.getResult().getData();

                });
    }
    /**
     * Use this to execute any function defined in firebase.
     * @param functionName name of function. capitalized sensitive
     * @param data Data to send. JSON Map format. STRING -> Object. Object any java default object.
     * @return returns task. run when complete on it to get actual data.
     * Map<String, Object> map.put().
     * callanyfunction("test", map);
     */
    public static Map<String, Object> callAnyFunction(String functionName, Map<String, Object> data){
        if(fFunctions == null)
            fFunctions = FirebaseFunctions.getInstance("europe-west1");

        Map<String, Object> result = new HashMap<>();
        anyFunction(functionName, data).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                }
            }
            else
            { if(task.getResult() != null) result.putAll(task.getResult()); }
        });
        return result;
    }

    public static ArrayList<String> parseQueueArrayList(ArrayList<Object> queue) {
        ArrayList<String> queueList = new ArrayList<>();
        HashMap<String, String> queueHashMap;
        for(Object item : queue) {
            queueHashMap = (HashMap<String, String>) item;
            if(queueHashMap == null) return new ArrayList<>();
            String arrayListItem = queueHashMap.get("name");
            queueList.add(arrayListItem);
        }
        return queueList;
    }

}
