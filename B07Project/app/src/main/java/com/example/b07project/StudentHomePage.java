package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StudentHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        Button stulogout = findViewById(R.id.studlogout);
        stulogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentHomePage.this, login_activity.class));
            }
        });

        Button stutakencrs = findViewById(R.id.takencrsbtn);
        stutakencrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentHomePage.this, DisplayTakenCourseActivity.class));
            }
        });

        Button stufutcrs = findViewById(R.id.futurecrsbtn);
        stufutcrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentHomePage.this, GenerateCourseStudent.class));
            }
        });
    }
}