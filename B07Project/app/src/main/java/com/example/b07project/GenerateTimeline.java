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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GenerateTimeline extends AppCompatActivity {

    ListView lvTimeline;
    private DatabaseReference mDatabase;

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

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> courses = (ArrayList<String>) args.getSerializable("ARRAYLIST");

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(GenerateTimeline.this, android.R.layout.simple_list_item_1, courses);
        lvTimeline.setAdapter(coursesAdapter);

        for(int i = 0; i < courses.size(); i++) {
            String current_course = courses.get(i);
            int j = 0;
            while(j < 4) {

                mDatabase.child("admin_courses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(GenerateTimeline.this, "There are no courses added to select prerequisites, please enter them manually", Toast.LENGTH_LONG).show();
                        }
                        else {
                            for(DataSnapshot ds : task.getResult().getChildren()) {
                                String key = ds.getKey();
                                if(key.equals(current_course)) {
                                    String prereq = ds.child("prerequisites").getValue().toString();

                                    Log.e("TAG", prereq);
//                                    String prereq_arr []= prereq.split(",");

                                }
                            }

                        }
                    }
                });

                j++;
            }

        }

    }
}