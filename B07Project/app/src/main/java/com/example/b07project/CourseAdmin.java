package com.example.b07project;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CourseAdmin {

    public String name, code, prerequisites, offerings;


    public CourseAdmin() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public CourseAdmin(String name, String code, String prerequisites, String offerings) {
        this.name = name;
        this.code = code;
        this.prerequisites = prerequisites;
        this.offerings = offerings;
    }


}
