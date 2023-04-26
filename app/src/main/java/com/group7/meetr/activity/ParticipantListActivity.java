package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseRealtimeDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticipantListActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_participant_list);

            RecyclerView recyclerView = findViewById(R.id.ParticipantList);

            //TODO: Be firebase om listan av namn. Alla namn över ska bort.

            ParticipantListAdapter adapter = new ParticipantListAdapter();
            //String[] names = myAdapter.getParticipants();
            Log.d("TAG", "onCreate: names" + Arrays.toString(adapter.getParticipants()));
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
            //TODO: Move this code to a more appropriate place.
            FirebaseRealtimeDatabase realtimeDatabase = new FirebaseRealtimeDatabase(database, "7", adapter);
            realtimeDatabase.addParticipantsListener();

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ParticipantListAdapter(getApplicationContext(), adapter.getParticipants()));

        }
    }