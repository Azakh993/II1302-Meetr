package com.group7.meetr.viewmodel;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import com.group7.meetr.data.remote.ConsensusHandler;
import java.util.ArrayList;

public class ConsensusViewModel extends ViewModel {
    public void callSetConsensusStance(boolean agrees, String meetingID) {
        ConsensusHandler.callSetConsensusStance(agrees, meetingID);
    }

    public Observable<ArrayList<Object>> getConsensusAgreedSubject() {
        return ConsensusHandler.getConsensusAgreedSubject();
    }

    public Observable<ArrayList<Object>> getConsensusNotSureSubject() {
        return ConsensusHandler.getConsensusNotSureSubject();
    }
}
