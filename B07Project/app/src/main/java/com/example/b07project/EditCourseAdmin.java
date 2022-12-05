package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EditCourseAdmin extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit_course_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etCourseName = (EditText) findViewById(R.id.etCourseName);
        etCourseCode = (EditText) findViewById(R.id.etCourseCode);
        textviewpre = (TextView) findViewById(R.id.textview_pre);
        textviewoff = (TextView) findViewById(R.id.textview_off);

        finished_button = (Button) findViewById(R.id.button_done);

        //Create intent on other end
        Intent intent = getIntent();
        String[] course_code_arr = intent.getStringExtra("course_code").toString().split("\n");

        String course_code = course_code_arr[0];

        //Get current signed in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "";
        if (user != null) {
            uid = user.getUid();
        }

        mDatabase.child("admin_courses").child(course_code).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    textviewpre.setText(task.getResult().child("prerequisites").getValue().toString());
                    textviewoff.setText(task.getResult().child("offerings").getValue().toString());
                    etCourseName.setText(task.getResult().child("name").getValue().toString());
                    etCourseCode.setText(task.getResult().child("code").getValue().toString());

                }
            }
        });


        //Populate array for Prerequisites Dropdown
        ArrayList<String> preItems = new ArrayList<String>();

        //Get list of courses from firebase -- from admin id
        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(EditCourseAdmin.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                } else {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        preItems.add(key);
                    }

                }
                selectedOptions = new boolean[preItems.size()];
            }
        });


//
//        ArrayList<String> pre_split = new ArrayList<String>(Arrays.asList(textviewpre.getText().toString().trim().split(",")));
//        for(int index = 0; index < pre_split.size(); index++) {
//            if(preItems.contains(pre_split.get(index))) {
//                int in = preItems.indexOf(pre_split.get(index));
//                selectedList.add(in);
//                Collections.sort(selectedList);
//            }
//        }


        textviewpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseAdmin.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseAdmin.this);
                builder.setTitle("Select Offerings");
                builder.setCancelable(false);
                String[] arr = {"Fall", "Winter", "Summer"};
                selectedOptions2 = new boolean[arr.length];

                builder.setMultiChoiceItems(arr, selectedOptions2, new DialogInterface.OnMultiChoiceClickListener() {
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


        finished_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCourseName = etCourseName.getText().toString().trim();
                String strCourseCode = etCourseCode.getText().toString().trim();
                String strPrerequisites = textviewpre.getText().toString().trim();
                String strOfferings = textviewoff.getText().toString().trim();

                if (strCourseCode.isEmpty()) {

                    etCourseCode.setError("Course code cannot be empty");

                } else if (strCourseName.isEmpty()) {

                    etCourseName.setError("Course name cannot be empty");

                } else if (strOfferings.isEmpty()) {

                    textviewoff.setError("Course offerings cannot be empty");
                    Toast.makeText(EditCourseAdmin.this, "Course offerings cannot be empty", Toast.LENGTH_LONG).show();

                } else {

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
                    writeNewCourse(strCourseName, strCourseCode, course_code, strPrerequisites, strOfferings);
                    Intent intent = new Intent(EditCourseAdmin.this, EditCourses.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

//                String strPrerequisites = etPrerequisites.getText().toString().trim();
//                String strOfferings = etCourseOfferings.getText().toString().trim();

            }
        });
    }

    private void deleteCourse(String courseCode, String preCode) {
        Log.e("TAG", courseCode);

        mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
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
                                newPrereqs += preCode + ",";
                                int upperBound = 0;
                                if (!newPrereqs.equals("")) {
                                    upperBound = newPrereqs.length() - 1;
                                }
                                mDatabase.child("admin_courses").child(ds.getKey()).child("prerequisites").setValue(newPrereqs.substring(0, upperBound));
                            }
                            i++;
                        }

                    }
                    mDatabase.child("admin_courses").child(courseCode).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditCourseAdmin.this, "Course Deleted Successfully", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditCourseAdmin.this, "Something's Wrong", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    public void writeNewCourse(String name, String code, String preCode, String prerequisites, String offerings) {

        //Check if code has changed
        if (!code.equals(preCode)) {
            mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(EditCourseAdmin.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                    } else {
                        for (DataSnapshot ds : task.getResult().getChildren()) {
                            if (ds.getKey().equals(preCode)) { // Check if it equals the intent code
                                //Delete previous course if code has changed
                                deleteCourse(preCode, code);
                            }

                            //Change prerequisites for other courses (if code has changed)
                            String[] prereqs = task.getResult().child(ds.getKey()).child("prerequisites").getValue().toString().split(",");
                            int i = 0;
                            boolean courseInList = false;
                            while (i < prereqs.length && !courseInList) {
                                if (prereqs[i].equals(preCode)) {
                                    courseInList = true;
                                    String newPrereqs = "";
                                    for (int j = 0; j < prereqs.length; j++) {
                                        if (j != i) {
                                            newPrereqs += prereqs[j] + ",";
                                        } else {
                                            newPrereqs += code + ",";
                                        }
                                    }
                                    int upperBound = 0;
                                    if (!newPrereqs.equals("")) {
                                        upperBound = newPrereqs.length() - 1;
                                    }
                                    mDatabase.child("admin_courses").child(ds.getKey()).child("prerequisites").setValue(newPrereqs.substring(0, upperBound));
                                }
                                i++;
                            }


//                            ArrayList<String> pre_arr = new ArrayList<String>(Arrays.asList(ds.child("prerequisites").getValue().toString().split(",")));
//                            String pre_change = "";
//                            for(int i = 0; i < pre_arr.size(); i++) {
//                                if(pre_arr.get(i).equals(preCode)) {
//                                    pre_change+=code;
//                                } else {
//                                    pre_change += pre_arr.get(i);
//                                }
//                                if(i != pre_arr.size()-1) {
//                                    pre_change += ",";
//                                }
//                            }

                        }

                    }
                }
            });

            //Change taken courses (if code has changed)
            //Check if code has changed
            if (!code.equals(preCode)) {
                mDatabase.child("students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(EditCourseAdmin.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                        } else {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                //For each user


//                            ArrayList<String> pre_arr = new ArrayList<String>(Arrays.asList(ds.child("prerequisites").getValue().toString().split(",")));
//                            String pre_change = "";
//                            for(int i = 0; i < pre_arr.size(); i++) {
//                                if(pre_arr.get(i).equals(preCode)) {
//                                    pre_change+=code;
//                                } else {
//                                    pre_change += pre_arr.get(i);
//                                }
//                                if(i != pre_arr.size()-1) {
//                                    pre_change += ",";
//                                }
//                            }

                            }

                        }
                    }
                });


            }


            CourseAdmin course = new CourseAdmin(name, code, prerequisites, offerings);
            mDatabase.child("admin_courses").child(code).setValue(course);

        }


    }
}