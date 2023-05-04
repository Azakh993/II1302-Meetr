package com.group7.meetr.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.model.Participant;
import com.group7.meetr.data.remote.SessionHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class InMeetingViewModel {
    private static MutableLiveData<Integer> liveData = new MutableLiveData<>();

    private SessionHandler sessionHandler = new SessionHandler();
    public void receiveProximityInput(long timestamp){
        sessionHandler.sendProximityData("7",timestamp);
    }

    /**
     * If next user in queue, set livedata to 2
     * if current user is 0 in queue set to 1
     * otherwise it will stay at 0
     * @param objects
     */
    public static void setLiveData(ArrayList<Object> objects){
        if(objects.size() > 1){
            if(isMyUser(objects.get(1)))
            {
                liveData.setValue(2);
            }
        }
        if(objects.size() > 0){
            if(isMyUser(objects.get(0)))
            {
                liveData.setValue(1);
            }
        }
        liveData.setValue(0);

    }
    private static boolean isMyUser(Object o){
        if(o == null) return false;
        HashMap<String, Object> test = (HashMap<String, Object>) o;
        String uid = (String)test.get("uid");
        String userName = (String) test.get("name");
        if(uid.equals(LoginPageViewModel.getCurrentUser().getUid()) && userName.equals(LoginPageViewModel.getCurrentUser().getEmail())){
            return true;
        }
        return false;

    }

    public static LiveData<Integer> getLiveData() {
        return liveData;
    }
}