package com.group7.meetr.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseRealtimeDatabase;

public class ParticipantListActivity extends AppCompatActivity {

     RecyclerView participantListRecyclerView;
    ParticipantListAdapter adapter;
    FirebaseRealtimeDatabase firebaseRealtimeDatabase;
    String[] names = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);
        firebaseRealtimeDatabase = new FirebaseRealtimeDatabase(FirebaseDatabase.getInstance(), "meetingID", adapter);
        firebaseRealtimeDatabase.addParticipantsListener();


        //String[] updatedNames = adapter.setNames(new String[]{"hej", "hejsan", "hur", "mår", "du"}); // call setNames() and retrieve updated names array
        //recyclerView.setAdapter(new ParticipantListAdapter(getApplicationContext(), updatedNames)); // set adapter with updated names array


        participantListRecyclerView = findViewById(R.id.ParticipantList);
        participantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParticipantListAdapter(getApplicationContext(), new String[0]); // create new instance
        String[] updatedNames = adapter.setNames(new String[]{"hej", "hejsan", "hur", "mår", "du"}); // call setNames() and retrieve updated names array
        participantListRecyclerView.setAdapter(new ParticipantListAdapter(getApplicationContext(), updatedNames)); // set adapter with updated names array


        //Intent intent = new Intent(ParticipantListActivity.this, MainActivity.class);
        //startActivity(intent);


    }
}