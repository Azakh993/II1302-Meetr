package com.group7.meetr.viewmodel;

import com.group7.meetr.data.remote.SessionHandler;

public class InputViewModel {

    private SessionHandler sessionHandler = new SessionHandler();
    public void receiveProximityInput(long timestamp){
        String sessionId = "7";
        sessionHandler.sendProximityData(sessionId, timestamp);
    }
}
