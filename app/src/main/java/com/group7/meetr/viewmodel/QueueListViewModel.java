package com.group7.meetr.viewmodel;

import static com.group7.meetr.viewmodel.ViewModelUtils.indexObserver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.remote.QueueHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QueueListViewModel {
    private static MutableLiveData<List<String>> queueLiveData = new MutableLiveData<>();
    private static ArrayList<String> queueList = new ArrayList<>();

    public QueueListViewModel() {
        indexObserver();
        queueListObserver();
    }

    static void queueListObserver() {
        QueueHandler.observeQueue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(QueueListViewModel::setQueue);
    }

    static void setQueue(ArrayList<Object> queue) {
        QueueListViewModel.setQueueList(queue);
        QueueListViewModel.queueLiveData.setValue(QueueListViewModel.queueList);
    }

    public static LiveData<List<String>> getQueueLiveData() {
        return QueueListViewModel.queueLiveData;
    }

    private static void setQueueList(ArrayList<Object> queue) {
        HashMap<String, String> queueHashMap;
        queueList.clear();
        for(Object item : queue) {
            queueHashMap = (HashMap<String, String>) item;
            String arrayListItem = queueHashMap.get("name");
            queueList.add(arrayListItem);
        }
    }


}
