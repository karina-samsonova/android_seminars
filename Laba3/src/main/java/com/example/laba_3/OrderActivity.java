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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    OrderAdapter adapter;
    Spinner mSpinner;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getOrder();
    }

    private void getOrder() {
        class GetOrder extends AsyncTask<Void, Void, List<Order>> {

            @Override
            protected List<Order> doInBackground(Void... voids) {
                List<Order> orderList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderDao()
                        .getAll();
                return orderList;
            }

            @Override
            protected void onPostExecute(List<Order> order) {
                super.onPostExecute(order);
                adapter = new OrderAdapter(OrderActivity.this, order);
                recyclerview.setAdapter(adapter);
            }
        }

        GetOrder gc = new GetOrder();
        gc.execute();
    }

    public void openHomePage(View view){
        Intent intent = new Intent(this, StartingActivity.class);
        startActivity(intent);
    }

    public void openAddPage(View view){
        Intent intent = new Intent(this, AddOrderActivity.class);
        startActivity(intent);
    }

    public void reload(View view){
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private void getOrderByStatus() {
        class GetOrderByStatus extends AsyncTask<Void, Void, List<Order>> {

            @Override
            protected List<Order> doInBackground(Void... voids) {
                List<Order> orderList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderDao()
                        .getByStatus(mSpinner.getSelectedItem().toString());
                return orderList;
            }

            @Override
            protected void onPostExecute(List<Order> order) {
                super.onPostExecute(order);
                adapter = new OrderAdapter(OrderActivity.this, order);
                recyclerview.setAdapter(adapter);
            }
        }

        GetOrderByStatus gc = new GetOrderByStatus();
        gc.execute();
    }

    public void searchByStatus(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog, null);
        mBuilder.setTitle("Choose the value to search for");
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"preparation", "delivery", "received"});
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = mView.findViewById(R.id.spinner);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setSelected(true);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getOrderByStatus();
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


    private void getOrderByStuff() {
        class GetOrderByStuff extends AsyncTask<Void, Void, List<Order>> {

            @Override
            protected List<Order> doInBackground(Void... voids) {
                List<Order> orderList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderDao()
                        .getByStuff(Integer.valueOf(String.valueOf(mEditText.getText())));
                return orderList;
            }

            @Override
            protected void onPostExecute(List<Order> order) {
                super.onPostExecute(order);
                adapter = new OrderAdapter(OrderActivity.this, order);
                recyclerview.setAdapter(adapter);
            }
        }

        GetOrderByStuff gc = new GetOrderByStuff();
        gc.execute();
    }

    public void searchByStuff(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog_int, null);
        mBuilder.setTitle("Enter the value to search for");
        mEditText = mView.findViewById(R.id.editTextInt);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getOrderByStuff();
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

    private void getOrderByCustomer() {
        class GetOrderByCustomer extends AsyncTask<Void, Void, List<Order>> {

            @Override
            protected List<Order> doInBackground(Void... voids) {
                List<Order> orderList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderDao()
                        .getByCustomer(Integer.valueOf(String.valueOf(mEditText.getText())));
                return orderList;
            }

            @Override
            protected void onPostExecute(List<Order> order) {
                super.onPostExecute(order);
                adapter = new OrderAdapter(OrderActivity.this, order);
                recyclerview.setAdapter(adapter);
            }
        }

        GetOrderByCustomer gc = new GetOrderByCustomer();
        gc.execute();
    }

    public void searchByCustomer(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.search_dialog_int, null);
        mBuilder.setTitle("Enter the value to search for");
        mEditText = mView.findViewById(R.id.editTextInt);
        mBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getOrderByCustomer();
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
