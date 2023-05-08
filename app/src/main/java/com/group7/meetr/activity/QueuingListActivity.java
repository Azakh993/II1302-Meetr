package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.QueueListViewModel;

import java.util.List;

public class QueuingListActivity extends AppCompatActivity {
    RecyclerView queueListRecyclerView;
    LiveData<List<String>> queueLiveData;
    List<String> queue;
    QueuingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);

        queueListRecyclerView = findViewById(R.id.ParticipantList);
        queueListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        queueLiveData = QueueListViewModel.getQueueLiveData();
        queue = queueLiveData.getValue();

        queueLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                queue = queueLiveData.getValue();
                adapter = new QueuingListAdapter(queue);
                queueListRecyclerView.setAdapter(adapter);
            }
        });



        ImageButton leaveQueuesButton = findViewById(R.id.btn_leave_queuelist);
        goToModeratorView(leaveQueuesButton);
    }


    private void goToModeratorView(ImageButton leaveParticipantsButton) {
        leaveParticipantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make participants activity and replace second variable here.
                intent = new Intent(QueuingListActivity.this, ModeratorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}