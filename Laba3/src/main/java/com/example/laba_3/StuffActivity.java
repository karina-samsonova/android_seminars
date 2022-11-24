package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class StuffActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    StuffAdapter adapter;
    Spinner mSpinner;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getStuff();
    }

    private void getStuff() {
        class GetStuff extends AsyncTask<Void, Void, List<Stuff>> {

            @Override
            protected List<Stuff> doInBackground(Void... voids) {
                List<Stuff> stuffList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .stuffDao()
                        .getAll();
                return stuffList;
            }

            @Override
            protected void onPostExecute(List<Stuff> stuff) {
                super.onPostExecute(stuff);
                adapter = new StuffAdapter(StuffActivity.this, stuff);
                recyclerview.setAdapter(adapter);
            }
        }

        GetStuff gc = new GetStuff();
        gc.execute();
    }

    private void getStuffByStock() {
        class GetStuffByStock extends AsyncTask<Void, Void, List<Stuff>> {

            @Override
            protected List<Stuff> doInBackground(Void... voids) {
                List<Stuff> stuffList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .stuffDao()
                        .getByStock(mSpinner.getSelectedItem().toString());
                return stuffList;
            }

            @Override
            protected void onPostExecute(List<Stuff> stuff) {
                super.onPostExecute(stuff);
                adapter = new StuffAdapter(StuffActivity.this, stuff);
                recyclerview.setAdapter(adapter);
            }
        }

        GetStuffByStock gc = new GetStuffByStock();
        gc.execute();
    }

    private void getStuffByName() {
        class GetStuffByName extends AsyncTask<Void, Void, List<Stuff>> {

            @Override
            protected List<Stuff> doInBackground(Void... voids) {
                List<Stuff> stuffList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .stuffDao()
                        .getByName(String.valueOf(mEditText.getText()));
                return stuffList;
            }

            @Override
            protected void onPostExecute(List<Stuff> stuff) {
                super.onPostExecute(stuff);
                adapter = new StuffAdapter(StuffActivity.this, stuff);
                recyclerview.setAdapter(adapter);
            }
        }

        GetStuffByName gc = new GetStuffByName();
        gc.execute();
    }

    public void openHomePage(View view){
        Intent intent = new Intent(this, StartingActivity.class);
        startActivity(intent);
    }

    public void openAddPage(View view){
        Intent intent = new Intent(this, AddStuffActivity.class);
        startActivity(intent);
    }

    public void reload(View view){
        Intent intent = new Intent(this, StuffActivity.class);
        startActivity(intent);
    }

    public void searchByStock(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(StuffActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog, null);
        mBuilder.setTitle("Choose the value to search for");
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"yes", "no"});
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = mView.findViewById(R.id.spinner);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setSelected(true);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getStuffByStock();
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void searchByName(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(StuffActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog_edit, null);
        mBuilder.setTitle("Enter the value to search for");
        mEditText = mView.findViewById(R.id.editTextTextPersonName);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getStuffByName();
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}