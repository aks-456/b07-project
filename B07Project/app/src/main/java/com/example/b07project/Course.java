package com.example.b07project;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Course {

        public String name;
        public String code;
        public String prerequisites;
        public String offerings;

        public Course() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Course(String name, String code, String prerequisites, String offerings) {
            this.name = name;
            this.code = code;
            this.offerings = offerings;
            this.prerequisites = prerequisites;
        }

}
