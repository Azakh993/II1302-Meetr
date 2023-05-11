package com.group7.meetr.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;

import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.SessionHandler;

public class ModeratorViewModel extends BaseObservable {

    public ModeratorViewModel(){

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

    public void endMeeting(Toast toast) {
        SessionHandler.callEndMeeting(Meeting.getMeetingID(), toast);
    }
}
