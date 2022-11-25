package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Display_taken_course extends AppCompatActivity {

    ArrayList<DisplayTakenCourse> takenCourseList = new ArrayList<DisplayTakenCourse>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_taken_course);

        RecyclerView recyclerView = findViewById(R.id.TakenCourseRecyclerView);
        setTakenCourseList();
        DTC_RecyclerViewAdapter dtc_recycler_adapter = new DTC_RecyclerViewAdapter(this,
                takenCourseList);
        recyclerView.setAdapter(dtc_recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setTakenCourseList(){
        String[] takenCourseCode = getResources().getStringArray(R.array.taken_course_code_test);
        String[] takenCourseName = getResources().getStringArray(R.array.taken_course_name_test);

        for(int i =0; i<takenCourseCode.length;i++){
            takenCourseList.add(new DisplayTakenCourse(takenCourseCode[i],takenCourseName[i]));
        }
    }
}