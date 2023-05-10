package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.RoleSelectionViewModel;

public class RoleSelectionActivity extends AppCompatActivity {
    private final RoleSelectionViewModel roleSelectionViewModel = new RoleSelectionViewModel();
    private LiveData<String> userRoleLiveData;
    private String userRole = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooserole);

        Button createMeetingBtn = findViewById(R.id.btn_NewMeeting);
        bindCreateFunctions(createMeetingBtn);

        Button joinBtn = findViewById(R.id.btn_JoinMeeting);
        bindJoinFunction(joinBtn);

        userRoleLiveData = roleSelectionViewModel.getUserRoleLiveData();
        userRoleLiveData.observe(this, string -> {
            userRole = userRoleLiveData.getValue();
            switchView();
        });
    }

    private void bindCreateFunctions(Button btn) {
        btn.setOnClickListener(view -> {
            roleSelectionViewModel.createMeeting();
        });
    }

    private void switchView() {
        if (userRole.equals("moderator")) {
            switchToModeratorView();
        } else if (userRole.equals("participant")) {
            switchToInMeetingView();
        } else {
            String toastString = "Invalid Meetr ID!";
            Toast.makeText(RoleSelectionActivity.this, toastString, toastString.length()).show();
        }
    }

    private void switchToModeratorView() {
        Intent intent = new Intent(RoleSelectionActivity.this, ModeratorActivity.class);
        startActivity(intent);
    }

    private void bindJoinFunction(Button btn) {
        btn.setOnClickListener(view -> {
            String meetingID = getMeetingID();
            if (meetingID == null) {
                String toastString = "Invalid Meetr ID format!";
                Toast.makeText(RoleSelectionActivity.this, toastString, toastString.length()).show();
            } else {
                roleSelectionViewModel.joinMeeting(meetingID);
            }
        });
    }

    private void switchToInMeetingView() {
        Intent intent = new Intent(RoleSelectionActivity.this, InMeetingActivity.class);
        startActivity(intent);
    }

    private String getMeetingID() {
        TextView meetingID = findViewById(R.id.txt_InputMeeting);
        if (meetingIDValid(meetingID.getText().toString())) {
            return (String) meetingID.getText().toString();
        } else {
            return null;
        }
    }

    private boolean meetingIDValid(String meetingID) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        if (meetingID.length() == 5) {
            for (char c : meetingID.toCharArray()) {
                if (!alphabet.contains(String.valueOf(c))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}