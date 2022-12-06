package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class AddtakencrsActivity extends AppCompatActivity {

    EditText editTextcode, editTextsessions;
    Button btnAdd, btnBack, setcrsbtn;
    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference mDatabase;
    DatabaseReference ref2;
    Spinner spinnercrs, spinnerses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtakencrs);

        database = FirebaseDatabase.getInstance();

        FirebaseUser ud = FirebaseAuth.getInstance().getCurrentUser();
        String userid = ud.getUid();
        ref = database.getReference("students").child(userid).child("takenCourse");

        spinnercrs = (Spinner) findViewById(R.id.spinnercrs);
        ArrayList<String> courses = new ArrayList<String>();
        courses.add("Choose course");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AddtakencrsActivity.this, "There are no courses", Toast.LENGTH_LONG).show();
                } else {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        courses.add(key);
                    }
                }
            }
        });

        ArrayAdapter<String> preAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        spinnercrs.setAdapter(preAdapter);

        spinnerses = (Spinner) findViewById(R.id.spinnerses);
        ArrayList<String> sessions = new ArrayList<String>();
        sessions.add("Choose session");
        sessions.add("Fall");
        sessions.add("Winter");
        sessions.add("Summer");
//
//        setcrsbtn = (Button) findViewById(R.id.setcrsbtn);
//        setcrsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDatabase.child("admin_courses").child(spinnercrs.getSelectedItem().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(AddtakencrsActivity.this, "There are no courses", Toast.LENGTH_LONG).show();
//                            sessions.add("failed!");
//                        } else {
//                            for (DataSnapshot ds : task.getResult().getChildren()) {
//                                sessions.add("test!");
//                                if (ds.getKey().toString() == "offerings"){
//                                    String[] temp = ds.getValue().toString().split(",");
//                                    for (int i = 0; i<temp.length; i++){
//                                        sessions.add(temp[i]);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                });
//
//            }
//        });
        ArrayAdapter<String> preAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sessions);
        spinnerses.setAdapter(preAdapter2);

//        editTextcode = (EditText) findViewById(R.id.edTxtCode);
//        editTextsessions = (EditText) findViewById(R.id.edTxtSession);

        btnAdd = (Button) findViewById(R.id.tcbuttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewCrs();
            }
            private void writeNewCrs() {

                String newer = "";
                if(!(spinnercrs.getSelectedItem().toString().equals("Choose course"))) {
                    newer = spinnercrs.getSelectedItem().toString();
                }
                else
                    return;
                String newerses = "";
                if(!(spinnerses.getSelectedItem().toString().equals("Choose session"))) {
                    newerses = spinnerses.getSelectedItem().toString();
                }
                else
                    return;

//                String code_input = editTextcode.getText().toString().toUpperCase();
//                String session_input = editTextsessions.getText().toString().toUpperCase();
//                Course course = new Course(editTextcode.getText().toString(),
//                        editTextsessions.getText().toString());
                Course course = new Course(newer, newerses);
//                String code = editTextcode.getText().toString().toUpperCase();
                ref.child(newer).setValue(course);
                Toast toast = Toast.makeText(AddtakencrsActivity.this, "Course added successfully!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.START, 90, 0);
                toast.show();
            }
        });


        btnBack = (Button) findViewById(R.id.tcbuttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();

            }

            public void switchActivities() {
                Intent switchActivityIntent = new Intent(AddtakencrsActivity.this, DisplayTakenCourseActivity.class);
                startActivity(switchActivityIntent);
            }

        });


    }
}




