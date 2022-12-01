package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddCourseAdmin extends AppCompatActivity {

    EditText etCourseName, etCourseCode, etCourseOfferings, etPrerequisites;
    Button btnAddCourse, btnAddPre;
    Spinner spPre;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etCourseName = (EditText)findViewById(R.id.etCourseName);
        etCourseCode = (EditText)findViewById(R.id.etCourseCode);
        etCourseOfferings = (EditText)findViewById(R.id.etCourseOfferings);
        etPrerequisites = (EditText)findViewById(R.id.etPrerequisities);
        btnAddCourse = (Button)findViewById(R.id.btnAddCourse);
        btnAddPre = (Button)findViewById(R.id.btnAddPre);
        spPre = (Spinner)findViewById(R.id.spPre);


        //Get current signed in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "";
        if (user != null) {

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();
        }

        //Populate array for Prerequisites Dropdown
        ArrayList<String> preItems=new ArrayList<String>();
        preItems.add("Choose Prerequisite(s)");

        //Get list of courses from firebase -- from admin id
        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AddCourseAdmin.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                }
                else {
                    for(DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        preItems.add(key);
                    }

                }
            }
        });

        // use default spinner item to show options in spinner
        ArrayAdapter<String> preAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, preItems);
        spPre.setAdapter(preAdapter);

        btnAddPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = etPrerequisites.getText().toString().trim();
                String newer = "";
                if(!(spPre.getSelectedItem().toString().equals("Choose Prerequisite(s)"))) {
                    if(current.equals("")) {
                        newer = current+ spPre.getSelectedItem().toString();
                    } else {
                        newer = current + ", " + spPre.getSelectedItem().toString();
                    }
                    etPrerequisites.setText(newer);

                }
            }
        });


        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCourseName = etCourseName.getText().toString().trim();
                String strCourseCode = etCourseCode.getText().toString().trim();
                String strPrerequisites = etPrerequisites.getText().toString().trim();
                String strOfferings = etCourseOfferings.getText().toString().trim();

                //Get current signed in user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = "";
                if (user != null) {

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    uid = user.getUid();
                }

                //Change to uid
                writeNewCourse(strCourseName, strCourseCode, strPrerequisites, strOfferings);

            }
        });


    }

    public void writeNewCourse(String name, String code, String prerequisites, String offerings) {
        //Check if the course code already exists
        Course course = new Course(name, code, prerequisites, offerings);
        mDatabase.child("admin_courses").child(code).setValue(course);
    }


}