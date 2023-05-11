package com.group7.meetr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.remote.ConsensusHandler;
import com.group7.meetr.data.remote.UtilFunctions;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConsensusListViewModel {
    private final MutableLiveData<ArrayList<String>> consensusAgreedLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> consensusNotSureLiveData = new MutableLiveData<>();

    public ConsensusListViewModel() {
        consensusAgreedObserver();
    }

    private void consensusAgreedObserver() {
        ConsensusHandler.getConsensusAgreedSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusAgreedLiveData);
    }

    private void consensusNotSureObserver() {
        ConsensusHandler.getConsensusNotSureSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusAgreedLiveData);
    }

    private void setConsensusAgreedLiveData(ArrayList<Object> consensusAgreed) {
        ArrayList<String> consensusAgreedList = UtilFunctions.parseabjhsdbhasj(consensusAgreed);
        consensusAgreedLiveData.setValue(consensusAgreedList);
    }

    private void setConsensusAgreedNotSureLiveData(ArrayList<Object> consensusNotSure) {
        ArrayList<String> consensusNotSureList = UtilFunctions.parseabjhsdbhasj(consensusNotSure);
        consensusAgreedLiveData.setValue(consensusNotSureList);
    }

    public LiveData<ArrayList<String>> getConsensusAgreedLiveData() {
        return consensusAgreedLiveData;
    }

    public LiveData<ArrayList<String>> getConsensusNotSureLiveData() {
        return consensusNotSureLiveData;
    }
}
