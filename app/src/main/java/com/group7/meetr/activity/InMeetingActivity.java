package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.group7.meetr.R;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class InMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);
        Button botton = findViewById(R.id.buttonJoin);

        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make participants activity and replace second variable here.
                intent = new Intent(InMeetingActivity.this, TalkingActivity.class);
                startActivity(intent);
            }
        });

    }
}