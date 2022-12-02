package com.example.b07project;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@IgnoreExtraProperties
public class Course implements Serializable{

        public String name;
        public String code;
        public String prerequisites;
        public String offerings;

        
        public Course() {
            
        }


        public Course(String name, String code, String prerequisites, String offerings) {
            this.name = name;
            this.code = code;
            this.offerings = offerings;
            this.prerequisites = prerequisites;
        }


    }