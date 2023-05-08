package com.group7.meetr.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group7.meetr.data.model.AuthenticationHandler;

public class LoginPageViewModel extends AndroidViewModel {
    private static AuthenticationHandler authenticationHandler;
    private FirebaseUser currentUser;


    public LoginPageViewModel(Application application) {
        super(application);
        authenticationHandler = new AuthenticationHandler(application);
    }
    public static FirebaseUser getCurrentUser(){
        return authenticationHandler.getCurrentUser();
    }


    /**
     * Checks if user login information is correct. -1 false login
     * 1 is user
     * 3 is admin user.
     *
     * @return
     */
    public FirebaseUser login(String email, String password) {
        currentUser = authenticationHandler.signIn(email, password);

        return currentUser;
    }
}
