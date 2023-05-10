package com.group7.meetr.data.remote;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group7.meetr.data.model.User;

public class AuthenticationHandler {
    private final String TAG = "EmailPassword";
    private final Application application;
    private final FirebaseAuth firebaseAuth;

    public AuthenticationHandler(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        if (firebaseAuth.getCurrentUser() != null) {
            User.setUserInfo(firebaseAuth.getCurrentUser());
        }
    }

    public void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                        Log.d(TAG, "signInWithEmail:success");
                        User.setUserInfo(firebaseAuth.getCurrentUser());
                    } else {
                        if(!isValid(email, password)) {
                            Toast.makeText(application.getApplicationContext(), "Incorrect credentials format!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Authentication failure!",
                                    Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public boolean isValid(String email, String password) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length() > 5;
    }

    public void createAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                        Log.d(TAG, "createUserWithEmail:success");
                        User.setUserInfo(firebaseAuth.getCurrentUser());
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(application.getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    // Add code here
                });
    }
}
