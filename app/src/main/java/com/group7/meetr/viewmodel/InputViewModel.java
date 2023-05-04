package com.group7.meetr.viewmodel;

import com.group7.meetr.data.remote.SessionHandler;

public class InputViewModel {

    private SessionHandler sessionHandler = new SessionHandler();

    private static final long REQUEST_DEBOUNCE_INTERVAL = 1000; // 1 second
    private long lastRequestTime = 0;

    public void receiveProximityInput(long timestamp){
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastRequestTime > REQUEST_DEBOUNCE_INTERVAL) {
            sessionHandler.sendProximityData("7",timestamp);
            lastRequestTime = currentTime;
        }
    }
}
