package com.group7.meetr.activity;

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

    private RecyclerView posListrecycler, negListrecycler;
    private QueuingListAdapter adapter1, adapter2;
    private LiveData<ArrayList<String>> consensusAgreedLiveData;
    private LiveData<ArrayList<String>> consensusNotSureLiveData;

    private List<String> poslist;
    private List<String> neglist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consensus_list);

        posListrecycler = findViewById(R.id.posRecyclerViewList);
        negListrecycler = findViewById(R.id.negRecyclerViewList);
        posListrecycler.setLayoutManager(new LinearLayoutManager(this));
        negListrecycler.setLayoutManager(new LinearLayoutManager(this));

        consensusAgreedLiveData = consensusListViewModel.getConsensusAgreedLiveData();
        consensusNotSureLiveData = consensusListViewModel.getConsensusNotSureLiveData();

        poslist = consensusAgreedLiveData.getValue();
        neglist = consensusNotSureLiveData.getValue();

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
    }


}