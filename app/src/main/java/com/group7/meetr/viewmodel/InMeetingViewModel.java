package com.group7.meetr.viewmodel;

import static com.group7.meetr.viewmodel.ViewModelUtils.indexObserver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.remote.QueueHandler;
import com.group7.meetr.data.remote.SessionHandler;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InMeetingViewModel {
    private static MutableLiveData<Integer> liveData = new MutableLiveData<>();

    public InMeetingViewModel() {
        indexObserver();
        queueListObserver();
    }

    static void queueListObserver() {
        QueueHandler.observeQueue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(InMeetingViewModel::setLiveData);
    }

    public void receiveProximityInput(long timestamp){
        QueueHandler.sendProximityData("7",timestamp); // ???
        SessionHandler.callNewMeeting(LoginPageViewModel.getCurrentUser().getEmail(),NewOrJoinMeetingViewModel.getCurrentMeeting().getMeetingID());
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
