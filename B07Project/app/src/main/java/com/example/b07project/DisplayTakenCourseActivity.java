package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class DisplayTakenCourseActivity extends AppCompatActivity {

    ListView listview;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<Course> list;
    Course course;
    Button btnAdd;
    Button btnBac;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_taken_course);

        course = new Course();

        listview = (ListView) findViewById(R.id.takencrslist_view);
        database = FirebaseDatabase.getInstance();
        FirebaseUser ud = FirebaseAuth.getInstance().getCurrentUser();
        String userid = ud.getUid();



<<<<<<< Updated upstream
        ref = database.getReference("students").child(userid).child("taken_courses");
=======

        FirebaseUser ud = FirebaseAuth.getInstance().getCurrentUser();
        String userid = ud.getUid();
        ref = database.getReference("students").child(userid).child("takenCourse");
>>>>>>> Stashed changes
        list = new ArrayList<>();
        crsAdapter adapter = new crsAdapter(DisplayTakenCourseActivity.this, R.layout.crs_list, list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    course = ds.getValue(Course.class);
                    list.add(course);

                }
                listview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        btnAdd = (Button) findViewById(R.id.tcbuttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                switchActivities();

            }


            public void switchActivities() {
                Intent switchActivityIntent = new Intent(DisplayTakenCourseActivity.this, AddtakencrsActivity.class);
                startActivity(switchActivityIntent);
            }
        });

        btnBac = (Button) findViewById(R.id.tcbuttonBack);
        btnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivitiestodash();

            }

            //TODO: change activity for switching under -> student home page;

            public void switchActivitiestodash() {
                Intent switchActivityIntent = new Intent(DisplayTakenCourseActivity.this, AddtakencrsActivity.class);
                startActivity(switchActivityIntent);
            }
        });




    }
}











