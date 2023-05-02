package com.group7.meetr.data.model;

import com.group7.meetr.viewmodel.LoginPageViewModel;

public class Participant {
    //1 UUID, talkingScore*, name;
    String UUID;
  String name;
    float talkingScore; // Might onyl need to exist on server...

    //Participant specific values. Not stored permanently but useful in meetings.
    // to not store permanently, simply null them before database storage.


    public Participant() {
    }

    public Participant(String UUID, String name, float talkingScore, boolean isModerator) {
        this.UUID = UUID;
        this.name = name;
        this.talkingScore = talkingScore;
        this.isModerator = isModerator;
    }

    boolean isModerator;

    public String getUUID() {
        return UUID;
    }

    public float getTalkingScore() {
        return talkingScore;
    }

    public String getName() {
        return name;
    }
    public boolean getIsModerator(){
        return isModerator;
    }


    public boolean isModerator() {
        return isModerator;
    }




    /**
     * A participant clientside for a specific meeting clientside.
     */
    public Participant(String displayName, boolean createdMeeting){
        this.UUID = LoginPageViewModel.getCurrentUser().getUid();
        this.name = LoginPageViewModel.getCurrentUser().getEmail();
        this.talkingScore = (float) 0.69;
        this.isModerator = createdMeeting;
    }
    public Participant(String UUID){
        //TODO: Get info from DB??
    }
}
