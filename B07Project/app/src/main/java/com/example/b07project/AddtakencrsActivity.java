package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddtakencrsActivity extends AppCompatActivity {

    EditText editTextcode, editTextsessions;
    Button btnAdd;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtakencrs);

        database = FirebaseDatabase.getInstance();
        //TODO fix user id with intent.getextra...

        String userid = "101";
        ref = database.getReference("students").child(userid).child("takenCourse");


        editTextcode = (EditText) findViewById(R.id.edTxtCode);
        editTextsessions = (EditText) findViewById(R.id.edTxtSession);

        btnAdd = (Button) findViewById(R.id.tcbuttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                writeNewCrs();


            }

            public void writeNewCrs() {
                Course course = new Course(editTextcode.getText().toString(),
                        editTextsessions.getText().toString());
                String code = editTextcode.getText().toString();
                ref.child(code).setValue(course);


            }
        });
    }
}