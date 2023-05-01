package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseRealtimeDatabase;
import com.group7.meetr.databinding.ActivityLoginpageBinding;
import com.group7.meetr.viewmodel.LoginPageViewModel;

public class LoginPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel updates the Model
        // after observing changes in the View

        // model will also update the view
        // via the ViewModel

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");
        LoginPageViewModel lpvm = new LoginPageViewModel(database);
        ActivityLoginpageBinding activityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_loginpage);
        activityMainBinding.setViewModel(lpvm);
        activityMainBinding.executePendingBindings();
        Button go_to_meeting = findViewById(R.id.btn_login);
        FirebaseRealtimeDatabase realtimeDatabase = new FirebaseRealtimeDatabase(database, "7");
        //realtimeDatabase.addParticipantsListener();
        go_to_meeting.setOnClickListener(new View.OnClickListener() {
            @Override  //skriver inte över?? nvm fucking skriver över -.-
            public void onClick(View view) {
                // har en ide för att lösa detta. confirmation funktion i loginpageviewmodel som confirmar med hjälp av dens vetande
                int i = lpvm.checkLogin();
                Log.d("!USER LOGIN INFO", "SUCCESSCODE" + i);
                Intent intent;
                if(i == 3) {
                    intent = new Intent(LoginPageActivity.this, ModeratorActivity.class);
                    startActivity(intent);

                }else if(i == 1){
                    intent = new Intent(LoginPageActivity.this, InMeetingActivity.class );//TODO: user-activity here!!!)
                    startActivity(intent);
                }

            }
        });
    }

}