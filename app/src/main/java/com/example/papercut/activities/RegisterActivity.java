package com.example.papercut.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.papercut.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    // UI elements
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    TextView textView;
    ProgressBar progressBar;
    Handler handler;
    Timer timer;

    private int progress=0;

    // Firebase Authentication
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find UI elements by their IDs
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register_final);
        textView = findViewById(R.id.loginNow);
        progressBar = findViewById(R.id.progressBar);
        handler = new Handler();
        timer = new Timer();

        // Set click listener for the login TextView
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the registration button
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                // Get email and password from input fields
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());

                // Check if email and password are not empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                    return;
                }

                // Create user with email and password using Firebase Auth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // If authentication is successful, show success message and redirect to MainPageActivity
                                    Toast.makeText(RegisterActivity.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                    progress = progressBar.getProgress();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            while (progress < 100) {
                                                progress++;
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        progressBar.setProgress(progress);
                                                    }
                                                });

                                                try {
                                                    Thread.sleep(5);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                    }).start();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 500);
                                } else {
                                    // Registration failed, display error message
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is already authenticated, if yes, redirect to AccountPageActivity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}