package com.group7.meetr.viewmodel;

import com.group7.meetr.data.remote.QueueHandler;

public class InputViewModel {

    private static final long REQUEST_DEBOUNCE_INTERVAL = 1000; // 1 second
    private long lastRequestTime = 0;

    /**
     *  The receiveSensorInput method is used to receive a user request as a timestamp.
     *  It employs a debounce mechanism which ensures that the data is not sent too frequently, which help reduce spammed requests
     *  to the database.
     * @param timestamp time of request detected from the user sensors
     */
    public void receiveProximityInput(long timestamp){
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastRequestTime > REQUEST_DEBOUNCE_INTERVAL) {
            QueueHandler.sendProximityData("7",timestamp);
            lastRequestTime = currentTime;
        }
    }
}
