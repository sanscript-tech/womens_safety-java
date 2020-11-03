package com.csd.myapplication;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postdelayed(new Runnable()){
            @Override
                    public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class)
                startActivity(homeIntent);
                finish();
            }

        }
    },TIME_OUT
}
