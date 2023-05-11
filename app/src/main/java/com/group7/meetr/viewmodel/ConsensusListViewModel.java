package com.group7.meetr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.model.Consensus;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConsensusListViewModel {
    private final MutableLiveData<ArrayList<String>> consensusAgreedLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> consensusNotSureLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> consensusAwaitingLiveData = new MutableLiveData<>();


    public ConsensusListViewModel() {
        consensusAgreedObserver();
        consensusNotSureObserver();
        consensusAwaitingObserver();
    }

    private void consensusAgreedObserver() {
        Consensus.getConsensusAgreedSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusAgreedLiveData);
    }

    private void consensusNotSureObserver() {
        Consensus.getConsensusNotSureSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusNotSureLiveData);
    }

    private void consensusAwaitingObserver() {
        Consensus.getConsensusAwaitingSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setConsensusAwaitingLiveData);
    }

    private void setConsensusAgreedLiveData(ArrayList<Object> consensusAgreed) {
        ArrayList<String> consensusAgreedList = parseToStringArray(consensusAgreed);
        consensusAgreedLiveData.setValue(consensusAgreedList);
    }

    private void setConsensusNotSureLiveData(ArrayList<Object> consensusNotSure) {
        ArrayList<String> consensusNotSureList = parseToStringArray(consensusNotSure);
        consensusNotSureLiveData.setValue(consensusNotSureList);
    }

    private void setConsensusAwaitingLiveData(ArrayList<Object> consensusAwaiting) {
        ArrayList<String> consensusAwaitingList = parseToStringArray(consensusAwaiting);
        consensusAwaitingLiveData.setValue(consensusAwaitingList);
    }

    public LiveData<ArrayList<String>> getConsensusAgreedLiveData() {
        return consensusAgreedLiveData;
    }

    public LiveData<ArrayList<String>> getConsensusNotSureLiveData() {
        return consensusNotSureLiveData;
    }

    public LiveData<ArrayList<String>> getConsensusAwaitingLiveData() {
        return consensusAwaitingLiveData;
    }

    private ArrayList<String> parseToStringArray(ArrayList<Object> consensusObjectArrayList){
        ArrayList<String> list = new ArrayList<>();
        for (Object user : consensusObjectArrayList) {
            list.add((String) user);
        }
        return list;
    }
}
