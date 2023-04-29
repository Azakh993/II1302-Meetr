package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;
import com.group7.meetr.databinding.ActivityModeratorBinding;
import com.group7.meetr.viewmodel.ModeratorViewModel;

public class ModeratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
        ModeratorViewModel lpvm = new ModeratorViewModel();
        ActivityModeratorBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_moderator);
        activityMainBinding.setViewModel(lpvm);
        activityMainBinding.executePendingBindings();
        FirebaseFunctionsManager.createQueue();


        /*
        Define buttons
         */
        Button go_to_options = findViewById(R.id.btn_options);
        go_to_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make options activity and replace second variable here.
                //intent = new Intent(ModeratorActivity.this, OptionsActivity.class);
                //startActivity(intent);
            }
        });
        Button go_to_participants = findViewById(R.id.btn_participants);
        go_to_participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make participants activity and replace second variable here.
                intent = new Intent(ModeratorActivity.this, InMeetingActivity.class);
                startActivity(intent);
            }
        });
        Button go_to_vote = findViewById(R.id.btn_vote);
        go_to_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make vote activity and replace second variable here.
                //intent = new Intent(ModeratorActivity.this, OptionsActivity.class);
                //startActivity(intent);
            }
        });
        ImageButton go_to_qr = findViewById(R.id.btn_lobby_code);
        go_to_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                //TODO: Make lobby qr activity and replace second variable here.
                //intent = new Intent(ModeratorActivity.this, OptionsActivity.class);
                //startActivity(intent);
            }
        });
        ImageButton leave_meeting = findViewById(R.id.btn_leave_meeting);
        leave_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ModeratorActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });
    }
}