package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group7.meetr.R;
import com.group7.meetr.data.remote.SessionHandler;
import com.group7.meetr.viewmodel.LoginPageViewModel;
import com.group7.meetr.viewmodel.NewOrJoinMeetingViewModel;

import java.util.Random;

public class NewOrJoinMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_join_meeting);
        Button joinBtn = findViewById(R.id.btn_JoinMeeting);
        bindJoinFunction(joinBtn);



        Button createMeetingBtn = findViewById(R.id.btn_NewMeeting);
        bindCreateFunctions(createMeetingBtn);
    }
    private void bindJoinFunction(Button btn){
        btn.setOnClickListener(view -> {
            String mid = getMID();
            if(mid == null){
                String s = "Invalid Meetr ID";
                Toast.makeText(NewOrJoinMeetingActivity.this, s,s.length() ).show();
                return;
            }
            NewOrJoinMeetingViewModel.setCurrentMeetingID(mid);
            SessionHandler.callJoinMeeting(mid,LoginPageViewModel.getCurrentUser().getEmail());
            Intent intent = new Intent(NewOrJoinMeetingActivity.this, InMeetingActivity.class );
            startActivity(intent);
        });

    }
    private String getMID(){
        TextView t = findViewById(R.id.txt_InputMeeting);
        if(checkCorrectMID(t.getText().toString())){
            return (String) t.getText().toString();
        }
        else return null;
    }
    private boolean checkCorrectMID(String meetingID){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        if(meetingID.length() == 5){
            for (char c : meetingID.toCharArray()) {
                if(!alphabet.contains(String.valueOf(c))) return false;
            }
            boolean b = SessionHandler.callCheckIfMeetingExists(meetingID);
            return b;
        }
        return false;
    }
    private void bindCreateFunctions(Button btn){
        btn.setOnClickListener(view -> {
            String mid = generateMID();
            NewOrJoinMeetingViewModel.setCurrentMeetingID(mid);
            SessionHandler.callNewMeeting(LoginPageViewModel.getCurrentUser().getEmail(), mid);
            Intent intent = new Intent(NewOrJoinMeetingActivity.this, ModeratorActivity.class );
            startActivity(intent);
        });
    }
    private String generateMID(){
        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 5;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }
}