package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.group7.meetr.R;

public class QueuingListActivity extends AppCompatActivity {
    RecyclerView queueListRecyclerView;
    QueuingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        queueListRecyclerView = findViewById(R.id.ParticipantList);
        queueListRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


}