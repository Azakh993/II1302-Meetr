package com.group7.meetr.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.group7.meetr.R;
import com.group7.meetr.viewmodel.ParticipantsListViewModel;

public class ParticipantListActivity extends AppCompatActivity {

    RecyclerView participantListRecyclerView;
    ParticipantListAdapter adapter;
    LiveData<FirebaseRecyclerOptions<String>> participantsListLiveData;
    FirebaseRecyclerOptions<String> participantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        participantListRecyclerView = findViewById(R.id.ParticipantList);
        participantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ParticipantsListViewModel participantsListViewModel = new ParticipantsListViewModel();
        participantsListViewModel.participantsListListener();
        participantsListLiveData = participantsListViewModel.getParticipants();
        participantsList = participantsListLiveData.getValue();
        participantsListLiveData.observe(this, new Observer<FirebaseRecyclerOptions<String>>() {
            @Override
            public void onChanged(FirebaseRecyclerOptions<String> stringFirebaseRecyclerOptions) {
                participantsList = participantsListLiveData.getValue();
            }
        });

        adapter = new ParticipantListAdapter(participantsList);
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