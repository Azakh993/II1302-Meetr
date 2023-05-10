package com.group7.meetr.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.databinding.BaseObservable;

public class ModeratorViewModel extends BaseObservable {

    public ModeratorViewModel(){

    }

    public void onBtnNextClicked(){
        Log.d("!TALKER", "Current talker should change!");
        //TODO: Insert next participant here
    }
    public void onBtnParticipantsClicked(){
        Log.d("!ACTIVITY", "Change view to Participants!");
        //TODO: Swap view + activity here.
    }
    public void onBtnOptionsClicked(){
        Log.d("!TALKER", "DEAD BUTTON...");
        //TODO: Add voting system and add that here? idk...
    }
    public void onBtnQRClicked(){
        Log.d("!ACTIVITY", "DEAD BUTTON...");
        //TODO: Add voting system and add that here? idk...
    }
    public void onBtnLeaveClicked(){
        Log.d("!ACTIVITY", "DEAD BUTTON...");
        //TODO: Add voting system and add that here? idk...
    }
}
