package com.group7.meetr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.QueueHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TalkingViewModel {
    private final MutableLiveData<Long> meetingEndedLiveData = new MutableLiveData<>();

    private final String meetingID = Meeting.getMeetingID();

    public TalkingViewModel() {
        ViewModelUtils.endTimeObserver();
        meetingEndedObserver();
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

    public LiveData<Long> getMeetingEndedLiveData() {
        return meetingEndedLiveData;
    }
    public void setMeetingEndedLiveData(Long meetingEnded) {
        meetingEndedLiveData.setValue(meetingEnded);
    }
}
