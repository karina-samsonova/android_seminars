package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
    }

    public void openOrders(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void openStuff(View view) {
        Intent intent = new Intent(this, StuffActivity.class);
        startActivity(intent);
    }

    public void openCustomers(View view) {
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }
}