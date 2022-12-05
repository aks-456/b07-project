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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
//    private AppBarConfiguration appBarConfiguration;
//    private ActivityRegisterBinding binding;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText emailBox = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordBox = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();

        Button button_stud = findViewById(R.id.register_button);
        Button button_adm = findViewById(R.id.register_button_admin);

        button_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();
                String email = emailBox.getText().toString();
                String password = passwordBox.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailBox.setError("Email Cannot Be Empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordBox.setError("Password Cannot Be Empty");
                    return;
                }
                if (password.length() < 6) {
                    passwordBox.setError("Password Must Be At least 6 Characters Long");
                    return;
                }
                createAccount(email, password, false);
            }
        });
        button_adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();
                String email = emailBox.getText().toString();
                String password = passwordBox.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailBox.setError("Email Cannot Be Empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordBox.setError("Password Cannot Be Empty");
                    return;
                }
                if (password.length() < 6) {
                    passwordBox.setError("Password Must Be At least 6 Characters Long");
                    return;
                }
                createAccount(email, password, true);

            }
        });
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

    private void createAccount(String email, String password, Boolean admin) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (admin){
                                String uid = "";
                                FirebaseUser ud = FirebaseAuth.getInstance().getCurrentUser();
                                if (ud != null) {
                                    uid = ud.getUid();
                                }
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Admin").child(uid);
                                myRef.setValue("True");
                            }
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void reload() {}

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent registerSuccessIntent = new Intent(RegisterActivity.this, login_activity.class);
            startActivity(registerSuccessIntent);
        }
    }
}