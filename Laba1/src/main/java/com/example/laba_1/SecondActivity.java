package com.example.laba_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
    public void sendLink(View view){
        EditText editText = findViewById(R.id.txt_link);
        String link = editText.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }
}