package com.group7.meetr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ParticipantsListViewModel {
    private final FirebaseDatabase database;
    private MutableLiveData<FirebaseRecyclerOptions<String>> participants = new MutableLiveData<>();
    private FirebaseRecyclerOptions<String> participantsList;
    private final String MEETINGID = "7";

    public ParticipantsListViewModel() {
        this.database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");;
     }

    public void participantsListListener() {
        participantsList =
                new FirebaseRecyclerOptions.Builder<String>()
                        .setQuery(database.getReference("/Sessions/")
                                .child("/7/").child("Participants"), String.class)
                        .build();
        participants.setValue(participantsList);
    }

    public LiveData<FirebaseRecyclerOptions<String>> getParticipants() {
        return participants;
    }
}
