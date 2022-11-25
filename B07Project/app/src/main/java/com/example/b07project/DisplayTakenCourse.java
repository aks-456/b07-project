package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DisplayTakenCourse extends AppCompatActivity {
    //this list is going to hold all the taken courses
    // and we then need to send that over to the recyclerview adapter

    String courseCode;
    String courseName;
    //ArrayList<DisplayTakenCourse> takenCourseList = new ArrayList<DisplayTakenCourse>();
    //we can later set an array list for the image

    public DisplayTakenCourse(String courseCode, String courseName) {
        this.courseCode=courseCode;
        this.courseName = courseName;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }
}
