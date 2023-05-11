package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.group7.meetr.R;
import com.group7.meetr.viewmodel.ConsensusViewModel;

public class ConsensusActivity extends AppCompatActivity {

    private ConsensusViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consensus);

        viewModel = new ViewModelProvider(this).get(ConsensusViewModel.class);

        Button yesButton = findViewById(R.id.button_yes);
        yesButton.setOnClickListener(v -> {
            viewModel.callSetConsensusStance(true);
            navigateToConsensusListActivity();
        });

        Button noButton = findViewById(R.id.button_no);
        noButton.setOnClickListener(v -> {
            viewModel.callSetConsensusStance(false);
            navigateToConsensusListActivity();
        });
    }

    private void navigateToConsensusListActivity() {
        Intent intent = new Intent(ConsensusActivity.this, ConsensusListActivity.class);
        startActivity(intent);
    }
}
