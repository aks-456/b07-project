package com.example.b07project;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@IgnoreExtraProperties
public class Course{


        public String code;
        public String sessions;




    public Course() {

    }

        public Course(String name, String code, String prerequisites, String offerings) {

            this.code = code;
            this.sessions = sessions;

        }


    public String getCode() {
        return code;
    }

    public String getSessions() {
        return sessions;
    }
}