package com.group7.meetr.viewmodel;

import com.group7.meetr.data.remote.SessionHandler;

public class InMeetingViewModel {

    private SessionHandler sessionHandler = new SessionHandler();
    public void receiveProximityInput(long timestamp){
        sessionHandler.sendProximityData("7",timestamp);
    }
}
