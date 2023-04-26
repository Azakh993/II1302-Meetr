package com.group7.meetr.viewmodel;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.BR;
import com.group7.meetr.MainActivity;
import com.group7.meetr.R;
import com.group7.meetr.activity.EmailPasswordActivity;
import com.group7.meetr.activity.LoginPageActivity;
import com.group7.meetr.activity.ModeratorActivity;
import com.group7.meetr.data.model.LoginPageModel;
import com.group7.meetr.data.remote.SessionHandler;

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
    public void onButtonClicked(){
        Log.d("!LOGIN", "LOGIN BUTRTON ");
    }


    /**
     * Checks if user login information is correct. -1 false login
     * 1 is user
     * 3 is admin user.
     * @return
     */
    public int checkLogin() {
        SessionHandler sessionHandler = new SessionHandler(database);
        String userMail = getUserEmail();

        if (isValid()) {
            //Debugging and logging
            Log.d("!User Email", userMail);
            Log.d("!User Pass", getUserPassword());
            EmailPasswordActivity emailLogIn = new EmailPasswordActivity();
            EmailPasswordActivity.login();
            emailLogIn.signIn(getUserEmail(), getUserPassword());

            String successMessage = "Login successful";
            setToastMessage(successMessage);
            if(userMail.contains("admin@admin.com")) {
                sessionHandler.createSession(userMail);
                return 3; // returns admin "key"
            }
            else {
                sessionHandler.joinSession(userMail);
            }
            return 1;
        }
        else {
            String errorMessage = "Email or Password is not valid";
            setToastMessage(errorMessage);
            return -1;
        }




    }


    public boolean isValid() {
        return !TextUtils.isEmpty(getUserEmail()) && Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches()
                && getUserPassword().length() > 5;
    }
}