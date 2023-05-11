package com.group7.meetr.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;

import java.util.ArrayList;
import java.util.List;

public class ConsensusListActivity extends AppCompatActivity {

    private RecyclerView posListrecycler, negListrecycler;
    private QueuingListAdapter adapter1, adapter2;
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

        poslist = new ArrayList<>();
        neglist = new ArrayList<>();

        poslist.add("hej");
        poslist.add("hej");
        //poslist.add("hej");

        neglist.add("test");
        //neglist.add("test");
        //neglist.add("test");


        adapter1 = new QueuingListAdapter(poslist);
        posListrecycler.setAdapter(adapter1);

        adapter2 = new QueuingListAdapter(neglist);
        negListrecycler.setAdapter(adapter2);

    }


}