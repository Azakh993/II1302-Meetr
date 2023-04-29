package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import android.graphics.Color;
public class InMeetingActivity extends AppCompatActivity {
    private int black = 0x000000;
    private int white = 0xffffff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);
        final RelativeLayout layout1;
        layout1 = findViewById(R.id.activity_participant);

        Button vib = findViewById(R.id.buttonJoin);
        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibr.vibrate(400);
                Intent intent;
                FirebaseFunctionsManager firebaseFunctionsManager = new FirebaseFunctionsManager();
                firebaseFunctionsManager.putInQueueOnFirebase();
                intent = new Intent(InMeetingActivity.this, TalkingActivity.class);
                startActivity(intent);
            }

        });

    }
}