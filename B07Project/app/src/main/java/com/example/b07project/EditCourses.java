package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EditCourses extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arr = new ArrayList<>();
    private DatabaseReference database;
    Button btn;
    Button add_course; // ADD COURSES BUTTON
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_courses);
        listView = (ListView)findViewById(R.id.list_view);
        btn = findViewById(R.id.button_to_go_back);
        add_course = findViewById(R.id.button3);
        ArrayAdapter<String> arr2 = new ArrayAdapter<>(this, R.layout.textviewlayout, arr);
        database = FirebaseDatabase.getInstance().getReference();

        database.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(EditCourses.this, "There Are No Courses Avalialbe", Toast.LENGTH_LONG).show();
                }
                else {
                    for(DataSnapshot ds : task.getResult().getChildren()) {

                        String key =ds.getKey() + "\n"+ ds.child("name").getValue() + "\nOFFERED: " + ds.child("offerings").getValue() + "\nPREREQUISITES: " + ds.child("prerequisites").getValue();
                        arr.add(key);
                    }
                    listView.setAdapter(arr2);
                }

            }
        });
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditCourses.this, AddCourseAdmin.class));
                arr2.notifyDataSetChanged();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(EditCourses.this, EditCourses.class));
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EditCourses.this, login_activity.class));


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCourses.this);
                builder.setTitle("Select");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteCourse(arr.get(i).split("\n")[0]);
                        arr2.notifyDataSetChanged();
                        finish();
                    }
                });
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Call edit func
                        startActivityForResult(new Intent(EditCourses.this, EditCourseAdmin.class), 1); // CHNAGE TO AK's edit code
                        arr2.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();



            }
            private void deleteCourse(String courseCode) {
                Log.e("TAG", courseCode);

                database.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            for (DataSnapshot ds: task.getResult().getChildren()) {
                                String[] prereqs = task.getResult().child(ds.getKey()).child("prerequisites").getValue().toString().split(",");
                                int i = 0;
                                boolean courseInList = false;
                                while (i < prereqs.length && !courseInList) {
                                    if (prereqs[i].equals(courseCode)) {
                                        courseInList = true;
                                        String newPrereqs = "";
                                        for (int j = 0; j < prereqs.length; j++) {
                                            if (j != i) {
                                                newPrereqs += prereqs[j] + ",";
                                            }
                                        }
                                        int upperBound = 0;
                                        if (!newPrereqs.equals("")) {
                                            upperBound = newPrereqs.length()-1;
                                        }
                                        database.child("admin_courses").child(ds.getKey()).child("prerequisites").setValue(newPrereqs.substring(0, upperBound));
                                    }
                                    i++;
                                }
                            }
                            database.child("admin_courses").child(courseCode).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditCourses.this, "Course Deleted Successfully", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditCourses.this, "Something's Wrong", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }
                });
            }


        });



    }
}
