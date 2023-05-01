package com.group7.meetr.data.model;

import java.util.ArrayList;

public class Queue {
    int frontIndex;

    public int getFrontIndex() {
        return frontIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    int lastIndex;
    ArrayList<String> participants;
    public Queue(){
        frontIndex = 0;
        lastIndex = 0;
        participants = new ArrayList<>();
    }
}
