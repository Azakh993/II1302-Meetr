package com.group7.meetr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.group7.meetr.R;
import com.group7.meetr.viewmodel.LoginPageViewModel;


public class LoginPageActivity extends AppCompatActivity {
    private LoginPageViewModel loginPageViewModel;
    private LiveData<FirebaseUser> firebaseUserLiveData;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPageViewModel = new ViewModelProvider(this).get(LoginPageViewModel.class);
        setContentView(R.layout.activity_loginpage);

        emailEditText = findViewById(R.id.inEmail);
        passwordEditText = findViewById(R.id.inPassword);
        loginButton = findViewById(R.id.btn_login);
        Button btn = findViewById(R.id.btn_registerInstead);
        loginOnButtonClick(loginButton);
        setRegisterButton(btn);

    }


    private void loginOnButtonClick(Button loginButton) {
        loginButton.setOnClickListener(view -> {
            String emailString = emailEditText.getText().toString();
            String passwordString = passwordEditText.getText().toString();

            firebaseUserLiveData = loginPageViewModel.login(emailString, passwordString);
            firebaseUserLiveData.observe(this, firebaseUser -> switchToJoinOrCreateActivity());
        });
    }
    private void setRegisterButton(Button register){
        register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPageActivity.this, RegisterPageActivity.class);
            startActivity(intent);
        });
    }

    private void switchToJoinOrCreateActivity() {
        Toast.makeText(getApplicationContext(), "Login successful!",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginPageActivity.this, RoleSelectionActivity.class);
        startActivity(intent);
    }
}