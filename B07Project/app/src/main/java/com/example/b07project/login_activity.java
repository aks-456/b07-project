package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText text_email, text_password;
    Button text_signup;
    Button text_signuppage;
    Button text_back;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         text_email = findViewById(R.id.editTextTextEmailAddress);
         text_password = findViewById(R.id.editTextTextPassword);
         text_signup = (Button)findViewById(R.id.activity_switcher_button);
        text_signuppage = (Button)findViewById(R.id.toSignUp);
         mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        text_signuppage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent = new Intent(login_activity.this, RegisterActivity.class);
            startActivity(intent);
        }
    });
    text_signup.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        String email = text_email.getText().toString();
        String password = text_password.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            text_email.setError("Email Cannot Be Empty");
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            text_password.setError("Password Cannot Be Empty");
            return;
        }
        if(password.length() < 6)
        {
            text_password.setError("Password must be of atleast length 6");
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
});
    }

    private void updateUI(FirebaseUser user){
        if(user != null) {
            Toast.makeText(login_activity.this, "Verification Successful",
                    Toast.LENGTH_SHORT).show();
            onStart();
        }
        return;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user==null)
            return;
        String uid = "";
        uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Admin").child(uid);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String finalUid = uid;
        mDatabase.child("Admin").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(login_activity.this, "There are no admins", Toast.LENGTH_LONG).show();
                }
                else {
                    for(DataSnapshot ds : task.getResult().getChildren()) {
                        if(finalUid.equals(ds.getKey().toString())) {
                            Intent intent = new Intent(login_activity.this, EditCourses.class);
                            startActivity(intent);
                            return;
                        }
                    }
                    Intent intent = new Intent(login_activity.this, StudentHomePage.class);
                    startActivity(intent);
                }
            }
        });
    }


//    private void updateUI(FirebaseUser user){
//        if(user != null) {
//            Toast.makeText(login_activity.this, "Verification Successful",
//                    Toast.LENGTH_SHORT).show();
//            int check = 0;
//            database.child("Admin").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//                        Toast.makeText(login_activity.this, "There Are No Admins", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        for(DataSnapshot ds : task.getResult().getChildren()) {
//                            if(ds.getKey().toString().equals(user.getUid().toString()))
//                            {
//                                Intent intent = new Intent(login_activity.this, EditCourses.class);
//                                intent.putExtra("key",user.getUid());
//                                startActivity(intent);
//                            }
//                        }
//                    }
//
//                }
//            });
//            Intent intent = new Intent(login_activity.this, EditCourseAdmin.class); //CHange to Student Homepage
//            intent.putExtra("key",user.getUid());
//            startActivity(intent);
//        }
//        return;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//           reload(currentUser);
//        }
//    }
//
//    private void reload(FirebaseUser user) {
//        Intent intent = new Intent(login_activity.this, EditCourses.class); //CHANGE TO THE HOMEPAGE
//        intent.putExtra("key",user.getUid());
//        startActivity(intent);
//    }

}