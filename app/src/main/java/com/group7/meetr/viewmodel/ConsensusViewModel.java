package com.group7.meetr.viewmodel;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ConsensusViewModel {

    private final MutableLiveData<List<String>> consenusLiveData = new MutableLiveData<>();
    private ArrayList<String> yesList = new ArrayList<>();
    private ArrayList<String> noList = new ArrayList<>();


    public ConsensusViewModel(ArrayList<String> yesList, ArrayList<String> noList) {
        this.yesList = yesList;
        this.noList = noList;
    }

}
