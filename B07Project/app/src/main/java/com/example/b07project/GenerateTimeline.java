package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;


public class GenerateTimeline extends AppCompatActivity {

    ListView lvTimeline;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_timeline);

        lvTimeline = (ListView)findViewById(R.id.lv_timeline);

        mDatabase = FirebaseDatabase.getInstance().getReference();

//        ArrayList<Object> object = new ArrayList<Object>();
//        Intent intent = new Intent(Current.class, Transfer.class);
//        Bundle args = new Bundle();
//        args.putSerializable("ARRAYLIST",(Serializable)object);
//        intent.putExtra("BUNDLE",args);
//        startActivity(intent);

//        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<String> courses = (ArrayList<String>) args.getSerializable("ARRAYLIST");

        ArrayList<String> courses = new ArrayList<String>();
        courses.add("CSCD01");
        courses.add("CSCB09");
        courses.add("CSCC01");
        courses.add("CSCA48");
        courses.add("CSCB36");

        ArrayList<String> takenCourses = new ArrayList<String>();

        mDatabase.child("students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(GenerateTimeline.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                }
                else {
                    for(DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        //101 -- replace with current user
                        if(key.equals("101")) {

                            String [] taken_arr = ds.child("takenCourse").getValue().toString().split(",");
                            takenCourses.addAll(Arrays.asList(taken_arr));
                        }
                    }
                }
            }
        });

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(GenerateTimeline.this, android.R.layout.simple_list_item_1, courses);
        lvTimeline.setAdapter(coursesAdapter);

//        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(GenerateTimeline.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
//                }
//                else {
//
//                    for (int i = 0; i < courses.size(); i++) {
//                        ArrayList<String> prereqs = new ArrayList<String>();
//                        ArrayList<String> newCourses = new ArrayList<String>();
//                        prereqs.add(courses.get(i));
//                        newCourses.add(courses.get(i));
//
//                        ArrayList<String> timelineCourses = new ArrayList<String>();
//                        Log.e("LENGTH", String.valueOf(getCourses(prereqs, newCourses).size()));
//                        /*for (int x = 0; x < timelineCourses.size(); x++) {
//                            Log.e("TASK" + x, timelineCourses.get(x));
//                        }*/
//                    }
//                }
//            }
//        });
        for (int i = 0; i < courses.size(); i++) {
            ArrayList<String> prereqs = new ArrayList<String>();
            ArrayList<String> newCourses = new ArrayList<String>();
            prereqs.add(courses.get(i));

            ArrayList<String> timelineCourses = new ArrayList<String>();
            //.e("LENGTH", String.valueOf(getCourses(prereqs, newCourses).size()));
            getCourses(prereqs, newCourses);
            /*for (int x = 0; x < timelineCourses.size(); x++) {
                Log.e("TASK" + x, timelineCourses.get(x));
            }*/
        }
    }


    protected void getCourses(ArrayList<String> prereqs, ArrayList<String> newCourses) {

        /*mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else {
                    int bound = prereqs.size();
                    int ctr = 0;
                    while (ctr < bound) {

                        // add prereqs to new courses
                        newCourses.add(prereqs.get(0));

                        for (DataSnapshot ds : task.getResult().getChildren()) {
                            String key = ds.getKey();
//                            if (key.equals(current_course)) {
//
//                                String[] pre_arr = ds.child("prerequisites").getValue().toString().split(",");
//                                prereq_arr.addAll(Arrays.asList(pre_arr));
//
//                            }
                            if (prereqs != null && key.equals(prereqs.get(0))) {
                                String[] pre_arr = ds.child("prerequisites").getValue().toString().split(",");

                                for (int j = 0; j < pre_arr.length; j++) {
                                    prereqs.add(pre_arr[j]);
                                }

                                prereqs.remove(0);
                                break;
                            }
                        }
                        //System.out.println(prereqs.size());
                        ctr++;
                    }
                }
            }
        });*/

        if (prereqs.isEmpty()) {
            // remove duplicate courses
            Set<String> coursesSet = new LinkedHashSet<>(newCourses);
            newCourses.clear();
            newCourses.addAll(coursesSet);

            // write newCourses array list to database

            // prints out one list of necessary courses for each course student wants to take
            for (int x = 0; x < newCourses.size(); x++) {
                Log.e("TASK" + x, newCourses.get(x));
            }
            Log.e("TASK", "---------------");
        }
        else {
            mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                    }
                    else {
                        ArrayList<String> newPrereqs = new ArrayList<String>();

                        for (int i = 0; i < prereqs.size(); i++) {

                            // add prereqs to new courses
                            newCourses.add(prereqs.get(i));

                            // get prereqs of each course in list and replace with their prereqs
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String key = ds.getKey();
                                if (key.equals(prereqs.get(i))) {
                                    String[] pre_arr = ds.child("prerequisites").getValue().toString().split(",");
                                    for (int j = 0; j < pre_arr.length; j++) {
                                        if (!pre_arr[j].isEmpty()){
                                            newPrereqs.add(pre_arr[j].trim());
                                        }
                                    }
                                }
                            }
                        }

                        getCourses(newPrereqs, newCourses);
                    }
                }
            });
        }
    }
}