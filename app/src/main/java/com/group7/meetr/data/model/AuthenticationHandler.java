package com.group7.meetr.data.model;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationHandler {
    private final String TAG = "EmailPassword";
    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public AuthenticationHandler(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            this.currentUser = firebaseAuth.getCurrentUser();
        }
    }

    public FirebaseUser signIn(String email, String password) {
        firebaseAuth.signOut();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        currentUser = firebaseAuth.getCurrentUser();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                });
        return currentUser;
    }

    public void createAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        currentUser = firebaseAuth.getCurrentUser();
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
