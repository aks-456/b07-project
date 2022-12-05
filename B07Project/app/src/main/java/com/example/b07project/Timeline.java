package com.example.b07project;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Timeline {

    public String course;

    public Timeline() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Timeline(String course) {
        this.course = course;
    }

}
