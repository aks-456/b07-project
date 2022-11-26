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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText text_email, text_password;
    Button text_signup;
    Button text_signuppage;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         text_email = findViewById(R.id.editTextTextEmailAddress);
         text_password = findViewById(R.id.editTextTextPassword);
         text_signup = findViewById(R.id.activity_switcher_button);
        text_signuppage = (Button)findViewById(R.id.toSignUp);
         mAuth = FirebaseAuth.getInstance();

    text_signuppage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent = new Intent(login_activity.this, MainActivity.class); //CHANGE TO THE AHIL's REGISTRATION PAGE
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
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           reload();
        }
    }

    private void reload() {
//        Intent intent = new Intent(login_activity.this, MainActivity.class); //CHANGE TO THE HOMEPAGE
//        startActivity(intent);
    }

}