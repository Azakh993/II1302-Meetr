package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

        Button vib = findViewById(R.id.buttonJoin);
        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibr.vibrate(400);
            }
        });
    }
}