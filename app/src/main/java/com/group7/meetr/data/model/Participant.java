package com.group7.meetr.data.model;

import com.group7.meetr.viewmodel.LoginPageViewModel;

public class Participant {
    //1 UUID, talkingScore*, name;
    String UUID;
    String userEmail;
    String userName;
    float talkingScore; // Might onyl need to exist on server...

    //Participant specific values. Not stored permanently but useful in meetings.
    // to not store permanently, simply null them before database storage.
    boolean isModerator;

    public String getUUID() {
        return UUID;
    }

    public float getTalkingScore() {
        return talkingScore;
    }

    public String getUserName() {
        return userName;
    }
    public boolean getIsModerator(){
        return isModerator;
    }


    public boolean isModerator() {
        return isModerator;
    }

    public String getUserEmail() {
        return userEmail;
    }


    /**
     * A participant clientside for a specific meeting clientside.
     */
    public Participant(String displayName, boolean createdMeeting){
        this.UUID = LoginPageViewModel.getCurrentUser().getUid();
        this.userEmail = LoginPageViewModel.getCurrentUser().getEmail();
        this.talkingScore = (float) 0.69;
        this.userName = displayName;
        this.isModerator = createdMeeting;
    }
    public Participant(String UUID){
        //TODO: Get info from DB??
    }
}
