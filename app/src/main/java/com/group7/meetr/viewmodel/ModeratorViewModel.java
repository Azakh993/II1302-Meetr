package com.group7.meetr.viewmodel;

import static com.group7.meetr.data.remote.FirebaseObservers.participantsConsensusObserver;

import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;

import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.ConsensusHandler;
import com.group7.meetr.data.remote.SessionHandler;

public class ModeratorViewModel extends BaseObservable {
    private final String meetingID = Meeting.getMeetingID();

    public ModeratorViewModel(){
        participantsConsensusObserver();
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
        SessionHandler.callEndMeeting(meetingID, toast);
    }

    public void endConsensus() {
        ConsensusHandler.callEndConsensus(meetingID);
    }

    public void startConsensusMode() {
        ConsensusHandler.callStartConsensus(meetingID);
    }
}
