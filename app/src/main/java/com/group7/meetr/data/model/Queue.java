package com.group7.meetr.data.model;

import java.util.ArrayList;

public class Queue {

    private static ArrayList<String> participants;

    public static ArrayList<String> getParticipants() {
        return participants;
    }

    public static void setParticipants(ArrayList<String> participants) {
        Queue.participants = participants;
    }
}
