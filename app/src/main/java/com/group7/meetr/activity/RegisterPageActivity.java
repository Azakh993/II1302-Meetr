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
import com.group7.meetr.viewmodel.RegisterPageViewModel;


public class RegisterPageActivity extends AppCompatActivity {
    private RegisterPageViewModel registerPageViewModel;
    private LiveData<FirebaseUser> firebaseUserLiveData;
    private EditText emailEditText;

    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerPageViewModel = new ViewModelProvider(this).get(RegisterPageViewModel.class);
        setContentView(R.layout.activity_registerpage);

        emailEditText = findViewById(R.id.inEmail);
        passwordEditText = findViewById(R.id.inPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.btn_login);

        registerButtonOnClick(registerButton);
    }


    private void registerButtonOnClick(Button registerButton) {
        registerButton.setOnClickListener(view -> {
            String emailString = emailEditText.getText().toString();
            String passwordString = passwordEditText.getText().toString();
            String confirmString = confirmPasswordEditText.getText().toString();

            if(!confirmString.equals(passwordString)){
                Toast t = new Toast(RegisterPageActivity.this);
                t.setText("Password does not match!");
                t.show();
                return;
            }else{
                firebaseUserLiveData = registerPageViewModel.register(emailString, passwordString);
                firebaseUserLiveData.observe(this, firebaseUser -> switchToJoinOrCreateActivity());
            }
        });
    }

    private void switchToJoinOrCreateActivity() {
        Toast.makeText(getApplicationContext(), "Registration successful!",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterPageActivity.this, LoginPageActivity.class);
        startActivity(intent);
    }
}