package com.group7.meetr.viewmodel;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;

import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.ConsensusHandler;
import java.util.ArrayList;

public class ConsensusViewModel extends ViewModel {
    private final String meetingID = Meeting.getMeetingID();

    public void callSetConsensusStance(boolean agrees) {
        ConsensusHandler.callSetConsensusStance(agrees, meetingID);
    }
}
