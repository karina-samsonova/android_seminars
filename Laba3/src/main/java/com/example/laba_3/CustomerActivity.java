package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    CustomerAdapter adapter;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getCustomer();
    }

    private void getCustomer() {
        class GetCustomer extends AsyncTask<Void, Void, List<Customer>> {

            @Override
            protected List<Customer> doInBackground(Void... voids) {
                List<Customer> customerList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .customerDao()
                        .getAll();
                return customerList;
            }

            @Override
            protected void onPostExecute(List<Customer> customer) {
                super.onPostExecute(customer);
                adapter = new CustomerAdapter(CustomerActivity.this, customer);
                recyclerview.setAdapter(adapter);
            }
        }

        GetCustomer gc = new GetCustomer();
        gc.execute();
    }

    public void openHomePage(View view){
        Intent intent = new Intent(this, StartingActivity.class);
        startActivity(intent);
    }

    public void openAddPage(View view){
        Intent intent = new Intent(this, AddCustomerActivity.class);
        startActivity(intent);
    }

    public void reload(View view){
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }

    private void getCustomerByName() {
        class GetCustomerByName extends AsyncTask<Void, Void, List<Customer>> {

            @Override
            protected List<Customer> doInBackground(Void... voids) {
                List<Customer> customerList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .customerDao()
                        .getByName(String.valueOf(mEditText.getText()));
                return customerList;
            }

            @Override
            protected void onPostExecute(List<Customer> customer) {
                super.onPostExecute(customer);
                adapter = new CustomerAdapter(CustomerActivity.this, customer);
                recyclerview.setAdapter(adapter);
            }
        }

        GetCustomerByName gc = new GetCustomerByName();
        gc.execute();
    }

    private void getCustomerBySurname() {
        class GetCustomerBySurname extends AsyncTask<Void, Void, List<Customer>> {

            @Override
            protected List<Customer> doInBackground(Void... voids) {
                List<Customer> customerList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .customerDao()
                        .getBySurname(String.valueOf(mEditText.getText()));
                return customerList;
            }

            @Override
            protected void onPostExecute(List<Customer> customer) {
                super.onPostExecute(customer);
                adapter = new CustomerAdapter(CustomerActivity.this, customer);
                recyclerview.setAdapter(adapter);
            }
        }

        GetCustomerBySurname gc = new GetCustomerBySurname();
        gc.execute();
    }

    public void searchByName(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog_edit, null);
        mBuilder.setTitle("Enter the value to search for");
        mEditText = mView.findViewById(R.id.editTextTextPersonName);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getCustomerByName();
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

    public void searchBySurname(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog_edit, null);
        mBuilder.setTitle("Enter the value to search for");
        mEditText = mView.findViewById(R.id.editTextTextPersonName);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getCustomerBySurname();
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