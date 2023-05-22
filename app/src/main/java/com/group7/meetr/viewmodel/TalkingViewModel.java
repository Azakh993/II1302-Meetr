package com.group7.meetr.viewmodel;

import static com.group7.meetr.data.remote.ConsensusHandler.consensusObserver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.model.Consensus;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.FirebaseObservers;
import com.group7.meetr.data.remote.QueueHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TalkingViewModel {
    private final MutableLiveData<Long> meetingEndedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> userConsensusAwaiting = new MutableLiveData<>();


    private final String meetingID = Meeting.getMeetingID();

    public TalkingViewModel() {
        FirebaseObservers.endTimeObserver();
        meetingEndedObserver();
        consensusObserver();
        userConsensusObserver();
    }

    public void finishedTalking() {
        QueueHandler.callFinishedTalking(meetingID);
    }

    private void meetingEndedObserver() {
        Meeting.observeMeetingEnded()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMeetingEndedLiveData);
    }

    private void userConsensusObserver() {
        Consensus.getUserConsensusAwaiting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setUserConsensusAwaiting);
    }

    private void setUserConsensusAwaiting(Boolean userConsensusAwaiting) {
        this.userConsensusAwaiting.setValue(userConsensusAwaiting);
    }

    public LiveData<Long> getMeetingEndedLiveData() {
        return meetingEndedLiveData;
    }

    public LiveData<Boolean> getUserConsensusAwaitingLiveData() {
        return userConsensusAwaiting;
    }
    public void setMeetingEndedLiveData(Long meetingEnded) {
        meetingEndedLiveData.setValue(meetingEnded);
    }
}
