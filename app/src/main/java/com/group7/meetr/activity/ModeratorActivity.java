package com.group7.meetr.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.group7.meetr.R;
import com.group7.meetr.data.model.Meeting;
import com.group7.meetr.data.remote.SessionHandler;
import com.group7.meetr.data.remote.UtilFunctions;
import com.group7.meetr.databinding.ActivityModeratorBinding;
import com.group7.meetr.viewmodel.ModeratorViewModel;

import java.util.HashMap;
import java.util.Map;

public class ModeratorActivity extends AppCompatActivity {
    private final ModeratorViewModel moderatorViewModel = new ModeratorViewModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityModeratorBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_moderator);
        activityMainBinding.setViewModel(moderatorViewModel);
        activityMainBinding.executePendingBindings();
        TextView t = findViewById(R.id.txt_meetingID);
        t.setText(Meeting.getMeetingID());

        Button optionsButton = findViewById(R.id.btn_options);
        goToOptions(optionsButton);

        Button participantsButton = findViewById(R.id.btn_participants);
        goToParticipants(participantsButton);

        ImageButton lobbyImgButton = findViewById(R.id.btn_lobby_code);
        goToLobby(lobbyImgButton);

        ImageButton leaveMeetingButton = findViewById(R.id.btn_leave_meeting);
        goToLogin(leaveMeetingButton);

        Button joinButton = findViewById(R.id.btn_join);
        goToParticipation(joinButton);

        Button queueButton = findViewById(R.id.btn_queue);
        goToQueue(queueButton);

        Button endButton = findViewById(R.id.btn_endMeeting);
        goToEndMeetingPrompt(endButton);

    }

    private void goToOptions(Button optionsButton) {
        optionsButton.setOnClickListener(view -> {

        });
    }
    private void goToEndMeetingPrompt(Button btn) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SessionHandler.callEndMeeting(Meeting.getMeetingID());
                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        btn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ModeratorActivity.this);
            builder.setMessage("Are you sure you want to conclude the meeting?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });
    }

    private void goToParticipants(Button participantsButton) {
        participantsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ModeratorActivity.this, ParticipantListActivity.class);
            startActivity(intent);
        });
    }

    private void goToQueue(Button queueButton) {
        queueButton.setOnClickListener(view -> {
            Intent intent = new Intent(ModeratorActivity.this, QueuingListActivity.class);
            startActivity(intent);
        });
    }

    private void goToParticipation(Button participation) {
        participation.setOnClickListener(view -> {
            Intent intent = new Intent(ModeratorActivity.this, InMeetingActivity.class);
            startActivity(intent);
        });
    }

    private void goToLobby(ImageButton lobbyImgButton) {
        lobbyImgButton.setOnClickListener(view -> {
        });
    }

    private void goToLogin(ImageButton leaveMeetingButton) {
        leaveMeetingButton.setOnClickListener(view -> {
            Intent intent = new Intent(ModeratorActivity.this, LoginPageActivity.class);
            startActivity(intent);
        });
    }
}