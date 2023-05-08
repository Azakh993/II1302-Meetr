package com.group7.meetr.data.model;

import java.util.ArrayList;

public class Consensus {

    private static ArrayList<String> participants;
    private static ArrayList<String> positiveParticipants;
    private static ArrayList<String> negativeParticipants;

    public static ArrayList<String> getParticipants() {
        return participants;
    }

    public static ArrayList<String> getPositiveParticipants() {
        return positiveParticipants;
    }
    public static ArrayList<String> getNegativeParticipants() {
        return negativeParticipants;
    }

    public static void setParticipants(ArrayList<String> participants) {
        Consensus.participants = participants;
    }
    public static void setPositiveParticipants(ArrayList<String> participants) {
        Consensus.positiveParticipants = positiveParticipants;
    }
    public static void setNegativeParticipants(ArrayList<String> participants) {
        Consensus.negativeParticipants = negativeParticipants;
    }

}