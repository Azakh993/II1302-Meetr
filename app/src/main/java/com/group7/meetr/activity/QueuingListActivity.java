package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.QueueListViewModel;

import java.util.List;

public class QueuingListActivity extends AppCompatActivity {
    private final QueueListViewModel queueListViewModel = new QueueListViewModel();
    private RecyclerView queueListRecyclerView;
    private LiveData<List<String>> queueLiveData;
    private List<String> queue;
    private QueuingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);

        queueListRecyclerView = findViewById(R.id.ParticipantList);
        queueListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        queueLiveData = queueListViewModel.getQueueLiveData();
        queue = queueLiveData.getValue();

        queueLiveData.observe(this, strings -> {
            queue = queueLiveData.getValue();
            adapter = new QueuingListAdapter(queue);
            queueListRecyclerView.setAdapter(adapter);
        });

        ImageButton leaveQueuesButton = findViewById(R.id.btn_leave_queuelist);
        goToModeratorView(leaveQueuesButton);
    }

    private void goToModeratorView(ImageButton leaveParticipantsButton) {
        leaveParticipantsButton.setOnClickListener(view -> {
            Intent intent = new Intent(QueuingListActivity.this, ModeratorActivity.class);
            startActivity(intent);
            finish();
        });
    }

}