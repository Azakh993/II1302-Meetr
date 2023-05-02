package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.group7.meetr.R;
import com.group7.meetr.viewmodel.LoginPageViewModel;

public class LoginPageActivity extends AppCompatActivity {
    private LoginPageViewModel loginPageViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPageViewModel = new ViewModelProvider(this).get(LoginPageViewModel.class);;
        setContentView(R.layout.activity_loginpage);

        emailEditText = findViewById(R.id.inEmail);
        passwordEditText = findViewById(R.id.inPassword);
        loginButton = findViewById(R.id.btn_login);

        loginOnButtonClick(loginButton);
    }

    private void loginOnButtonClick(Button loginButton) {
        loginButton.setOnClickListener(view -> {
            String emailString = emailEditText.getText().toString();
            String passwordString = passwordEditText.getText().toString();

            FirebaseUser currentUser = loginPageViewModel.login(emailString, passwordString);

            if(!isValid(emailString, passwordString)) {
                Toast.makeText(getApplicationContext(), "Incorrect credentials format!",
                        Toast.LENGTH_SHORT).show();
            }

            else if(currentUser == null || currentUser.getUid().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Authentication failure!",
                        Toast.LENGTH_SHORT).show();
            }

            else {
                if (emailString.contains("admin@admin.com")) {
                    switchToModeratorActivity();
                } else {
                    switchToInMeetingActivity();
                }
            }
        });
    }

    private void switchToModeratorActivity() {
        Toast.makeText(getApplicationContext(), "Moderator login successful!",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginPageActivity.this, ModeratorActivity.class);
        startActivity(intent);
    }

    private void switchToInMeetingActivity() {
        Toast.makeText(getApplicationContext(), "Participant login successful!",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginPageActivity.this, InMeetingActivity.class );
        startActivity(intent);
    }

    public boolean isValid(String email, String password) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length() > 5;
    }
}