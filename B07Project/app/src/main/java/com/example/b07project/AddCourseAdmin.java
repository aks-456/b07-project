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

        etCourseName = new EditText(this);
        etCourseCode = new EditText(this);
        etCourseOfferings = new EditText(this);
        etPrerequisites = new EditText(this);
        btnAddCourse = new Button(this);


        etCourseName.setId(R.id.etCourseName);
        etCourseCode.setId(R.id.etCourseCode);
        etCourseOfferings.setId(R.id.etCourseOfferings);
        etPrerequisites.setId(R.id.etPrerequisities);
        btnAddCourse.setId(R.id.btnAddCourse);


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