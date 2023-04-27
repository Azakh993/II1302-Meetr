package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;


import com.group7.meetr.R;

public class TalkingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talking);

        Button botton = findViewById(R.id.buttonFinish);


        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibr.vibrate(400);
                Intent intent;
                //TODO: Make participants activity and replace second variable here.
                intent = new Intent(TalkingActivity.this, InMeetingActivity.class);
                startActivity(intent);
            }
        });
    }
}