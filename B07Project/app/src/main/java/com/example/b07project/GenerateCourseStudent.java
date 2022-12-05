package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
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

public class GenerateCourseStudent extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Spinner spfuture;
    Button addfubutton, confirmbutton;
    EditText displaytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_generate_course_student);
        } else {
            setContentView(R.layout.activity_main);
        }

        spfuture = (Spinner) findViewById(R.id.spinnerfuture);
        addfubutton = (Button) findViewById(R.id.addfubutton);
        confirmbutton = (Button) findViewById(R.id.confirmbutton);
        displaytext = (EditText) findViewById(R.id.displaytext);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ArrayList<String> fucrs = new ArrayList<String>();
        fucrs.add("Choose Future Courses");

        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(GenerateCourseStudent.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                } else {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        fucrs.add(key);
                    }
                }
            }
        });

        ArrayAdapter<String> preAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fucrs);
        spfuture.setAdapter(preAdapter);

        addfubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = displaytext.getText().toString().trim();
                String newer = "";
                if(!(spfuture.getSelectedItem().toString().equals("Choose Future Courses"))) {
                    if(current.equals("")) {
                        newer = current+ spfuture.getSelectedItem().toString();
                    } else {
                        newer = current + "," + spfuture.getSelectedItem().toString();
                    }
                    displaytext.setText(newer);
                }
            }
        });

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = "";
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Student").child(uid).child("FutureCrs");
                String txt = displaytext.getText().toString().trim();
//
                myRef.setValue(txt);
            }
        });
    }

//
//    public void addfuturetofiredb(View view){
//
//        String uid = "";
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            uid = user.getUid();
//        }
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Student").child(uid).child("FutureCrs");
//        myRef.setValue("future course 1");
//    }
}