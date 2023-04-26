package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.MainActivity;
import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseRealtimeDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParticipantListActivity extends AppCompatActivity {

     RecyclerView participantListRecyclerView;
    ParticipantListAdapter adapter;
    FirebaseRealtimeDatabase firebaseRealtimeDatabase;
    List<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);
        firebaseRealtimeDatabase = new FirebaseRealtimeDatabase(FirebaseDatabase.getInstance(), "meetingID", adapter);
        firebaseRealtimeDatabase.addParticipantsListener();

        participantListRecyclerView = findViewById(R.id.ParticipantList);
        participantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParticipantListAdapter(this, adapter.getParticipants());
        participantListRecyclerView.setAdapter(adapter);

        Intent intent = new Intent(ParticipantListActivity.this, MainActivity.class);
        startActivity(intent);


    }
}