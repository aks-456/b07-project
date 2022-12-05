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
<<<<<<< Updated upstream
=======
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.primitives.Ints;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.StringUtils;
>>>>>>> Stashed changes
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
<<<<<<< Updated upstream
import java.util.List;
=======
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

>>>>>>> Stashed changes

public class GenerateTimeline extends AppCompatActivity {

    ListView lvTimeline;
    private DatabaseReference mDatabase;
    ArrayList<String> futureCourses = new ArrayList<String>();

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

<<<<<<< Updated upstream
        ArrayList<String> prereq_arr = new ArrayList<String>();
        ArrayList<String> taken_courses = new ArrayList<String>();
=======

        ArrayList<String> future_courses_display = new ArrayList<String>();

        ArrayList<String> takenCourses = new ArrayList<String>();
>>>>>>> Stashed changes

        HashMap<String, String> sessionOfferings = new HashMap<String, String>();

        String uid = "101";
        mDatabase.child("students").child(uid).child("taken_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(GenerateTimeline.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                } else {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        //101 -- replace with current user
                        //ArrayList<String> taken_arr = new ArrayList<String>();
                        takenCourses.add(ds.getKey().toString());
                        //Log.e("TAKENCOURSE", ds.getKey());
//                        if (key.equals("101")) {
//                            mDatabase.child("students").child(key).child("taken_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                    for (DataSnapshot ds : task.getResult().getChildren()) {
//                                        taken_arr.add(ds.getKey());
//                                        Log.e("TAKENCOURSE", ds.getKey());
//                                    }
//
//
//                                }

<<<<<<< Updated upstream
                            String [] taken_arr = ds.child("taken_courses").getValue().toString().split(",");
                            taken_courses.addAll(Arrays.asList(taken_arr));

                            for(int j = 0; j < taken_courses.size(); j++) {
                                Log.e("RANDOM", taken_courses.get(j));
=======
//                                String[] taken_arr = ds.getKey().toString().split(",");

//                            System.out.println("TAKENCOURSE "+taken_arr[0]);
//                            takenCourses.addAll(Arrays.asList(taken_arr));

//                            });
                        }
                    }
                }
//            }
        });


        //Write list of courses needed to take
        for (int i = 0; i < courses.size(); i++) {
            ArrayList<String> prereqs = new ArrayList<String>();
            ArrayList<String> newCourses = new ArrayList<String>();

            prereqs.add(courses.get(i));

            ArrayList<String> timelineCourses = new ArrayList<String>();
            getCourses(prereqs, newCourses, takenCourses);

        }
        Log.e("END", "This is the end");


        //Get list of courses needed in array adapter
        //ArrayList<String> futureCourses = new ArrayList<String>();

//            mDatabase.child("timeline_generation").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//                        Log.e("HELLO", "Error getting data");
//                    } else {
//                        Log.e("END", "This is the end");
//
//                        for (DataSnapshot ds : task.getResult().getChildren()) {
//                            futureCourses.add(ds.getKey().toString());
//                            Log.e("RANDOM", ds.getKey().toString());
//                        }
//                    }
//                }
//            });


        for(int i = 0; i < futureCourses.size(); i++) {

            int year = 2023;

            //        mDatabase.child("students").child(uid).child("taken_courses").get().

            String [] sessions = mDatabase.child("admin_courses").child(futureCourses.get(i)).child("offerings").get().getResult().getValue().toString().split(",");
            Log.e("CHECKSTRING", "hello");
            int [] session_nums = new int[sessions.length];

            Log.e("ADDEDSTUFF", mDatabase.child("admin_courses").get().getResult().child(futureCourses.get(i)).child("prerequisites").getValue().toString());


            if(mDatabase.child("admin_courses").get().getResult().child(futureCourses.get(i)).child("prerequisites").getValue().toString().equals("")) {

                for(int j = 0; j < sessions.length; j++) {
                    if(sessions[i].equals("Winter")) {
                        session_nums[i] = 1;
                        Log.e("SessionNum", String.valueOf(session_nums[i]));
                    } else if(sessions[i].equals("Summer")) {
                        Log.e("SessionNum", String.valueOf(session_nums[i]));
                        session_nums[i] = 2;
                    } else if(sessions[i].equals("Fall")) {
                        Log.e("SessionNum", String.valueOf(session_nums[i]));

                        session_nums[i] = 3;
                    }
                }

                int min_val = 4;
                int min_index = -1;
                //Get lowest value for session:
                for(int j = 0; j < session_nums.length; j++) {
                    if(session_nums[j] < min_val) {
                        min_index = j;
                    }
                }
                sessionOfferings.put(futureCourses.get(i), sessions[min_index] + " " + Integer.toString(year));

            } else {

                //If course does have prerequisites, then check for the date of each prereq and see if all of them are schedule, if so then get the latest date of all 3.
                String [] pre_arr = mDatabase.child("admin_courses").get().getResult().child(futureCourses.get(i)).child("prerequisites").getValue().toString().split("");
                ArrayList<String> scheduled_prereq = new ArrayList<String>();
                for (String s : pre_arr) {
                    if (sessionOfferings.containsKey(s)) {
                        scheduled_prereq.add(s);
                    }
                }

                if(scheduled_prereq.size() == pre_arr.length) {
                    //Get largest year
                    int largest_year = 2023;
                    int counter = 0;
                    for(int j = 0; j<scheduled_prereq.size(); j++) {
                        String [] session_schedule = sessionOfferings.get(scheduled_prereq.get(j)).split(" ");
                        int current_year = Integer.parseInt(session_schedule[1]);
                        if(largest_year >= current_year) {
                            largest_year = current_year;
                            counter++;
                        }
                    }

                    int largest_session = 1;
                    for(int j = 0; j<scheduled_prereq.size(); j++) {
                        String [] session_schedule = sessionOfferings.get(scheduled_prereq.get(j)).split(" ");
                        int current_year = Integer.parseInt(session_schedule[1]);

                        if(current_year == largest_year) {
                            if(sessions[j].equals("Winter")) {
                            } else if(sessions[i].equals("Summer")) {
                                if(2 > largest_session) {
                                    largest_session = 2;
                                }
                            } else if(sessions[i].equals("Fall")) {
                                if(3 > largest_session) {
                                    largest_session = 3;
                                }
>>>>>>> Stashed changes
                            }

                        }
                    }

<<<<<<< Updated upstream
                }
            }
        });
=======
                    //Get largest session in largest year

                    //Assign to sessionOfferings
                    if(largest_session == 3) {
                        sessionOfferings.put(futureCourses.get(i), "Winter " + Integer.toString(largest_year+1));
                    } else if(largest_session == 2) {
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

                    i = i-1;
                }

            }

        }


        //Delete all of timeline_generation
        mDatabase.child("timeline_generation").removeValue();
        ArrayList<String> session_vals = new ArrayList<String>();
        //Populate listview with sessionOfferings;
        for(String x: sessionOfferings.values()) {
            if(session_vals.contains(x)) {
                continue;
            }

            session_vals.add(x);
            String new_str = "";
            for(String y: sessionOfferings.keySet()) {
               // Log.e("CHECKSTRING", y);
                if(sessionOfferings.get(y).equals(x)) {
                    new_str += y + ", "; //remove comma at end
                }
            }

            new_str = new_str.substring(0, new_str.length()-1);

            future_courses_display.add(x + "   |   " + new_str);
        }


        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(GenerateTimeline.this, android.R.layout.simple_list_item_1, future_courses_display);

        lvTimeline.setAdapter(coursesAdapter);

        //Log.e("AddedStuff", (sessionOfferings.get("CSCA48")));

    }

    public void writeNewCourse(String course) {
        Timeline timeline = new Timeline(course);

        mDatabase.child("timeline_generation").child(course).setValue(timeline);
    }



    protected void getCourses(ArrayList<String> prereqs, ArrayList<String> newCourses, ArrayList<String> takenCourses) {

>>>>>>> Stashed changes


        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(GenerateTimeline.this, android.R.layout.simple_list_item_1, courses);
        lvTimeline.setAdapter(coursesAdapter);

<<<<<<< Updated upstream
=======
            // prints out one list of necessary courses for each course student wants to take
            for (int x = 0; x < newCourses.size(); x++) {
              //  Log.e("TASK" + x, newCourses.get(x));


                //Don't add if they've already taken it
                if(!(takenCourses.contains(newCourses.get(x)))) {
                    takenCourses.add(newCourses.get(x));
                }

            }
            //Log.e("TASK", "---------------");
        }
        else {
>>>>>>> Stashed changes
            mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
<<<<<<< Updated upstream
                        Toast.makeText(GenerateTimeline.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                    } else {
                        for (DataSnapshot ds : task.getResult().getChildren()) {
                            String key = ds.getKey();
                            if (key.equals(current_course)) {

                                String[] pre_arr = ds.child("prerequisites").getValue().toString().split(",");
                                prereq_arr.addAll(Arrays.asList(pre_arr));

                            }
                        }

=======
                        Log.e("ERROR", "ERROR");
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

                        getCourses(newPrereqs, newCourses, takenCourses);
>>>>>>> Stashed changes
                    }
                }
            });
    }
}