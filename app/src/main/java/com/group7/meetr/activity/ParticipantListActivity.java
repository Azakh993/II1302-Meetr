package com.group7.meetr.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.R;

public class ParticipantListActivity extends AppCompatActivity {

    RecyclerView participantListRecyclerView;
    ParticipantListAdapter adapter;
    //FirebaseRealtimeDatabase firebaseRealtimeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        participantListRecyclerView = findViewById(R.id.ParticipantList);
        participantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
        //firebaseRealtimeDatabase = new FirebaseRealtimeDatabase(database, "meetingID");
        //String[] str = adapter.getNames();
        //firebaseRealtimeDatabase.addParticipantsListener();

        FirebaseRecyclerOptions<Participant> options =
                new FirebaseRecyclerOptions.Builder<Participant>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("/Sessions/7/").child("Participants"), Participant.class)
                        .build();

        adapter = new ParticipantListAdapter(options);
        participantListRecyclerView.setAdapter(adapter);

        //adapter = new ParticipantAdapter(getApplicationContext(), new String[0]); // create new instance
        //String[] str = adapter.setUsers(new String[]{"user@user.com", "user2@user.com", "user@user.com", "user@user.com"});
        //adapter = new ParticipantAdapter(getApplicationContext(), adapter.getNames());
        //participantListRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
