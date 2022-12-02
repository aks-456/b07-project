package com.example.b07project;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class Model {
    private static Model instance;

    private DatabaseReference coursesRef;
    private DatabaseReference usersRef;

    private Model() {
        coursesRef = FirebaseDatabase.getInstance().getReference("courses");
        usersRef = FirebaseDatabase.getInstance().getReference("users");
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    // Used to create/edit a User Object to firebase
    public void postUser(String userID, User user, Consumer<Boolean> callback) {
        coursesRef.child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    // Used to create/edit a Course Object to firebase
    public void postCourse(Course course, Consumer<Boolean> callback) {
        coursesRef.child(course.code).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void getCourses(Consumer<List<Course>> callback) {
        coursesRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Course> allCourses = new ArrayList<>();
                for (DataSnapshot courseSnapshot: snapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    allCourses.add(course);
                }
                callback.accept(allCourses);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void getUserById(String userID, Consumer<User> callback) {
        usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                callback.accept(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // THIS CAN BE USED TO GENERATE (Table1 & Table2)
    // For example: if code == "CSCC24"
    // return -> List {Course("C24"), Course("B07"), Course("B09"), Course("A48"), Course("A08")
    public void getCoursesPathByCode(String code, Consumer<List<Course>> callback) {
        coursesRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Course> allCourses = new HashMap<>();
                boolean exist = false;
                for (DataSnapshot courseSnapshot: snapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    allCourses.put(course.code, course);
                    if (course.code.equalsIgnoreCase(code)) exist = true;
                }

                if (!exist) {
                    callback.accept(null);
                    return;
                }

                List<Course> path = new ArrayList<>(); // result

                Queue<String> q = new LinkedList<>();
                q.offer(code);  // enqueue

                while (!q.isEmpty()) {
                    String cur = q.poll();  // dequeue
                    Course course = allCourses.get(cur);
                    path.add(course);

//                    for (String next: course.prerequisite)
//                        q.offer(next);  // enqueue the next course
                }
                callback.accept(path);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

}