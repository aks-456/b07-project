package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_or_student extends AppCompatActivity {

    Button text_admin;
    Button text_student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_or_student);
        text_admin = findViewById(R.id.Admin);
        text_student = findViewById(R.id.Student);
        text_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_or_student.this, login_activity.class);
                startActivity(intent);
            }
        });
        text_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_or_student.this, login_activity.class);
                startActivity(intent);
            }
        });

    }
}