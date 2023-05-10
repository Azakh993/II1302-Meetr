package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group7.meetr.R;

public class ConsensusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consensus);

        Button yesButton = findViewById(R.id.button_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ConsensusActivity.this, ConsensusListActivity.class);
                startActivity(intent);
            }
        });

        Button noButton = findViewById(R.id.button_no);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ConsensusActivity.this, ConsensusListActivity.class);
                startActivity(intent);
            }
        });
    }
}