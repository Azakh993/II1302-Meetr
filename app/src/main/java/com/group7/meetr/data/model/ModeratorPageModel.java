package com.group7.meetr.data.model;
import androidx.annotation.Nullable;

public class ModeratorPageModel {
    @Nullable
    public String getMeeting_name() {
        return meeting_name;
    }

    public void setMeeting_name(@Nullable String meeting_name) {
        this.meeting_name = meeting_name;
    }

    @Nullable
    String meeting_name;

    // constructor to initialize
    // the variables
    public ModeratorPageModel(String meeting_name) {
        meeting_name = meeting_name;

    }
}