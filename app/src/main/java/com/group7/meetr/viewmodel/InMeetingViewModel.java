package com.group7.meetr.viewmodel;

import static com.group7.meetr.data.remote.ConsensusHandler.consensusObserver;
import static com.group7.meetr.data.remote.FirebaseObservers.indexObserver;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group7.meetr.data.model.Consensus;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.User;
import com.group7.meetr.data.remote.FirebaseObservers;
import com.group7.meetr.data.remote.QueueHandler;
import com.group7.meetr.data.remote.UtilFunctions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InMeetingViewModel {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
    private final MutableLiveData<Integer> queuePositionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> meetingEndedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> userConsensusAwaiting = new MutableLiveData<>();

    private ArrayList<Object> queueArrayList = new ArrayList<>();
    private final String email;
    private final String uid;
    private final String meetingID;

    public InMeetingViewModel() {
        email = User.getEmail();
        uid = User.getUid();
        meetingID = Meeting.getMeetingID();
        indexObserver();
        queueListObserver();
        consensusObserver();
        userConsensusObserver();
        FirebaseObservers.endTimeObserver();
        meetingEndedObserver();
    }

    private void queueListObserver() {
        QueueHandler.observeQueue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setQueueParameters);
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

    public void enqueue() {
        ArrayList<String> queue = UtilFunctions.parseQueueArrayList(queueArrayList);

        if(queue.isEmpty()) {
            QueueHandler.callEnqueue(meetingID, email, System.currentTimeMillis());
            return;
        }

        for (String email: queue) {
            if(email.equals(this.email)) {
              return;
            }
        }
        QueueHandler.callEnqueue(meetingID, email, System.currentTimeMillis());
    }

    public void replyEnqueue() {
        ArrayList<String> queue = UtilFunctions.parseQueueArrayList(queueArrayList);

        if(queue.isEmpty()) {
            QueueHandler.callReplyEnqueue(meetingID, email, System.currentTimeMillis());
            return;
        }

        for (String email: queue) {
            if(email.equals(this.email)) {
                return;
            }
        }
        QueueHandler.callReplyEnqueue(meetingID, email, System.currentTimeMillis());
    }

    public void inclusiveEnqueue() {
        ArrayList<String> queue = UtilFunctions.parseQueueArrayList(queueArrayList);

        if(queue.isEmpty()) {
            QueueHandler.callInclusiveEnqueue(meetingID, email, System.currentTimeMillis());
            return;
        }

        for (String email: queue) {
            if(email.equals(this.email)) {
                return;
            }
        }
        QueueHandler.callInclusiveEnqueue(meetingID, email, System.currentTimeMillis());
    }

    private boolean checkPosition(Object userObject) {
        if (userObject == null) {
            return false;
        }
        HashMap<String, Object> userHashMap = (HashMap<String, Object>) userObject;
        String userUID = (String) userHashMap.get("uid");
        String userEmail = (String) userHashMap.get("name");

        if(userEmail != null && userUID != null) {
            return userUID.equals(uid) && userEmail.equals(email);
        }

        return false;
    }

    public LiveData<Integer> getQueuePositionLiveData() {
        return queuePositionLiveData;
    }

    /**
     * If next user in queue, set livedata to 2
     * if current user is 0 in queue set to 1
     * otherwise it will stay at 0
     *
     * @param queue
     */
    public void setQueueParameters(ArrayList<Object> queue) {
        queueArrayList = queue;
        if (queue.size() > 1) {
            Object secondInQueue = queue.get(1);
            if (checkPosition(secondInQueue)) {
                queuePositionLiveData.setValue(2);
            }
        }
        if (queue.size() > 0) {
            Object firstInQueue = queue.get(0);
            if (checkPosition(firstInQueue)) {
                queuePositionLiveData.setValue(1);
            }
        }
        queuePositionLiveData.setValue(0);
    }

    private void setUserConsensusAwaiting(Boolean userConsensusAwaiting) {
        this.userConsensusAwaiting.setValue(userConsensusAwaiting);
    }

    public LiveData<Long> getMeetingEndedLiveData() {
        return meetingEndedLiveData;
    }

    public void setMeetingEndedLiveData(Long meetingEnded) {
        meetingEndedLiveData.setValue(meetingEnded);
    }

    public LiveData<Boolean> getUserConsensusAwaiting() {
        return userConsensusAwaiting;
    }
}
