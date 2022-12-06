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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class GenerateTimeline extends AppCompatActivity {

    ListView lvTimeline;
    private DatabaseReference mDatabase;
    ArrayList<String> futureCourses = new ArrayList<String>();
    String is_empty = "h";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_generate_timeline);

            ArrayList<String> courses = new ArrayList<String>();
            courses.add("CSCD01");

            mDatabase = FirebaseDatabase.getInstance().getReference();
            lvTimeline = (ListView)findViewById(R.id.lv_timeline);

            ArrayList<String> prereq_arr = new ArrayList<String>();
            ArrayList<String> taken_courses = new ArrayList<String>();

            ArrayList<String> future_courses_display = new ArrayList<String>();


            HashMap<String, String> sessionOfferings = new HashMap<String, String>();

            String uid = "8x12b03lV6QJGDlkXzlBquuiY753";
            ArrayList<String> takenCourses = new ArrayList<String>();
            takenCourses.add("CSCB07");

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

            mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                    } else {
                        for (int i = 0; i < courses.size(); i++) {
                            ArrayList<String> prereqs = new ArrayList<String>();
                            ArrayList<String> newCourses = new ArrayList<String>();
                            prereqs.add(courses.get(i));

                            ArrayList<String> timelineCourses = new ArrayList<String>();
                            //.e("LENGTH", String.valueOf(getCourses(prereqs, newCourses).size()));
                            getCourses(prereqs, newCourses, task, takenCourses);
                        }

                        Log.e("TAG10", "yes");


                        for (int i = 0; i < futureCourses.size(); i++) {

                            int year = 2023;

                            //        mDatabase.child("students").child(uid).child("taken_courses").get().


                            ArrayList<String> sessions = new ArrayList<String>();
                            String course_rn = futureCourses.get(i);
                            mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
//
                                        for(DataSnapshot ds: task.getResult().getChildren()) {
                                            if(ds.getKey().equals(course_rn)) {
                                                Log.e("TAGS", ds.child("offerings").getValue().toString());
                                                sessions.addAll(Arrays.asList(ds.child("offerings").getValue().toString().split(",")));
                                            }
                                        }
                                    }
                                }
                            });

                           // String[] sessions = mDatabase.child("admin_courses").child(futureCourses.get(i)).child("offerings").get().getResult().getValue().toString().split(",");

                            int[] session_nums = new int[sessions.size()];

                           // Log.e("ADDEDSTUFF", mDatabase.child("admin_courses").get().getResult().child(futureCourses.get(i)).child("prerequisites").getValue().toString());


                            ArrayList<String> prereq_str =new ArrayList<String>();
                            String str = "";
                            mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
//
                                        for(DataSnapshot ds: task.getResult().getChildren()) {
                                            if(ds.getKey().equals(course_rn)) {
                                                Log.e("TAGS", ds.child("prerequisites").getValue().toString());
                                                is_empty = ds.child("prerequisites").getValue().toString();
                                            }
                                        }
                                    }
                                }
                            });


                            if (is_empty.equals("")) {

                                for (int j = 0; j < sessions.size(); j++) {
                                    if (sessions.get(j).equals("Winter")) {
                                        session_nums[j] = 1;
                                        Log.e("SessionNum", String.valueOf(session_nums[j]));
                                    } else if (sessions.get(j).equals("Summer")) {
                                        Log.e("SessionNum", String.valueOf(session_nums[j]));
                                        session_nums[j] = 2;
                                    } else if (sessions.get(j).equals("Fall")) {
                                        Log.e("SessionNum", String.valueOf(session_nums[j]));

                                        session_nums[j]= 3;
                                    }
                                }

                                int min_val = 4;
                                int min_index = -1;
                                //Get lowest value for session:
                                for (int j = 0; j < session_nums.length; j++) {
                                    if (session_nums[j] < min_val) {
                                        min_index = j;
                                    }
                                }
                                sessionOfferings.put(futureCourses.get(i), sessions.get(min_index) + " " + Integer.toString(2023));

                            } else {

                                //If course does have prerequisites, then check for the date of each prereq and see if all of them are schedule, if so then get the latest date of all 3.
                                ArrayList<String> pre_arr = new ArrayList<String>();
                                mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
//
                                            for(DataSnapshot ds: task.getResult().getChildren()) {
                                                if(ds.getKey().equals(course_rn)) {
                                                    Log.e("TAGS", ds.child("prerequisites").getValue().toString());
                                                    pre_arr.addAll( Arrays.asList(ds.child("prerequisites").getValue().toString().split(",")));
                                                }
                                            }
                                        }
                                    }
                                });



                                ArrayList<String> scheduled_prereq = new ArrayList<String>();
                                for (String s : pre_arr) {
                                    if (sessionOfferings.containsKey(s)) {
                                        scheduled_prereq.add(s);
                                    }
                                }

                                if (scheduled_prereq.size() == pre_arr.size()) {
                                    //Get largest year
                                    int largest_year = year;
                                    int counter = 0;
                                    for (int j = 0; j < scheduled_prereq.size(); j++) {
                                        String[] session_schedule = sessionOfferings.get(scheduled_prereq.get(j)).split(" ");
                                        int current_year = Integer.parseInt(session_schedule[1]);
                                        if (current_year >= largest_year) {
                                            largest_year = current_year;
                                            counter++;
                                        }
                                    }

                                    int largest_session = 1;
                                    for (int j = 0; j < scheduled_prereq.size(); j++) {
                                        String[] session_schedule = sessionOfferings.get(scheduled_prereq.get(j)).split(" ");
                                        int current_year = Integer.parseInt(session_schedule[1]);

                                        largest_year = current_year;
                                            if (sessions.get(j).equals("Winter")) {
                                                largest_session = 1;
                                            } else if (sessions.get(j).equals("Summer")) {
                                                if (2 > largest_session) {
                                                    largest_session = 2;
                                                }
                                            } else if (sessions.get(j).equals("Fall")) {
                                                if (3 > largest_session) {
                                                    largest_session = 3;
                                                }
                                            }
                                    }

                                    //Get largest session in largest year

                                    //Assign to sessionOfferings
                                    year = largest_year;
                                    if (largest_session == 3) {
                                        sessionOfferings.put(futureCourses.get(i), "Winter " + Integer.toString(largest_year + 1));
                                    } else if (largest_session == 2) {
                                        sessionOfferings.put(futureCourses.get(i), "Fall " + Integer.toString(largest_year));
                                    } else {
                                        sessionOfferings.put(futureCourses.get(i), "Summer " + Integer.toString(largest_year));
                                    }

                                } else {  //If it has a prerequisite that is not scheduled, we can use the fact that no taken courses are on the list, hence we have to swap this with its prerequisite (that we have not taken) in the course.
                                    for (String s : pre_arr) {
                                        if (!(scheduled_prereq.contains(s))) {
                                            int element = futureCourses.indexOf(s);
                                            Collections.swap(futureCourses, element, i);
                                            break;
                                        }
                                    }

                                    i = i - 1;
                                }

                            }

                        }


                        //Delete all of timeline_generation
                        ArrayList<String> session_vals = new ArrayList<String>();
                        //Populate listview with sessionOfferings;
                        for (String x : sessionOfferings.values()) {
                            if (session_vals.contains(x)) {
                                continue;
                            }

                            session_vals.add(x);

                            String new_str = "";
                            for (String y : sessionOfferings.keySet()) {
                                // Log.e("CHECKSTRING", y);
                                if (sessionOfferings.get(y).equals(x)) {
                                    new_str += y + ", "; //remove comma at end
                                }
                            }

                            new_str = new_str.substring(0, new_str.length() - 1);

                            future_courses_display.add(x + ": " + new_str);


                            //mytextview.setText(Html.fromHtml(sourceString));
                        }


                        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(GenerateTimeline.this, android.R.layout.simple_list_item_1, future_courses_display);

                        lvTimeline.setAdapter(coursesAdapter);

                    }
                }
            });
        }






                protected void getCourses(ArrayList<String> prereqs, ArrayList<String> newCourses, Task<DataSnapshot> coursesTask, ArrayList<String> takenCourses) {

                    if (prereqs.isEmpty()) {
                        // remove duplicate courses
                        Set<String> coursesSet = new LinkedHashSet<>(newCourses);
                        newCourses.clear();
                        newCourses.addAll(coursesSet);

                        // write newCourses array list to database

                        // prints out one list of necessary courses for each course student wants to take
                        for (int x = 0; x < newCourses.size(); x++) {
                            Log.e("TASK" + x, newCourses.get(x));
                            if(!(futureCourses.contains(newCourses.get(x))) && !(takenCourses.contains(newCourses.get(x)))) {
                                futureCourses.add(newCourses.get(x));
                            }
                        }
                        Log.e("TASK", "---------------");
                    } else {

                        ArrayList<String> newPrereqs = new ArrayList<String>();

                        for (int i = 0; i < prereqs.size(); i++) {

                            // add prereqs to new courses
                            newCourses.add(prereqs.get(i));

                            for (DataSnapshot ds : coursesTask.getResult().getChildren()) {
                                String key = ds.getKey();
                                if (key.equals(prereqs.get(i))) {
                                    String[] pre_arr = ds.child("prerequisites").getValue().toString().split(",");
                                    for (int j = 0; j < pre_arr.length; j++) {
                                        if (!pre_arr[j].isEmpty()) {
                                            newPrereqs.add(pre_arr[j].trim());
                                        }
                                    }
                                }
                            }
                        }

                        getCourses(newPrereqs, newCourses, coursesTask, takenCourses);
                    }
                }


    }



