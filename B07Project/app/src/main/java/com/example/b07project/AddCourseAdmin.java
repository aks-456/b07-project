package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCourseAdmin extends AppCompatActivity {

    EditText etCourseName, etCourseCode, etCourseOfferings, etPrerequisites;
    Button btnAddCourse;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etCourseName = findViewById(R.id.etCourseName);
        etCourseCode = findViewById(R.id.etCourseCode);
        etCourseOfferings = findViewById(R.id.etCourseOfferings);
        etPrerequisites = findViewById(R.id.etPrerequisities);
        btnAddCourse = findViewById(R.id.btnAddCourse);


        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCourseName = etCourseName.getText().toString().trim();
                String strCourseCode = etCourseOfferings.getText().toString().trim();
                String strPrerequisites = etPrerequisites.getText().toString().trim();
                String strOfferings = etCourseOfferings.getText().toString().trim();

//                String [] arrPrerequisites = strPrerequisites.split(",");
//                String [] arrOfferings = strOfferings.split(",");



            }
        });


    }


    public void writeNewUser(String courseId, String name, String code, String prerequisites, String offerings) {
        Course course = new Course(name, code, prerequisites, offerings);
        mDatabase.child("courses").child(courseId).setValue(course);

    }




}