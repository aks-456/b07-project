package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class AddCourseAdmin extends AppCompatActivity {

    EditText etCourseName, etCourseCode;
    TextView textviewpre, textviewoff;
    Button finished_button;
    private DatabaseReference mDatabase;
    boolean[] selectedOptions, selectedOptions2;
    ArrayList<Integer> selectedList = new ArrayList<>();
    ArrayList<Integer> selectedList2 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etCourseName = (EditText)findViewById(R.id.etCourseName);
        etCourseCode = (EditText)findViewById(R.id.etCourseCode);
        textviewpre = (TextView)findViewById(R.id.textview_pre);
        textviewoff = (TextView)findViewById(R.id.textview_off);

        finished_button = (Button)findViewById(R.id.button_done);

        //Get current signed in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "";
        if (user != null) {

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();
        }

        //Populate array for Prerequisites Dropdown
        ArrayList<String> preItems=new ArrayList<String>();

        //Get list of courses from firebase -- from admin id
        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AddCourseAdmin.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                }
                else {
                    for(DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        preItems.add(key);
                    }

                }
                selectedOptions = new boolean[preItems.size()];
            }
        });
        textviewpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddCourseAdmin.this);
                builder.setTitle("Select Prerequisites");
                builder.setCancelable(false);
                String[] arr = preItems.toArray(new String[preItems.size()]);

                builder.setMultiChoiceItems(arr, selectedOptions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selectedList.add(i);
                            Collections.sort(selectedList);
                        } else {
                            selectedList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < selectedList.size(); j++) {
                            // concat array value
                            stringBuilder.append(arr[selectedList.get(j)]);
                            // check condition
                            if (j != selectedList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textviewpre.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedOptions.length; j++) {
                            // remove all selection
                            selectedOptions[j] = false;
                            // clear language list
                            selectedList.clear();
                            // clear text view value
                            textviewpre.setText("");
                        }
                    }
                });
                builder.show();

            }
        });
        textviewoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddCourseAdmin.this);
                builder.setTitle("Select Offerings");
                builder.setCancelable(false);
                String[] arr = {"Fall", "Winter", "Summer"};
                selectedOptions2 = new boolean[arr.length];

                builder.setMultiChoiceItems(arr, selectedOptions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            selectedList2.add(i);
                            // Sort array list
                            Collections.sort(selectedList2);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            selectedList2.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < selectedList2.size(); j++) {
                            // concat array value
                            stringBuilder.append(arr[selectedList2.get(j)]);
                            // check condition
                            if (j != selectedList2.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textviewoff.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        selectedList2.clear();
                        dialogInterface.dismiss();

                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedOptions2.length; j++) {
                            // remove all selection
                            selectedOptions2[j] = false;
                            // clear language list
                            selectedList2.clear();
                            // clear text view value
                            textviewoff.setText("");
                        }
                    }
                });
                builder.show();

            }
        });



        // use default spinner item to show options in spinner
//        ArrayAdapter<String> preAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, preItems);
//        spPre.setAdapter(preAdapter);
//
//        btnAddPre.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String current = etPrerequisites.getText().toString().trim();
//                String newer = "";
//                if(!(spPre.getSelectedItem().toString().equals("Choose Prerequisite(s)"))) {
//                    if(current.equals("")) {
//                        newer = current+ spPre.getSelectedItem().toString();
//                    } else {
//                        newer = current + ", " + spPre.getSelectedItem().toString();
//                    }
//                    etPrerequisites.setText(newer);
//
//                }
//            }
//        });
//
//
        finished_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCourseName = etCourseName.getText().toString().trim();
                String strCourseCode = etCourseCode.getText().toString().trim();
                String strPrerequisites =  textviewpre.getText().toString().trim();
                String strOfferings =  textviewoff.getText().toString().trim();

//                String strPrerequisites = etPrerequisites.getText().toString().trim();
//                String strOfferings = etCourseOfferings.getText().toString().trim();

                //Get current signed in user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = "";
                if (user != null) {

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    uid = user.getUid();
                }
                //Change to uid
                writeNewCourse(strCourseName, strCourseCode, strPrerequisites, strOfferings);
                Intent intent = new Intent(AddCourseAdmin.this, EditCourses.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


    }

    public void writeNewCourse(String name, String code, String prerequisites, String offerings) {
        //Check if the course code already exists
        Course course = new Course(name, code, prerequisites, offerings);
        mDatabase.child("admin_courses").child(code).setValue(course);
    }


}