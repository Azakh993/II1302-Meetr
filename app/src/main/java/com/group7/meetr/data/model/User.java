package com.group7.meetr.data.model;

import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class User {
    private final static PublishSubject<FirebaseUser> firebaseUserSubject = PublishSubject.create();

    private static FirebaseUser currentFirebaseUser;
    private static String email = null;
    private static String uid = null;


    public static void setUserInfo(FirebaseUser currentUser) {
        currentFirebaseUser = currentUser;
        firebaseUserSubject.onNext(currentUser);
        email = currentUser.getEmail();
        uid = currentUser.getUid();
    }

    public User() {

    }

    public static Observable<FirebaseUser> observeFirebaseUser() {
        return firebaseUserSubject;
    }

    public static FirebaseUser getCurrentFirebaseUser() {
        return currentFirebaseUser;
    }

    public static String getEmail() {
        return email;
    }

    public static String getUid() {
        return uid;
    }

}