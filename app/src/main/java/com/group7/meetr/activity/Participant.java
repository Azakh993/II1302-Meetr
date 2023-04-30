package com.group7.meetr.activity;

public class Participant {
    String userEmail;

    public Participant() {
    }

    public Participant(String user) {
        this.userEmail = user;
    }

    public String getUser() {
        return this.userEmail;
    }

    public void setUser(String user) {
        this.userEmail = user;
    }

}
