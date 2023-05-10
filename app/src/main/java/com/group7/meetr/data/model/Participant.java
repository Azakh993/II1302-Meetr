package com.group7.meetr.data.model;

public class Participant {
    String UUID;
  String name;
    float talkingScore;

    public Participant() {
    }

    public Participant(String UUID, String name) {
        this.UUID = UUID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * A participant clientside for a specific meeting clientside.
     */
    public Participant(String displayName, boolean createdMeeting){
        this.UUID = User.getUid();
        this.name = User.getEmail();
    }
    public Participant(String UUID){
        //TODO: Get info from DB??
    }

    @Override
    public String toString() {
        return "Participant{" +
                "UUID='" + UUID + '\'' +
                ", name='" + name + '\'' +
                ", talkingScore=" + talkingScore +
                '}';
    }
}
