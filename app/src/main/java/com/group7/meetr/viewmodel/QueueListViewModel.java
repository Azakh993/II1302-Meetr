package com.group7.meetr.viewmodel;

import static com.group7.meetr.viewmodel.FirebaseObservers.indexObserver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group7.meetr.data.remote.QueueHandler;
import com.group7.meetr.data.remote.UtilFunctions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QueueListViewModel {
    private final MutableLiveData<List<String>> queueLiveData = new MutableLiveData<>();
    private ArrayList<String> queueList = new ArrayList<>();

    public QueueListViewModel() {
        indexObserver();
        queueListObserver();
    }

    private void queueListObserver() {
        QueueHandler.observeQueue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setQueue);
    }

    private void setQueue(ArrayList<Object> queue) {
        queueList = UtilFunctions.parseQueueArrayList(queue);
        queueLiveData.setValue(queueList);
    }

    public LiveData<List<String>> getQueueLiveData() {
        return queueLiveData;
    }
}
