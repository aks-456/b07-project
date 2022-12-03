package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class display_taken_course extends AppCompatActivity implements View.OnClickListener {

    ListView listview;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<Course> list;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_taken_course);

        course = new Course();

        listview = (ListView) findViewById(R.id.takencrslist_view);
        database = FirebaseDatabase.getInstance();

        String userid = "101";
        ref = database.getReference("students").child(userid).child("takenCourse");
        list = new ArrayList<>();
        crsAdapter adapter = new crsAdapter(display_taken_course.this,R.layout.crs_list,list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    course = ds.getValue(Course.class);
                    list.add(course);

                }
                listview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    @Override
    public void onClick(View view) {
        Toast.makeText(display_taken_course.this, "Theremanually", Toast.LENGTH_LONG).show();
    }
}







