package com.example.b07project;

<<<<<<< Updated upstream
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
=======

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class Display_taken_course extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ListView listView;
        ArrayList<String> arr = new ArrayList<>();
        DatabaseReference database;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_taken_course);

        listView = (ListView)findViewById(R.id.takencrslist_view);

        ArrayAdapter<String> arr2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        database = FirebaseDatabase.getInstance().getReference("test");

        listView.setAdapter(arr2);






//        Intent intent = getIntent();
//        // getStringExtra?
//        String ccode = intent.getStringExtra("aaa");
//
//        TextView crsName = (TextView) findViewById(R.id.dtc_title);
//        crsName.setText(ccode);

//        String [] crs = {"Brian", "Nick", "Vincent", "Anya"};
//        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,crs);
//        ListView listView = (ListView)findViewById(R.id.takencrslist_view);
//        listView.setAdapter(userAdapter);



    }
}



//    //ArrayList<DisplayTakenCourse> takenCourseList = new ArrayList<DisplayTakenCourse>();
//    RecyclerView recyclerView;
//    DatabaseReference database;
//    DTC_RecyclerViewAdapter dtcadapter;
//    ArrayList<TakenCourse> takenCourseslist;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_taken_course);
//
//        recyclerView = findViewById(R.id.TakenCourseRecyclerView);
//        //double check later on
//        database = FirebaseDatabase.getInstance().getReference("Student");
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        takenCourseslist = new ArrayList<>();
//        dtcadapter = new DTC_RecyclerViewAdapter(this,takenCourseslist);
//        recyclerView.setAdapter(dtcadapter);
//
//
//
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_taken_course);
//
//        RecyclerView recyclerView = findViewById(R.id.TakenCourseRecyclerView);
//        setTakenCourseList();
//        DTC_RecyclerViewAdapter dtc_recycler_adapter = new DTC_RecyclerViewAdapter(this,
//                takenCourseList);
//        recyclerView.setAdapter(dtc_recycler_adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
//    private void setTakenCourseList(){
//        String[] takenCourseCode = getResources().getStringArray(R.array.taken_course_code_test);
//        String[] takenCourseName = getResources().getStringArray(R.array.taken_course_name_test);
//
//        for(int i =0; i<takenCourseCode.length;i++){
//            takenCourseList.add(new DisplayTakenCourse(takenCourseCode[i],takenCourseName[i]));
//        }
//    }
//
>>>>>>> Stashed changes
