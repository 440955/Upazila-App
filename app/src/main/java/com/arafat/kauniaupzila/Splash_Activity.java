package com.arafat.kauniaupzila;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(ContextCompat.getColor((Context) Splash_Activity.this, R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor((Context) Splash_Activity.this, R.color.white));


        Thread myThread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    startActivity(new Intent((Context) Splash_Activity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        myThread.start();


    }
}