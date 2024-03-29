package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.ConsensusListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ConsensusListActivity extends AppCompatActivity {
    private ConsensusListViewModel consensusListViewModel = new ConsensusListViewModel();

    private RecyclerView posListrecycler, negListrecycler, awaitListrecyler;
    private QueuingListAdapter adapter1, adapter2, awaitadpter;
    private LiveData<ArrayList<String>> consensusAgreedLiveData;
    private LiveData<ArrayList<String>> consensusNotSureLiveData;
    private LiveData<ArrayList<String>> consensusAwaitingLiveData;
    private LiveData<Boolean> userConsensusAwaiting;



    private List<String> poslist;
    private List<String> neglist;
    private List<String> awaitinglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consensus_list);

        posListrecycler = findViewById(R.id.posRecyclerViewList);
        negListrecycler = findViewById(R.id.negRecyclerViewList);
        awaitListrecyler = findViewById(R.id.awaitRecyclerViewList);
        posListrecycler.setLayoutManager(new LinearLayoutManager(this));
        negListrecycler.setLayoutManager(new LinearLayoutManager(this));
        awaitListrecyler.setLayoutManager(new LinearLayoutManager(this));

        consensusAgreedLiveData = consensusListViewModel.getConsensusAgreedLiveData();
        consensusNotSureLiveData = consensusListViewModel.getConsensusNotSureLiveData();
        consensusAwaitingLiveData = consensusListViewModel.getConsensusAwaitingLiveData();

        poslist = consensusAgreedLiveData.getValue();
        neglist = consensusNotSureLiveData.getValue();
        awaitinglist = consensusAwaitingLiveData.getValue();

        consensusAgreedLiveData.observe(this, arraylist -> {
            poslist = consensusAgreedLiveData.getValue();
            adapter1 = new QueuingListAdapter(poslist);
            posListrecycler.setAdapter(adapter1);
        });

        consensusNotSureLiveData.observe(this, arraylist -> {
            neglist = consensusNotSureLiveData.getValue();
            adapter2 = new QueuingListAdapter(neglist);
            negListrecycler.setAdapter(adapter2);
        });
        consensusAwaitingLiveData.observe(this, arraylist -> {
            awaitinglist = consensusAwaitingLiveData.getValue();
            awaitadpter = new QueuingListAdapter(awaitinglist);
            awaitListrecyler.setAdapter(awaitadpter);
        });

        userConsensusAwaiting = consensusListViewModel.getUserConsensusAwaiting();
        userConsensusAwaiting.observe(this, awaitingState -> {
            if(awaitingState) {
                Intent intent = new Intent(ConsensusListActivity.this, ConsensusActivity.class);
                startActivity(intent);
            }
        });
    }


}