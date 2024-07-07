package com.example.papercut.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.papercut.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Firebase Authentication instance
    FirebaseAuth auth;

    // Button reference for logout functionality
    Button button;

    // Text view to display user details (email)
    TextView textView;

    // Currently logged-in user object
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance();

        // Find views by their IDs from the layout resource
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);

        // Get the current user from Firebase Authentication
        user = auth.getCurrentUser();

        // Check if user is logged in
        if (user == null) {
            // Redirect to login page if user is not logged in
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Set user's email address to the text view
            textView.setText(user.getEmail());
        }

        // Attach click listener to the logout button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the user out using Firebase Authentication
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                // Redirect to login page after logout
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}