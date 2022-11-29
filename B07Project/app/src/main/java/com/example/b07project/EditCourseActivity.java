package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCourseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText courseCodeBox;
    EditText offeringSessionBox;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        courseCodeBox = findViewById(R.id.editTextCourseCode);
        offeringSessionBox = findViewById(R.id.editTextOfferingSession);

        deleteButton = findViewById(R.id.delete_course_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseCode = courseCodeBox.getText().toString().trim();
                String offeringSession = offeringSessionBox.getText().toString().trim();
                deleteCourse(courseCode, offeringSession);
            }
        });
    }

    private void deleteCourse(String courseCode, String offeringSession) {
        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    mDatabase.child("admin_courses").child(courseCode).removeValue();
                    //for (DataSnapshot ds: task.getResult().getChildren()) {

                    //}
                    //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

}