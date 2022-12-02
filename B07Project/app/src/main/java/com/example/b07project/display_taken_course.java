package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class display_taken_course extends AppCompatActivity implements View.OnClickListener {
    private Button add;
    private Button del;
    private EditText edTxtCode;
    private TextView view;
    private Model model;
    private String userID;

    private List<Course> courseTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_taken_course);


        userID = getIntent().getStringExtra("key");


        courseTaken = new ArrayList<>();

        model = Model.getInstance();

        edTxtCode = (EditText) findViewById(R.id.edTxtCode);

        add = (Button) findViewById(R.id.tcbuttonAdd);
        add.setOnClickListener(this);

        del = (Button) findViewById(R.id.tcbuttonDel);

        ListView listView = (ListView) findViewById(R.id.takencrslist_view);
        updateListView();


        model.getCourses((List<Course> allCourses) -> {
            //
            this.courseTaken = allCourses;
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tcbuttonAdd:
                addCourse();
                break;
        }
    }

//    //for admin mb
//    public void AdminAddCourse() {
//        System.out.println("adding");
//        // should get all the strings from Edit texts
//
//        // hard code (testing)
//        Course course = new Course();
//        course.code = "CSCA48";
//        course.sessions = "Winter 2023";
//        course.prerequisite.add("CSCA08");
//
//        model.postCourse(course, (Boolean added) -> {
//            if (!added) {
//                Toast.makeText(display_taken_course.this, "failed to add course", Toast.LENGTH_LONG).show();
//                return;
//            }
//            else{
//                Toast.makeText(display_taken_course.this, "added course", Toast.LENGTH_LONG).show();
//            }
//        });
//    }


//reading from firebase
    private void updateListView() {
        model.getCourses((List<Course> allCourses) -> {
            model.getUserById(userID, (User user) -> {
                for (Course course: allCourses) {
                    if (user.coursesTaken.contains(course.code)) {
                        courseTaken.add(course);
                    }
                }
                ListView listView = (ListView)findViewById(R.id.takencrslist_view);
                crsAdapter adapter = new crsAdapter(display_taken_course.this,R.layout.crs_list,allCourses);
                listView.setAdapter(adapter);

            });
        });
    }

    private void addCourse() {
        String code = edTxtCode.getText().toString().trim().toUpperCase();
        model.getCoursesPathByCode(code, (List<Course> path) -> {

            if (path == null) {
                Toast.makeText(display_taken_course.this, "course does not exist", Toast.LENGTH_LONG).show();
                return;
            }

            model.getUserById(userID, (User user) -> {
                Course toAdd = path.get(0);

                if (user.coursesTaken.contains(toAdd.code)) {
                    Toast.makeText(display_taken_course.this, "course already been taken", Toast.LENGTH_LONG).show();
                    return;
                }

                for (int i = 1; i < path.size(); i++) {
                    if (!user.coursesTaken.contains(path.get(i).code)) {
                        Toast.makeText(display_taken_course.this, "missing prerequisite", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                user.coursesTaken.add(toAdd.code);
                model.postUser(userID, user, (Boolean success) -> {
                    if (!success) {
                        Toast.makeText(display_taken_course.this, "failed to add course", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        finish();
                    }

                });
            });
        });

    }
}


