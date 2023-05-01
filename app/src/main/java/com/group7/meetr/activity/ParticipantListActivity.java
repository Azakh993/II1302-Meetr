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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        participantListRecyclerView = findViewById(R.id.ParticipantList);
        participantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         * FirebaseRecyclerOptions object is used to configure
         * a RecyclerView adapter that will
         * display a list of strings retrieved
         * from the Firebase Realtime Database.
         */
        FirebaseRecyclerOptions<String> options =
                new FirebaseRecyclerOptions.Builder<String>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("/Sessions/7/").child("Participants"), String.class)
                        .build();

        adapter = new ParticipantListAdapter(options);
        participantListRecyclerView.setAdapter(adapter);

    }

    /**
     *This method starts listening for changes in the data source
     * (in this case, the Firebase Realtime Database)
     * and updates the RecyclerView adapter accordingly.
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * This method stops listening for changes in
     * the data source (the Firebase Realtime Database)
     * and frees up resources used by the adapter.
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
