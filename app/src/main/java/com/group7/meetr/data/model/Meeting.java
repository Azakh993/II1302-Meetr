package com.group7.meetr.data.model;

import com.group7.meetr.activity.EmailPasswordActivity;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Meeting {
    List<Participant> participants;// meetings/MID/participants/UID and boolean for if mod??
    //2
    //3
    List<String> queue;
    //(each queueposition) UID + timestamp + reason?

    String meetingID;
    int frontIndex;
    int lastIndex;
    //TIME startTime;
    //TIME endTime; //endtime == null mötet aktivt

    /**
     * Adds a user using their userID. Usually used when we get a single update from database.
     * @param userID who to add. Found with EmailPasswordActivity.getAuth
     */
    public void addParticipant(String userID){
        //TODO: Get previous meeting participation score from here!
        this.participants.add(new Participant("John Doe", true));
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public List<String> getQueue() {
        return queue;
    }

    public int getFrontIndex() {
        return frontIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    /**
     * Create a new meeting with random UID. Then create that respectively online.
     */
    public Meeting(){
        this.meetingID = generateMID();
        this.frontIndex = 0;
        this.lastIndex = 0;
        this.participants = new ArrayList<>();

        String UID = EmailPasswordActivity.getmAuth().getUid(); // gets current logged in user.
        addParticipant(UID); //adds current logged in user.

        // Queue handling
        FirebaseFunctionsManager.createQueue();
        queue = new ArrayList<>();
        //TODO: Listener for when queue changes.
    }


    /**
     * Create a copy of a current meeting session by basing it of data in Firebase;
     * Use firebaserealtimedatabase please...
     * Use cloud functions to instanciate Queue.
     * @param meetingID the meeting id that is being recreated locally.
     */
    public Meeting(String meetingID){

    }
    private String generateMID(){
        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // create random string builder
        StringBuilder sb = new StringBuilder();
        sb.append("meet-");

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 7;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }

    /**
     * End meeting and saves every person to db.
     */
    public void endMeeting(){

    }
}
