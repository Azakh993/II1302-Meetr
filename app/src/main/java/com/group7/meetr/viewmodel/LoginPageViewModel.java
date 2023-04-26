package com.group7.meetr.viewmodel;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.BR;
import com.group7.meetr.ParticipantView;
import com.group7.meetr.activity.EmailPasswordActivity;
import com.group7.meetr.data.model.LoginPageModel;
import com.group7.meetr.data.remote.SessionHandler;

import com.group7.meetr.MainActivity;

public class LoginPageViewModel extends BaseObservable {

    // creating object of Model class
    private final LoginPageModel model;
    private final FirebaseDatabase database;

    @Bindable
    private String toastMessage = null;


    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }


    @Bindable
    public String getUserEmail() {
        return model.getEmail();
    }

    public void setUserEmail(String email) {
        model.setEmail(email);
        notifyPropertyChanged(BR.userEmail);
    }


    @Bindable
    public String getUserPassword() {
        return model.getPassword();
    }

    public void setUserPassword(String password) {
        model.setPassword(password);
        notifyPropertyChanged(BR.userPassword);
    }


    public LoginPageViewModel(FirebaseDatabase database) {

        // instantiating object of
        // model class
        model = new LoginPageModel("","");
        this.database = database;
    }



    public void onButtonClicked() {
        if (isValid()) {

            Log.d("!User Email", getUserEmail());
            Log.d("!User Pass", getUserPassword());
            EmailPasswordActivity emailLogIn = new EmailPasswordActivity();
            EmailPasswordActivity.login();
            emailLogIn.signIn(getUserEmail(), getUserPassword());

            String successMessage = "Login successful";
            setToastMessage(successMessage);

        }
        else {
            String errorMessage = "Email or Password is not valid";
            setToastMessage(errorMessage);
        }

        String userMail = getUserEmail();
        SessionHandler sessionHandler = new SessionHandler(database);

        if(userMail.contains("admin@admin.com")) {
            sessionHandler.createSession(userMail);
        } else {
            sessionHandler.joinSession(userMail);
        }
    }


    public boolean isValid() {
        return !TextUtils.isEmpty(getUserEmail()) && Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches()
                && getUserPassword().length() > 5;
    }
}