package com.group7.meetr.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.TalkingViewModel;

public class TalkingActivity extends AppCompatActivity {
    private final TalkingViewModel talkingViewModel = new TalkingViewModel();
    private LiveData<Long> meetingEnded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talking);

        Button button = findViewById(R.id.buttonFinish);

        meetingEnded = talkingViewModel.getMeetingEndedLiveData();
        meetingEnded.observe(this, newMeetingEndedTime -> {
            if(newMeetingEndedTime != 0) {
                finish();
            }
        });

        button.setOnClickListener(view -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(400, 10));
            talkingViewModel.finishedTalking();
            finish();
        });
    }
}