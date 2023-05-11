package com.group7.meetr.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.model.Participant;

public class ConsensusListViewModel {
    private final FirebaseDatabase database;
    private final MutableLiveData<FirebaseRecyclerOptions<Participant>> participants = new MutableLiveData<>();
    private final MutableLiveData<FirebaseRecyclerOptions<Participant>> yesParticipants = new MutableLiveData<>();
    private final MutableLiveData<FirebaseRecyclerOptions<Participant>> noParticipants = new MutableLiveData<>();
    private FirebaseRecyclerOptions<Participant> participantsList;
    private FirebaseRecyclerOptions<Participant> yesParticipantsList;
    private FirebaseRecyclerOptions<Participant> noParticipantsList;

    public ConsensusListViewModel() {
        this.database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public void participantsListListener() {
        participantsList =
                new FirebaseRecyclerOptions.Builder<Participant>()
                        .setQuery(database.getReference("Sessions")
                                .child(Meeting.getMeetingID()).child("ListOfParticipants"), Participant.class)
                        .build();
        participants.setValue(participantsList);
    }

    public void yesListListener() {
        participantsList =
                new FirebaseRecyclerOptions.Builder<Participant>()
                        .setQuery(database.getReference("Sessions")
                                .child(Meeting.getMeetingID()).child("ListOfParticipants"), Participant.class)
                        .build();
        yesParticipants.setValue(yesParticipantsList);
    }
    public void noListListener() {
        participantsList =
                new FirebaseRecyclerOptions.Builder<Participant>()
                        .setQuery(database.getReference("Sessions")
                                .child(Meeting.getMeetingID()).child("ListOfParticipants"), Participant.class)
                        .build();
        noParticipants.setValue(noParticipantsList);
    }

    public LiveData<FirebaseRecyclerOptions<Participant>> getParticipants() {
        return participants;
    }
    public LiveData<FirebaseRecyclerOptions<Participant>> getYesParticipants() {
        return yesParticipants;
    }
    public LiveData<FirebaseRecyclerOptions<Participant>> getNoParticipants() {
        return noParticipants;
    }
}