package com.group7.meetr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.User;
import com.group7.meetr.data.remote.SessionHandler;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoleSelectionViewModel {
    private final MutableLiveData<String> userRoleLiveData = new MutableLiveData<>();
    private String meetingID;
    private String createOrJoin = null;

    public RoleSelectionViewModel() {
        meetingIDValidityObserver();
    }

    private void meetingIDValidityObserver() {
        SessionHandler.observeMeetingIDValidity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMeetingIDValidity);
    }

    private void setMeetingIDValidity(Boolean meetingIDExists) {
        if(meetingIDExists && createOrJoin.equals("create")) {
            userRoleLiveData.setValue("moderator");
            Meeting.setMeetingID(meetingID);
            SessionHandler.callNewMeeting(User.getEmail(), meetingID);
        } else if(!meetingIDExists && createOrJoin.equals("join")) {
            userRoleLiveData.setValue("participant");
            Meeting.setMeetingID(meetingID);
            SessionHandler.callJoinMeeting(meetingID, User.getEmail());
        } else {
            userRoleLiveData.setValue("invalid");
        }
    }

    private String generateMeetingID() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int meetingIDLength = 5;
        for (int i = 0; i < meetingIDLength; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public void joinMeeting(String meetingID) {
        createOrJoin = "join";
        this.meetingID = meetingID;
        checkMeetingID(meetingID);
    }

    public void createMeeting() {
        createOrJoin = "create";
        meetingID = generateMeetingID();
        checkMeetingID(meetingID);
    }

    public void checkMeetingID(String meetingID) {
        SessionHandler.callCheckIfMeetingExists(meetingID);
    }

    public LiveData<String> getUserRoleLiveData() {
        return userRoleLiveData;
    }
}
