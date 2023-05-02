package com.group7.meetr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.data.model.Participant;

public class ParticipantsListViewModel {
    private final FirebaseDatabase database;
    private MutableLiveData<FirebaseRecyclerOptions<Participant>> participants = new MutableLiveData<>();
    private FirebaseRecyclerOptions<Participant> participantsList;

    public ParticipantsListViewModel() {
        this.database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");;
    }

    public void participantsListListener() {
        participantsList =
                new FirebaseRecyclerOptions.Builder<Participant>()
                        .setQuery(database.getReference("/Sessions/")
                                .child("/7/").child("ListOfParticipants"), Participant.class)
                        .build();
        participants.setValue(participantsList);
    }

    public LiveData<FirebaseRecyclerOptions<Participant>> getParticipants() {
        return participants;
    }
}