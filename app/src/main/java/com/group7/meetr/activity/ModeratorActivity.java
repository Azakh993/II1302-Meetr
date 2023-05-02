package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.group7.meetr.R;
import com.group7.meetr.databinding.ActivityModeratorBinding;
import com.group7.meetr.viewmodel.ModeratorViewModel;

public class ModeratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModeratorViewModel moderatorViewModel = new ModeratorViewModel();
        ActivityModeratorBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_moderator);
        activityMainBinding.setViewModel(moderatorViewModel);
        activityMainBinding.executePendingBindings();

        Button optionsButton = findViewById(R.id.btn_options);
        goToOptions(optionsButton);

        Button participantsButton = findViewById(R.id.btn_participants);
        goToParticipants(participantsButton);

        Button voteButton = findViewById(R.id.btn_vote);
        goToVote(voteButton);

        ImageButton lobbyImgButton = findViewById(R.id.btn_lobby_code);
        goToLobby(lobbyImgButton);

        ImageButton leaveMeetingButton = findViewById(R.id.btn_leave_meeting);
        goToLogin(leaveMeetingButton);
    }

    private void goToOptions(Button optionsButton) {
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make options activity and replace second variable here.
                //intent = new Intent(ModeratorActivity.this, OptionsActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void goToParticipants(Button participantsButton) {
        participantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make participants activity and replace second variable here.
                intent = new Intent(ModeratorActivity.this, ParticipantListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goToVote(Button voteButton) {
        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make vote activity and replace second variable here.
                //intent = new Intent(ModeratorActivity.this, OptionsActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void goToLobby(ImageButton lobbyImgButton) {
        lobbyImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make lobby qr activity and replace second variable here.
                //intent = new Intent(ModeratorActivity.this, OptionsActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void goToLogin(ImageButton leaveMeetingButton) {
        leaveMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ModeratorActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });
    }
}