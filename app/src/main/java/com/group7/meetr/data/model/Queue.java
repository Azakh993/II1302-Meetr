package com.group7.meetr.data.model;

import android.util.Log;

import com.group7.meetr.data.remote.FirebaseFunctionsManager;

import java.util.ArrayList;

public class Queue {
    int frontIndex;
    int lastIndex;
    ArrayList<String> participants;

    public int getFrontIndex() {
        return frontIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }


    public Queue(){
        frontIndex = 0;
        lastIndex = 3;
        participants.add("Name1");
        participants.add("Name2");
        participants.add("Name3");
        participants.add("Name4");
    }

    public static void setParameters() {
        ArrayList<Object> functionsOutput = FirebaseFunctionsManager.callGetSpeakingQueue("7");
        Log.d("Cloud Function:", functionsOutput.toString());
    }
}
