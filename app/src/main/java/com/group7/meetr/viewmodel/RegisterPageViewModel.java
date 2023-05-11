package com.group7.meetr.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.group7.meetr.data.model.User;
import com.group7.meetr.data.remote.AuthenticationHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterPageViewModel extends AndroidViewModel {
    private final AuthenticationHandler authenticationHandler;
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();
    private FirebaseUser user;


    public RegisterPageViewModel(Application application) {
        super(application);
        authenticationHandler = new AuthenticationHandler(application);
        firebaseUserObserver();
    }

    private void firebaseUserObserver() {
        User.observeFirebaseUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setUserLiveData);
    }

    private void setUserLiveData(FirebaseUser firebaseUser) {
        setFirebaseUser(firebaseUser);
        firebaseUserMutableLiveData.setValue(user);
    }

    private void setFirebaseUser(FirebaseUser firebaseUser) {
        user = firebaseUser;
    }

    public LiveData<FirebaseUser> register(String email, String password) {
        authenticationHandler.createAccount(email,password);
        return firebaseUserMutableLiveData;
    }
}
