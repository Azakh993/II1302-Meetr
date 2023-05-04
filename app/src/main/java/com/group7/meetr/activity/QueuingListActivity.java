package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.QueueListViewModel;

import java.util.List;

public class QueuingListActivity extends AppCompatActivity {
    RecyclerView queueListRecyclerView;
    LiveData<List<String>> queueLiveData;
    List<String> queue;
    QueuingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        queueListRecyclerView = findViewById(R.id.ParticipantList);
        queueListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        queueLiveData = QueueListViewModel.getQueueLiveData();
        queue = queueLiveData.getValue();

        queueLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                queue = queueLiveData.getValue();
            }
        });
    }
}