package com.example.papercut.activities;

import android.app.ActivityOptions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import com.example.papercut.R;


import java.util.Timer;
import java.util.TimerTask;

public class StartPage1Activity extends AppCompatActivity{
    Timer timer;
    Bundle b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage1);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), StartPage2Activity.class);
                //b = ActivityOptions.makeSceneTransitionAnimation(StartPage1Activity.this).toBundle();
                //startActivity(intent, b);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}